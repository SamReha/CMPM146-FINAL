"use strict";
//var tracery = require('tracery');
var fs = require('fs');

// GET DUNGEON.JSON
fs.readFile('dungeon.json', 'utf8', function (err,data) {
    var dungeon = "";
    if (err) {
        console.log("ERROR! I couldn't open the dungeon file!");
        console.log(err);
    } else {
        dungeon = data;
        //var grammar = tracery.createGrammar(JSON.parse(dungeon));
        //var example = grammar.flatten('#origin#');
        
        var dummy_output = [
            "(start), (room keylock), (room keylock), (room no_obstacle), (room no_obstacle), (room no_obstacle), (room no_obstacle), (end)",
            "(start), (room keylock), (room keylock), (room keylock), (end)",
            "(start), (room no_obstacle), (room no_obstacle), (room no_obstacle), (end)",
            "(start), (room no_obstacle), (room keylock), (room no_obstacle), (room keylock), (end)",
            "(start), (room keylock), (room keylock), (room no_obstacle), (room no_obstacle), (room keylock), (room no_obstacle), (end"
        ];
        
        var random_index = Math.floor((Math.random()*dummy_output.length));

        console.log(dummy_output[random_index]);
    }
});