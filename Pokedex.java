/* Pokemon database. Keeps a data of all the Pokemon that exist in the game.
 * Also keeps track of whether the Pokemon has been seen and captured.
 * All the information is stored in arrays; we access the correct information
 * via the # of the Pokemon.
 */

import java.util.*;
import java.io.*;

public class Pokedex {
    private String[]                 name;          // Official name
    private String[]                 altername;     // (e.g. Lizard Pokemon)
    private String[]                 ability1;      // 1st known ability
    private String[]                 ability2;      // 2nd known ability, if any
    private String[]                 hiddenAbility; // Hidden ability
    private String[]                 type1;         // 1st type
    private String[]                 type2;         // 2nd type, if any
    private int[]                    height;        // Height (inches)
    private double[]                 weight;        // Weight (pounds)
    private String[]                 bio;           // Pokedex biography

    private int[]                    baseHP;        // base HP
    private int[]                    baseAttack;    // base attack
    private int[]                    baseDefense;   // base defense
    private int[]                    baseSpAttack;  // base special attack
    private int[]                    baseSpDefense; // base special defense
    private int[]                    baseSpeed;     // base speed
    
    private TreeMap<Integer, Move>[] movesToLearn;  // Level: move to learn
    private Move[][]                 machineMoves;  // Moves to learn from HM/TM
    private boolean[]                megaEvolve;    // Pokemon can mega-evolve
    private boolean[]                evolves;       // Pokemon has evolution
    private int                      evolveLvl;     // Level needed to evolve
                                                    // -1 = requires a stone

    private int[]                    catchRate;     // Catch rate at full HP

    private int[]                    numSeen;       // # of times seen
    private boolean[]                captured;      // Pokemon is captured

    private String[]                 eggGroup1;     // 1st breed
    private String[]                 eggGroup2;     // 2nd breed, if any
    private int[]                    minHatchTime;  // Min steps to hatch
    private int[]                    maxHatchTime;  // Max steps to hatch

    private String[]                 pokedexArt;    // Art on Pokedex
    private String                   frontSprite;   // Pokemon sprite file
    private String                   backSprite;    // Pokemon sprite file
    private String                   miniSprite;    // Sprite for inventory

    // Pokemon database constructor. Reads in csv file and builds the Pokedex
    public Pokedex() {

    }
}