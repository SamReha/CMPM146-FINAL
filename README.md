# CMPM146-FINAL
A Rogue-like game that uses context-free grammars as the basis of its level generation system, plus a system for authoring new grammars automatically.

In this project, we demonstrate Roag, a Rogue-like game that uses context-free grammars to generate new dungeon content. We also demonstrate a grammar generator capable of intelligently authoring new grammars for use by Roag.

Context free grammars can be a very useful tool for level generation in video games, despite their current lack of use. Grammars are great at generating distinct artifacts that all exhibit common structures and patterns. This makes them ideal for our domain, Rogue-like dungeons, because they allow you to define a possibility space of dungeons wherein each dungeon feels different, but all dungeons follow similar rules, like always having a start and ending or other high-level structural elements. Because we are using the context-free grammar engine Tracery.JS (galaxykate), our grammars are defined as JSON documents, which makes them human-readable and human-editable as well. 

Our grammar generator is capable of authoring new, Roag-compatible dungeon grammars automatically by referencing a set of "atomic elements", essentially a dictionary of symbols representing the basic elements of whatever game will be using the created grammars. These are things like floor types, enemy types, and pickups types. When a designer is using our grammar generation system, they must hand-author the atomic elements, but the grammar generator does the rest of the work for you. Again, because our grammars are human-readable and human-editable, the automatically produced grammars can be easily tweaked and tuned by hand.

## Usage:
To play Roag, compile the game with the following command:
javac RoagGame/Frame.java RoagGame/Roag.java

And run Roag with this command:
java RoagGame/Frame.java

Roag's current grammar is located in grammar_generator/dungeon.json. If there's no grammar here, one will need to be provided in order for Roag to be playable.

The grammar generator can be run with the following command:
python grammar_generator/generator

It will look for an atoms file in grammar\_generator/atoms.json. If there's no atoms file here, one will need to be provided. It will write the new grammar to grammar_generator/dungeon.json.
The grammar generator can be configured by editing the settings object in grammar_generator/atoms.json

## Known Issues:
Tracery.JS cannot currently be run in a Node-friendly way, as such our project lacks a grammar engine. Roag will run using a random grammar from a pre-authored list regardless of the dungeon.json file present.

## Future Plans:

## Contact Us:
 - Samuel Reha (sreha@ucsc.edu)
 - Nico Williams
 - Dustin Pfeiffer
