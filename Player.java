/*
 * Contains all information attached to the player
 */

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.ArrayList;

public class Player {
	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 50;
	public static final int STEP_SIZE = 50;
    public static final int NUM_SPRITES = 18;

    // CONSTANTS FOR SPRITE NAMES
    public static final int FRONT_STOP = 10;
    public static final int FRONT_WALK_1 = 1;
    public static final int FRONT_WALK_2 = 2;
    public static final int FRONT_RUN_1 = 3;
    public static final int FRONT_RUN_2 = 4;
    public static final int BACK_STOP = 5;
    public static final int BACK_WALK_1 = 6;
    public static final int BACK_WALK_2 = 7;
    public static final int BACK_RUN_1 = 8;
    public static final int BACK_RUN_2 = 9;
    public static final int LEFT_STOP = 0;
    public static final int LEFT_WALK = 11;
    public static final int LEFT_RUN_1 = 12;
    public static final int LEFT_RUN_2 = 13;
    public static final int RIGHT_STOP = 14;
    public static final int RIGHT_WALK = 15;
    public static final int RIGHT_RUN_1 = 16;
    public static final int RIGHT_RUN_2 = 17;

	private String name;
    private String[] spriteFilenames;
	private BufferedImage[] sprites;
    private BufferedImage curSprite;
	private int posX, posY;
	private int legs;

	// private Pokemon[6] pokemonTeam;

