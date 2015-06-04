import json

# Open atoms.json, build dictionary of atomic elements
atoms = json.loads(open("atoms.json", "r").read())

# Use atoms to generate a Tracery grammar with semi-random properties
# As a demo, let's just hard-code that boring example grammar I made earlier
grammar = dict(end = ["(room end)"],
               middle = ["(room)", "(room), #middle#"],
               start = ["(room start)"],
               dungeon = ["#start#, #middle#, #end#"],
               origin = ["#dungeon#"]
              )

# Spit that out grammar as a json file (dungeon.json).
dungeon = open("dungeon.json", "w")
dungeon.write(json.dumps(grammar, indent=2))