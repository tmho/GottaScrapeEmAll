/*
	# Usage
		curl http://www.pokeffectiveness.com | node pmon.js > pokemon.json
*/
var concat = require("concat-stream");
var trRow = require("trumpet")();
var $ = require("cheerio");
var started;

var map = {
	superweak: 400,
	weak: 200,
	normal_col: 100,
	resistant: 50,
	superresistant: 25,
	immune: 0
};

trRow.selectAll(".poke_row", function (row) {
	var obj = { types: [ ], typeDamageMods: { } };

	row.createReadStream().pipe(concat(function (html) {
		process.stdout.write(!started ? "[" : ",\n");
		started = true;

		$("span", html.toString()).each(function () {
			if ($(this).attr("class").match(/poke_type/))
				obj.types.push($(this).text());
			else if (!$(this).attr("class").match(/type/))
				obj[$(this).attr("class")] = $(this).text();
			else
				obj.typeDamageMods[$(this).text()] = map[$(this).parent().attr("class")];
		});

		process.stdout.write(JSON.stringify(obj, null, 2));
	}));
});

trRow.on("end", function () {
	process.stdout.write("]");
});

process.stdin.pipe(trRow);