	public Player(int x, int y) {
		posX = x;
		posY = y;
		legs = 0;
		sprites = new BufferedImage[NUM_SPRITES];
		spriteFilenames = new String[NUM_SPRITES];
		int i = 0;
		spriteFilenames[FRONT_STOP]   = "f-stop.png";
		spriteFilenames[FRONT_WALK_1] = "f-walk-1.png";
		spriteFilenames[FRONT_WALK_2] = "f-walk-2.png";
		spriteFilenames[FRONT_RUN_1]  = "f-run-1.png";
		spriteFilenames[FRONT_RUN_2]  = "f-run-2.png";
		spriteFilenames[BACK_STOP]    = "b-stop.png";
		spriteFilenames[BACK_WALK_1]  = "b-walk-1.png";
		spriteFilenames[BACK_WALK_2]  = "b-walk-2.png";
		spriteFilenames[BACK_RUN_1]   = "b-run-1.png";
		spriteFilenames[BACK_RUN_2]   = "b-run-2.png";
		spriteFilenames[LEFT_STOP]    = "l-stop.png";
		spriteFilenames[LEFT_WALK]    = "l-walk.png";
		spriteFilenames[LEFT_RUN_1]   = "l-run-1.png";
		spriteFilenames[LEFT_RUN_2]   = "l-run-2.png";
		spriteFilenames[RIGHT_STOP]   = "r-stop.png";
		spriteFilenames[RIGHT_WALK]   = "r-walk.png";
		spriteFilenames[RIGHT_RUN_1]  = "r-run-1.png";
		spriteFilenames[RIGHT_RUN_2]  = "r-run-2.png";

		// Load the sprites
        try {
        	sprites[FRONT_STOP] = ImageIO.read(new File("pics" + File.separator
            	+ "trainer" + File.separator + spriteFilenames[FRONT_STOP]));
        	i++;
            sprites[FRONT_WALK_1] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[FRONT_WALK_1]));
            i++;
            sprites[FRONT_WALK_2] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[FRONT_WALK_2]));
            i++;
            sprites[FRONT_RUN_1] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[FRONT_RUN_1]));
            i++;
            sprites[FRONT_RUN_2] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[FRONT_RUN_2]));
            i++;
            sprites[BACK_STOP] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[BACK_STOP]));
            i++;
            sprites[BACK_WALK_1] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[BACK_WALK_1]));
            i++;
            sprites[BACK_WALK_2] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[BACK_WALK_2]));
            i++;
            sprites[BACK_RUN_1] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[BACK_RUN_1]));
            i++;
            sprites[BACK_RUN_2] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[BACK_RUN_2]));
            i++;
            sprites[LEFT_STOP] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[LEFT_STOP]));
            i++;
            sprites[LEFT_WALK] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[LEFT_WALK]));
            i++;
            sprites[LEFT_RUN_1] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[LEFT_RUN_1]));
            i++;
            sprites[LEFT_RUN_2] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[LEFT_RUN_2]));
            i++;
            sprites[RIGHT_STOP] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[RIGHT_STOP]));
            i++;
            sprites[RIGHT_WALK] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[RIGHT_WALK]));
            i++;
            sprites[RIGHT_RUN_1] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[RIGHT_RUN_1]));
            i++;
            sprites[RIGHT_RUN_2] = ImageIO.read(new File("pics" + File.separator
                + "trainer" + File.separator + spriteFilenames[RIGHT_RUN_2]));
        }
        catch (IOException e) {
            System.out.println("Error reading sprite file " + spriteFilenames[i]);
        }

        curSprite = sprites[FRONT_STOP];
	}

	// Getters
	public BufferedImage getSprite() {
		return curSprite;
	}

	public int getX() { 
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int getLegs() {
    	return legs;
    }

	// Setters
	public void updateSprite(int val) {
		curSprite = sprites[val];
	}

	public void resetLegs() {
		legs = 0;
	}

	public void switchLegs() {
    	legs++;
    	legs = legs % 4;
    }

	// Adjusting the position on player moving left
	public boolean moveLeft(PokemonMap map, ArrayList<Integer> keys) {
        boolean move = false;

        if (posX >= STEP_SIZE) {
            int nextMValue = map.getMValue(map.getMY(), map.getMX() - 1);

            move = canMove(nextMValue);

            if (move) {
                posX -= STEP_SIZE;
                map.updateMX(-1);

                System.out.println("mX: " + map.getMX());
                System.out.println("mY: " + map.getMY());
                System.out.println("Current matrix value: " + map.curMValue());
			}

			// switch legs in animation
            if(keys.contains(PokemonGame.RUN_KEY)) {
                if(legs % 2 == 0) {
                    updateSprite(LEFT_RUN_1);
                }
                else {
                    updateSprite(LEFT_RUN_2);
                }
            }
            else {
                if(legs % 2 == 0) {
                    updateSprite(LEFT_WALK);
                }
                else {
                    updateSprite(LEFT_STOP);
                }
            }

            switchLegs();   
		}

		return move;
	}

	// Adjusting the position on player moving right
	public boolean moveRight(PokemonMap map, ArrayList<Integer> keys) {
		boolean move = false;

		if (posX <= PokemonGame.PANEL_WIDTH - 2 * STEP_SIZE) {
			int nextMValue = map.getMValue(map.getMY(), map.getMX() + 1);
                            
            move = canMove(nextMValue);

            if (move) {
            	posX += STEP_SIZE;
				map.updateMX(1);

				System.out.println("mX: " + map.getMX());
				System.out.println("mY: " + map.getMY());
				System.out.println("Current matrix value: " + map.curMValue());
			}

			// switch legs in animation
            if(keys.contains(PokemonGame.RUN_KEY)) {
                if(legs % 2 == 0) {
                    updateSprite(RIGHT_RUN_1);
                }
                else {
                    updateSprite(RIGHT_RUN_2);
                }
            }
            else {
                if(legs % 2 == 0) {
                    updateSprite(RIGHT_WALK);
                }
                else {
                    updateSprite(RIGHT_STOP);
                }
            }

            switchLegs(); 
		}

		return move;
	}

    // Adjusting the position on player moving left
    public boolean moveUp(PokemonMap map, ArrayList<Integer> keys) {
        boolean move = false;

        if (posY >= STEP_SIZE) {
            int nextMValue = map.getMValue(map.getMY() - 1, map.getMX());
                            
            move = canMove(nextMValue);

            if (move) {
                posY -= STEP_SIZE;
                map.updateMY(-1);

                System.out.println("mX: " + map.getMX());
                System.out.println("mY: " + map.getMY());
                System.out.println("Current matrix value: " + map.curMValue());
            }

            if(keys.contains(PokemonGame.RUN_KEY)) {
                if(legs % 2 == 0) {
                    updateSprite(BACK_RUN_1);
                }
                else {
                    updateSprite(BACK_RUN_2);
                }
            }
            else {
                if(legs == 0) {
                    updateSprite(BACK_WALK_1);
                }
                else if(legs == 1){
                    updateSprite(BACK_STOP);
                }
                else if(legs == 2){
                    updateSprite(BACK_WALK_2);
                }
                else {
                    updateSprite(BACK_STOP);
                }
            }

            switchLegs();
		}

        return move;
	}

	public boolean moveDown(PokemonMap map, ArrayList<Integer> keys) {
		boolean move = false;

		if (posY <= PokemonGame.PANEL_HEIGHT - 2 * STEP_SIZE) {
			int nextMValue = map.getMValue(map.getMY() + 1, map.getMX());
                            
            move = canMove(nextMValue);

            if (move) {
            	posY += STEP_SIZE;
				map.updateMY(1);

				System.out.println("mX: " + map.getMX());
				System.out.println("mY: " + map.getMY());
				System.out.println("Current matrix value: " + map.curMValue());
			}

            if(keys.contains(PokemonGame.RUN_KEY)) {
                if(legs % 2 == 0) {
                    updateSprite(LEFT_RUN_1);
                }
                else {
                    updateSprite(LEFT_RUN_2);
                }
            }
            else {
    			if(legs == 0) {
                    updateSprite(FRONT_WALK_1);
                }
                else if(legs == 1){
                    updateSprite(FRONT_STOP);
                }
                else if(legs == 2){
                    updateSprite(FRONT_WALK_2);
                }
                else {
                    updateSprite(FRONT_STOP);
                }
            }

            switchLegs();
		}

		return move;
	}

	private boolean canMove(int nextValue) {
        switch(nextValue) {
            case PokemonMap.GRASS:
                return true;

            case PokemonMap.WALL:
                return false;

            case PokemonMap.WEED:
                return true;
            }

        return true;
    }
}