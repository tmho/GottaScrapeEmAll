/*
	# Usage
		curl http://www.pokeffectiveness.com | node pmon.js > pokemon.json
*/
var request = require("request");
var concat = require("concat-stream");
var es = require("event-stream");
var trumpet = require("trumpet");
var $ = require("cheerio");

var map = {
	superweak: 400,
	weak: 200	,
	normal_col: 100,
	resistant: 50,
	superresistant: 25,
	immune: 0
};

var wsStats = require("stream").Transform({ objectMode: true });

wsStats._transform = function (obj, enc, next) {
	var out = this;
    var trStats = trumpet();

	trStats.selectAll(".span-8 .vitals-table tbody tr", function (row) {
		row.createReadStream().pipe(concat(function (html) {
			if (html.toString().length > 4)
				obj.stats[$("th", html.toString()).text()] = $("td:first-child", html.toString()).eq(0).text();
		}));
	});

	trStats.on("end", function () {
		out.push(JSON.stringify(obj, null, 2));
		next();
	});

    request("http://pokemondb.net/pokedex/" + obj["poke_name"]).pipe(trStats);
};

wsStats.on("end", function () {
	process.stdout.write("]");
});

process.stdout.write("[");
wsStats.pipe(es.join(",")).pipe(process.stdout);


var trRow = trumpet();

trRow.selectAll(".poke_row", function (row) {
	var obj = { types: [ ], typeDamageMods: { }, stats: { } };

	row.createReadStream().pipe(concat(function (html) {
		$("span", html.toString()).each(function () {
			if ($(this).attr("class").match(/poke_type/))
				obj.types.push($(this).text());
			else if (!$(this).attr("class").match(/type/))
				obj[$(this).attr("class")] = $(this).text();
			else
				obj.typeDamageMods[$(this).text()] = map[$(this).parent().attr("class")];
		});

		wsStats.write(obj);
	}));
});

trRow.on("end", function () {
	wsStats.end();
});

process.stdin.pipe(trRow);