"""
USAGE: -a [file_path] to set custom path for atom.json
       -d [file_path] to set custom path for dungeon.json
       -o to enable overwritting of output without warning
"""

import sys
import getopt
import os.path as path
import json

# SETUP
atom_path = "atoms.json"
dungeon_path = "dungeon.json"
override = False
try:
    opts, args = getopt.getopt(sys.argv[1:], "oa:d:", ["afile=","dfile="])
except getopt.GetoptError:
    print 'USAGE: generator.py -a <alternate atoms file> -d <alternate dungeon file> -o'
    print "Generation failed with status 2"
    sys.exit(2)
    
for opt, arg in opts:
    if opt in ("o", "-o"):
        print "Warnings disabled!"
        override = True
    elif opt in ("d", "-d"):
        dungeon_path = arg
    elif opt in ("a", "-a"):
        atom_path = arg

# Open atoms.json, build dictionary of atomic elements
print "Reading atoms from " + atom_path + "..."
try:
    atom_file = open(atom_path, "r").read()
except IOError:
    print "IOError: No such file or directory: '" + atom_path + "'"
    print "Generation failed with status 2"
    sys.exit(2)

try:
    atoms = json.loads(atom_file)
except ValueError:
    print "ValueError in JSON, file at " + atom_path + " is likely malformed."
    print "Generation failed with status 2"
    sys.exit(2)

# Use atoms to generate a Tracery grammar with semi-random properties
print "Generating grammar..."
# As a demo, let's just hard-code that boring example grammar I made earlier
grammar = dict(end = ["(room end)"],
               middle = ["(room)", "(room), #middle#"],
               start = ["(room start)"],
               dungeon = ["#start#, #middle#, #end#"],
               origin = ["#dungeon#"]
              )
print "Grammar Generated successfully!"

# Spit that out grammar as a json file (dungeon.json).
print "Saving grammar to " + dungeon_path + "..."
if not override and path.isfile(dungeon_path):
    go_ahead = raw_input(dungeon_path + " already exists. Would you like to overwrite it? (y/n): ")
    if go_ahead is "y" or go_ahead is "Y":
        dungeon = open(dungeon_path, "w")
        dungeon.write(json.dumps(grammar, indent=2))
        print "Grammar successfully saved to " + dungeon_path + "!"
    else:
        print "Exiting generation with status 0"
        sys.exit(0)
else:
    dungeon = open(dungeon_path, "w")
    dungeon.write(json.dumps(grammar, indent=2))