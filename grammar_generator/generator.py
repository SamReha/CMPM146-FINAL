"""
USAGE: -a [file_path] to set custom path for atom.json
       -d [file_path] to set custom path for dungeon.json
       -o to enable overwritting of output without warning
"""

import sys
import getopt
import os.path as path
import json

from random import randint

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
    grammar = dict(end = ["(end #obstacle# #enemy# #pickup#)"],
                   middle = ["#room#", "#room#, #middle#"],
                   room = ["(#type# #obstacle# #enemy# #pickup#)"],
                   start = ["(start #obstacle# #enemy# #pickup#)"],
                   dungeon = ["#start#, #middle#, #end#"],
                   origin = ["#dungeon#"]
                  )

    if not settings['use_all_atoms']:
        floors = random_reduce(floors)
        enemies = random_reduce(enemies)
        pickups = random_reduce(pickups)
        obstacles = random_reduce(obstacles)

    # Write rules for types, obstacles, enemies and pickups
    grammar["type"] = generate_type_rule(floors)
    grammar["obstacle"] = generate_obstacle_rule(obstacles)
    grammar["pickup"] = generate_pickup_rule(pickups)
    grammar["enemy"] = generate_enemy_rule(enemies)
    grammar["random"] = generate_number_series_rule(randint(1, 20))

    # Flip a coin to determine if we're getting fixed length or non-fixed length dungeon grammars
    if (randint(0, 1) is 1) or settings['always_fixed_length']:
        if settings['always_fixed_length']:
            dungeon_length = settings['fixed_length'] - 1
        else:
            dungeon_length = randint(0, 19)
            
        middle_rule_string = "#room#"
        for i in range(0, dungeon_length):
            middle_rule_string += ", #room#"
            
        grammar['middle'] = [middle_rule_string]
    else:   # Make a grammar capable of generating dungeons of varying lengths
        # grammar['middle'] = ["#room#", "#room#, #middle#"]  # Placeholder!
        # Roll for likelyhood of termination
        term_chance = randint(1, 10)
        grammar['middle'] = []
        for i in range(0, 10):
            if term_chance > 0:
                grammar['middle'].append("#room#")
                term_chance -= 1
            else:
                grammar['middle'].append("#room#, #middle#")
    
    print "Grammar Generated successfully!"

    # Spit that out grammar as a json file (dungeon.json).
    save_dungeon(grammar, dungeon_path, override)
        
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
   
## Saves the tracery-formatted grammar dunegon_trace to the path specified by path_to_dungeon
def save_dungeon(dungeon_trace, path_to_dungeon, override_status):
    print "Saving grammar to " + path_to_dungeon + "..."
    if not override_status and path.isfile(path_to_dungeon):
        go_ahead = raw_input(path_to_dungeon + " already exists. Would you like to overwrite it? (y/n): ")
        if go_ahead is "y" or go_ahead is "Y":
            dungeon = open(path_to_dungeon, "w")
            dungeon.write(json.dumps(dungeon_trace, indent=2))
            print "Grammar successfully saved to " + path_to_dungeon + "!"
        else:
            print "Exiting generation with status 0"
            sys.exit(0)
    else:
        dungeon = open(path_to_dungeon, "w")
        dungeon.write(json.dumps(dungeon_trace, indent=2))
        print "Grammar successfully saved to " + path_to_dungeon + "!"
        
## Randomly filter out some atoms with no heuristic
def random_reduce(atom_set):
    reduced_set = list(atom_set)
    # Roll to see how many will get excluded (but be sure to leave at LEAST one atom!)
    numToKill = randint(0, len(atom_set)-1)
    while numToKill > 0:
        random_index = randint(0, len(reduced_set)-1)
        reduced_set.pop(random_index)
        numToKill -= 1
    
    return reduced_set
    
## Returns a list of rooms types defined as (floor_type, obstacle_type) tuples
#def get_room_types(floor_set, obstacle_set):
#    room_set = []
#    obstacle_set.append(dict(type="no_obstacle"))
#    for floor in floor_set:
#        for obstacle in obstacle_set:
#            room_set.append((floor['type'], obstacle['type']))
#    return room_set
    
## Generate a valid tracery rule defining what a room can be
#def generate_room_rule(room_set):
#    room_rule = "(#type# #obstacle# #enemy# #pickup#)"
#        
#    return room_rule

def generate_type_rule(type_set):
    type_rule = []
    for type in type_set:
        type_rule.append(type['type'])
    return type_rule
    
def generate_obstacle_rule(obstacle_set):
    obstacle_rule = ["no_obstacle"]
    for obstacle in obstacle_set:
        obstacle_rule.append(obstacle['type'])
    return obstacle_rule
    
def generate_pickup_rule(pickup_set):
    pickup_rule = ["no_pickup"]
    for pickup in pickup_set:
        pickup_rule.append(pickup['type'] + ":#random#")
    return pickup_rule
      
def generate_enemy_rule(enemy_set):
    enemy_rule = ["no_enemy"]
    for enemy in enemy_set:
        enemy_rule.append(enemy['type'] + ":#random#")
    return enemy_rule
    
def generate_number_series_rule(int):
    series_rule = []
    for i in range(1, int):
        series_rule.append(str(i))
    return series_rule
    
    
if __name__ == "__main__":
    main()