"""
USAGE: -a [file_path] to set custom path for atom.json
       -d [file_path] to set custom path for dungeon.json
       -o to enable overwritting of output without warning
"""

import sys
import getopt
import os.path as path
import json

def main():
    atom_path, dungeon_path, override = setup(sys.argv)

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
        
    floors, enemies, pickups, obstacles = atoms['floor'], atoms['enemy'], atoms['pickup'], atoms['obstacle']
    settings = atoms['settings']
    if settings['always_overwrite_old_files']:
        override = True
        print 'Overwriting enabled in settings!'

    # Use atoms to generate a Tracery grammar with semi-random properties
    print "Generating grammar..."

    if not settings['use_all_atoms']:
        floors = get_potential_floors(floors)
        enemies = get_potential_enemies(enemies)
        pickups = get_potential_pickups(pickups)
        obstacles = get_potential_obstacles(obstacles)


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
        print "Grammar successfully saved to " + dungeon_path + "!"
        
def setup(arguments):
    atom_p = "atoms.json"
    dungeon_p = "dungeon.json"
    override_p = False
    try:
        opts, args = getopt.getopt(arguments[1:], "oa:d:", ["afile=","dfile="])
    except getopt.GetoptError:
        print 'USAGE: generator.py -a <alternate atoms file> -d <alternate dungeon file> -o'
        print "Generation failed with status 2"
        sys.exit(2)
        
    for opt, arg in opts:
        if opt in ("o", "-o"):
            print "Warnings disabled!"
            override_p = True
        elif opt in ("d", "-d"):
            dungeon_p = arg
        elif opt in ("a", "-a"):
            atom_p = arg
            
    return atom_p, dungeon_p, override_p
        
def get_potential_floors(floor_set):
    return floor_set
    
def get_potential_enemies(enemy_set):
    return enemy_set
    
def get_potential_pickups(pickup_set):
    return pickup_set
    
def get_potential_obstacles(obstacle_set):
    return obstacle_set
    
if __name__ == "__main__":
    main()