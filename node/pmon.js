/*
	# Usage
		curl http://www.pokeffectiveness.com | node pmon.js > pokemon.json
*/
var request = require("request");
var concat = require("concat-stream");
var es = require("event-stream");
var trumpet = require("trumpet");
var $ = require("cheerio");

//Scrape all pokemeon from pokeffectiveness.com
var parsePokemonList = require("stream").Transform();
parsePokemonList._readableState.objectMode = true;

parsePokemonList._transform = function (buff, enc, next) {
	var out = this;
	var filterRow = trumpet();

	var map = {
		superweak: 400,
		weak: 200	,
		normal_col: 100,
		resistant: 50,
		superresistant: 25,
		immune: 0
	};

	filterRow.selectAll(".poke_row", function (row) {
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

			out.push(obj);
		}));
	});

	filterRow.write(buff);
	next();
};

//Scrape all stats from pokemondb.net
var parsePokemon = require("stream").Transform({ objectMode: true });

parsePokemon._transform = function (obj, enc, next) {
	var out = this;
    var filterStats = trumpet();

	filterStats.selectAll(".span-8 .vitals-table tbody tr", function (row) {
		row.createReadStream().pipe(concat(function (html) {
			if (html.toString().length > 4)
				obj.stats[$("th", html.toString()).text()] = $("td:first-child", html.toString()).eq(0).text();
		}));
	});

	filterStats.on("end", function () {
		out.push(obj);
		next();
	});

    request("http://pokemondb.net/pokedex/" + obj["poke_name"]).pipe(filterStats);
};

process.stdin
	.pipe(parsePokemonList)
	.pipe(parsePokemon)
	.pipe(require("JSONStream").stringify())
	.pipe(process.stdout);
