"use strict";
//var tracery = require('tracery');
var fs = require('fs');

// GET DUNGEON.JSON
fs.readFile('../grammar_generator/dungeon.json', 'utf8', function (err,data) {
    var dungeon = "";
    if (err) {
        console.log("ERROR! I couldn't open the dungeon file!");
        console.log(err);
    } else {
        // CONVERT CONTENTS INTO GRAMMAR OBJECT AND SAMPLE
        dungeon = data;
        //var grammar = tracery.createGrammar(JSON.parse(dungeon));
        //var sample = grammar.flatten('#origin#');
        var dummy_output = [
            "(start no_obstacle no_enemy treasure:1), (room no_obstacle no_enemy armor:2), (room keylock skeletron:1 no_pickup), (end keylock skeletron:1 weapon:2)",
            "(start no_obstacle skeletron:1 no_pickup), (room no_obstacle skeletron:1 armor:2), (end no_obstacle no_enemy treasure:2)",
            "(start no_obstacle skeletron:1 weapon:1), (room no_obstacle no_enemy armor:2), (end keylock no_enemy treasure:2)",
            "(start keylock no_enemy treasure:2), (room no_obstacle skeletron:1 treasure:2), (room no_obstacle skeletron:1 treasure:1), (room no_obstacle no_enemy armor:2), (end no_obstacle no_enemy treasure:2)",
            "(start keylock skeletron:1 treasure:1), (room no_obstacle skeletron:1 treasure:1), (room no_obstacle no_enemy armor:1), (room keylock no_enemy treasure:1), (end no_obstacle skeletron:1 armor:2)"
        ];
        var random_index = Math.floor((Math.random()*dummy_output.length));
        
        // SAVE SAMPLE TO TRACE.TXT
        fs.writeFile("..\\RoagGame\\trace.txt", dummy_output[random_index], function(err) {
            if(err) {
                return console.log(err);
            }
            console.log("Trace written to ..\\RoagGame\\trace.txt");
        }); 
    }
});