package main;

import panels.ActionMenuPanel;
import panels.ChatPanel;
import panels.GameStatusPanel;
import units.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Contains game mechanics and paints components;
 * @author Helen
 *
 */
@SuppressWarnings("serial")
public class Game extends JPanel implements MouseListener, Runnable {

	/**Enum to represent various game states, changes canvas accordingly*/
	public enum gameState {
        MAIN_MENU, LOBBY, OPTIONS_AUDIO, OPTIONS_VIDEO, OPTIONS_PLAYER, Game,
	}

	/**Enum to represent various unit types*/
	public enum UnitType {
		NONE, CommandCentre, Base, GoldMine, Melee
	};

	/**Main menu screen*/
	public static MainMenu menu;
    public static Lobby lobby;
    public static Options options;

	public static gameState state = gameState.MAIN_MENU;
	/**Initialise map*/
	private Map map = new Map(10, 10);
	/**Set tile size in pixels*/
	private static final int TILE_SIZE = 64;
	/**Array to store valid movement tiles*/
	private boolean[][] validMoves = new boolean[map.getWidth()][map.getHeight()];
	/**Map width in pixels*/
	private int gameWidth = TILE_SIZE * map.getWidth();
	/**Map height in pixels*/
	private int gameHeight = TILE_SIZE * map.getHeight() + TILE_SIZE;
	/**Set the threading status*/
	public static boolean running = false;

	/**Creates an array list of characters on the screen*/
	private ArrayList<Unit> units = new ArrayList<Unit>();
	/**Keeps track of player resources: 0 - gold, 1 - base, 2 - units*/
	private ArrayList<Integer> res = new ArrayList<Integer>();
	/**Represents if a character has been selected, only one character can be selected at a time*/
	private boolean selected = false;
	/**Represents the type of the selected unit*/
	private UnitType unitType;

	/**Action panel*/
	private ActionMenuPanel actionPanel;
	/**Represents the command that the game should do next*/
	private String command;
	/**Chat panel*/
	private ChatPanel chatPanel;
	/**Status panel*/
	private GameStatusPanel statusPanel;

	/**TODO: May change in future versions that allow parameter changing in lobby view*/
	/**Max number of gold*/
	private final int MAX_GOLD = 5000;
	/**Max number of buildings*/
	private final int MAX_BUILDINGS = 10;
	/**Max number of units*/
	private final int MAX_UNITS = 50;
	/**Action point total of user*/
	private int actionPoints = 10;
	/**Current action point total of user*/
	private int currActionPoints = actionPoints;

	/**The player's unique identification number*/
	private int playerNo;
	/**The number of the team the player is on*/
	private int	teamNo;
	/**When this is 0 it is this players turn*/
	private int myTurn;
	/**The total number of players in the game*/
	private int totPlayerNo;
	/**Keeps track of the total number of turns*/
	private int turnCount = 1;
	
	/**Placeholder integer for turn number*/
	private int turnNum = 1;

	/**
	 * Constructor to add a mouse listener and add new command centres
	 */
	public Game() {
		addMouseListener(this);

		/**Add new characters and set their positions*/
		addNewCentre(1, 1, 0, 0);
		addNewCentre(1, 1, 0, map.getHeight()-1);
		addNewCentre(1, 1, map.getWidth()-1, 0);
		addNewCentre(1, 1, map.getWidth()-1, map.getHeight()-1);
		menu = new MainMenu();
        lobby = new Lobby();
        options = new Options();

		res.add(500);
		res.add(0);
		res.add(0);
		
		this.addMouseListener(new MainMenu());
	}

	/**
	 * Method to paint the canvas
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(new Color(238, 243, 250));

        if (state == gameState.Game) {
            statusPanel.setVisible(true);
            actionPanel.setVisible(true);
            chatPanel.setVisible(true);
            Graphics2D g2 = (Graphics2D) g;
            int x = 0;
            int y = 0;
            /**Draws tiles in correct areas with correct spacing as well*/
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    g2.drawImage(ImageClass.resize(map.getImageTile(i, j), TILE_SIZE), x, y, this); //resizes and draws terrain tiles
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x, y, TILE_SIZE, TILE_SIZE); //draws grid lines
                    x += TILE_SIZE;
                }
                x = 0;
                y += TILE_SIZE;
            }
            y = 0;

            /**Draws character-related things*/
            for (int k = 0; k < units.size(); k++) {
                /**Draw character*/
                Unit next = units.get(k);
                g2.drawImage(ImageClass.resize(next.getCharImg(), TILE_SIZE), next.getX(), next.getY(), this); //resizes and draws character

                /**Draw stuff when character is selected*/
                if (next.getSelected() == true) {
                    g2.setColor(Color.RED);
                    g2.drawRect(next.getX(), next.getY(), TILE_SIZE, TILE_SIZE); //Draws red border around selected
                    g2.drawString(next.getCurrHealth() + "/" + next.getMaxHealth(), next.getX(), next.getY() + (TILE_SIZE / 6)); //Draws health stat in top part of tile

                    /**This draws the movement range of the character*/
                    for (int i = 0; i < validMoves.length; i++) {
                        for (int j = 0; j < validMoves.length; j++)
                            if (validMoves[i][j]) {
                                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); //Sets thickness of line
                                g2.drawRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE); //Draws movement range
                            }
                    }
                }
            }

            /**Else draw main menu if game state is set to main menu*/
        } else if (state == gameState.MAIN_MENU){
			menu.render(g);
		} else if (state == gameState.OPTIONS_PLAYER) {
            setBackground(Color.WHITE);
            options.render(g);
        }
	}

	/**
	 * Method to stop the thread running
	 */
	public synchronized void gameStop(){

		if (!running) {
			return;
		}
		running = false;
	}

	/**
	 * Method to constantly repaint canvas
	 */
	public void run() {

		if (running) {
			return;
		}
		running = true;
		while(running) {
			repaint();
		}

		gameStop();
		return;
	}


	/**
	 * Method executed when user clicks mouse
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {

		/**Get position of mouse where user clicked*/
		int mouseX = arg0.getX();
		int mouseY = arg0.getY();

		/**This command is number 2 or 3, or "create new melee unit" or "no action selected"*/
		if (command == actionPanel.getCommands().get(0) || command == actionPanel.getCommands().get(3)) {

			for (int k = 0; k < units.size(); k++) {
				Unit next = units.get(k);

				/**Get position of all characters*/
				int charX = next.getX();
				int charY = next.getY();
				int charMaxX = next.getX() + TILE_SIZE;
				int charMaxY = next.getY() + TILE_SIZE;
				int x = next.getX() / TILE_SIZE; //Calculates x-position in array
				int y = next.getY() / TILE_SIZE; //Calculates y-position in array

				/**Detect if mouse clicked within a character tile and nothing is selected at the moment*/
				if ((mouseX >= charX && mouseX <= charMaxX) && (mouseY >= charY && mouseY <= charMaxY) && !next.getSelected() && !selected && command == actionPanel.getCommands().get(3)) {
					next.setSelected(true);
					selected = true;
					updateStatusPanel(next);

					/**Create map of where the selected character can move*/
					for (int i = 0; i <= next.getMoveRange(); i++) { //Based on character's move range
						for (int j = next.getMoveRange(); j >= 0; j--) { //Discerns diagonal movement
							if ((x-i) >= 0 && (y+j) < map.getHeight() && i+j <= 5) { //Boundary checking for bottom-right
								validMoves[(x-i)][y+j] = true; //Add positions diagonally bottom-left of character
							}	
							if ((x+i) < map.getWidth() && (y+j) < map.getHeight()  && i+j <= 5) {
								validMoves[(x+i)][y+j] = true; //Add positions right of character and left of map boundary
							}
							if ((x-j) >= 0 && (y-i) >= 0 && i+j <= 5) {
								validMoves[x-j][(y-i)] = true; //Add positions above character and below map boundary
							}
							if ((x+j) < map.getWidth() && (y-i) >= 0 && i+j <= 5) {
								validMoves[x+j][(y-i)] = true; //Add positions diagonally top-right of character
							}
						}

						k = units.size(); //sets k to max so it stops looping
					}

					/**Case if some character is already selected and clicked area is within its movement range*/
				} else if (next.getSelected() && selected && command == actionPanel.getCommands().get(0)) {
					int mouseTileX = mouseX / TILE_SIZE;
					int mouseTileY = mouseY / TILE_SIZE;
					int baseX = next.getX() / TILE_SIZE;
					int baseY = next.getY() / TILE_SIZE;

					if (mouseTileX - 1 == baseX || mouseTileX + 1 == baseX || mouseTileY - 1 == baseY || mouseTileY + 1 == baseY) {
						addNewMeleeUnit(1, 1, mouseTileX, mouseTileY);
						updateStatusPanel();
						next.setSelected(false);
						selected = false;
					} else {
						JOptionPane.showMessageDialog(this, "You cannot place a unit here");
					}
				} else if (next.getSelected() && validMoves[mouseX / TILE_SIZE][mouseY / TILE_SIZE] && selected) {

					/**Checks if this tile is already being occupied by another character*/
					if (map.getMapStatus()[mouseX / TILE_SIZE][mouseY / TILE_SIZE]) {

						/**Checks if this tile is occupied by the character that the user is trying to move*/
						if ((mouseX >= charX && mouseX <= charMaxX) && (mouseY >= charY && mouseY <= charMaxY) && units.get(k).getSelected()) {
							units.get(k).setSelected(false); //deselects character
							selected = false;
							validMoves = new boolean[map.getWidth()][map.getHeight()]; //reinitialises valid move array

							k = units.size(); //sets k to max so it stops looping

							/**Else - tile is occupied by another character that isn't itself*/
						} else {

							/**Attempts to determine which character the user has selected*/
							for (int l = 0; l < units.size(); l++) {
								if (mouseX / TILE_SIZE == units.get(l).getX() / TILE_SIZE && mouseY / TILE_SIZE == units.get(l).getY() / TILE_SIZE && !units.get(l).getSelected()) {
									int opponentXPix = units.get(l).getX(); //opponent character x-position in pixels
									int opponentYPix = units.get(l).getY(); //opponent character y-position in pixels
									int opponentX = opponentXPix / TILE_SIZE; //opponent character x-position in tiles
									int opponentY = opponentYPix / TILE_SIZE; //opponent character y-position in tiles

									map.setOccupiedTile(x, y, false);
									int[] newPos = determinePosition(next.getAttackRange(), x, y, opponentX, opponentY); //determines best spot to move to
									if (!(newPos[0] == -1) && !(newPos[1] == -1) && currActionPoints >= 3) { //-1 are default values
										moveUnit(units.get(k), x, y, newPos[0], newPos[1]);
										startBattle(units.get(k), units.get(l)); //initiates battle sequence
										currActionPoints = currActionPoints - 3;
									} else {
										JOptionPane.showMessageDialog(this, "You don't have enough action points");
									}
								}	
							}
						}
						k = units.size(); //sets k to max so it doesn't loop again
					}

					/**Else - location clicked is not currently occupied by anything*/
				} else if (next.getSelected()){
					int newX = mouseX / TILE_SIZE;//calculates new x-position in tiles
					int newY = mouseY / TILE_SIZE; //calculates new y-position in tiles

					moveUnit(next, charX / TILE_SIZE, charY / TILE_SIZE, newX, newY);
					k = units.size(); //sets k to max so it doesn't loop again
				}
			}	

			/**Else command is number 2, or "create new gold mine"*/
		} else if (command == actionPanel.getCommands().get(2)) {
			int newX = mouseX / TILE_SIZE;//calculates new x-position in tiles
			int newY = mouseY / TILE_SIZE; //calculates new y-position in tiles

			addNewMine(1, 1, newX, newY);
			updateStatusPanel();

			command = actionPanel.getCommands().get(3); //reset command stats

			/**Else command is number 1, or "create new base"*/
		} else if (command == actionPanel.getCommands().get(1)) {
			int newX = mouseX / TILE_SIZE;//calculates new x-position in tiles
			int newY = mouseY / TILE_SIZE; //calculates new y-position in tiles

			addNewBase(1, 1, newX, newY);
			updateStatusPanel();

			command = actionPanel.getCommands().get(3); //reset command status
			/**Else command is number 0, or "create new unit"*/
		} else if (command == actionPanel.getCommands().get(0)) {
			JOptionPane.showMessageDialog(this, "You don't have a base selected");
		}
	}	



	@Override
	public void mouseEntered(MouseEvent arg0) {
		//when user mouse enters area, unused currently
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//when user mouse leaves area, unused currently
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		//when user presses mouse on component, unused currently

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//when user releases mouse on component, unused currently
	}

	/**
	 * Method to add a new command centre to the array list, set their position and occupy corresponding square
	 * @param x - the x-position in tiles
	 * @param y - the y-position in tiles
	 */
	public void addNewCentre(int team, int player, int x, int y) {
		if (!map.getMapStatus()[x][y]) {
			CommandCentre newCentre = new CommandCentre(team, player, x * TILE_SIZE, y * TILE_SIZE); //set position in pixels
			units.add(newCentre); //add character to array list
			map.setOccupiedTile(x, y, true); //set occupied tile
		} else {
			JOptionPane.showMessageDialog(this, "You cannot place a command centre here");
		}
	}

	/**
	 * Method to add a new character to the array list, set their position and occupy corresponding square
	 * @param x - the x-position in tiles
	 * @param y - the y-position in tiles
	 */
	public void addNewMeleeUnit(int team, int player, int x, int y) {
		if (res.get(2) < MAX_UNITS) {
			if (!map.getMapStatus()[x][y]) {
				MeleeUnit newMelee = new MeleeUnit(team, player, x * TILE_SIZE, y * TILE_SIZE); //set position in pixels
				if (currActionPoints >= newMelee.getActionCost() && res.get(0) >= newMelee.getGoldCost()) {
					units.add(newMelee); //add character to array list
					map.setOccupiedTile(x, y, true); //set occupied tile
					currActionPoints = currActionPoints - newMelee.getActionCost();
					res.set(0, res.get(0) - newMelee.getGoldCost());
					res.set(2, res.get(2)+1);
				} else {
					JOptionPane.showMessageDialog(this, "You don't have enough gold/action points");
				}
			} else {
				JOptionPane.showMessageDialog(this, "You cannot place a unit here");
			}
		} else {
			JOptionPane.showMessageDialog(this, "You have reached the unit cap");
		}
	}

	/**
	 * Method to add a new base to the array list, set their position and occupy corresponding square
	 * @param x - the x-position in tiles
	 * @param y - the y-position in tiles
	 */
	public void addNewBase(int team, int player, int x, int y) {
		if (res.get(1) < MAX_BUILDINGS) {
			if (!map.getMapStatus()[x][y]) {
				Base newBase = new Base(team, player, x * TILE_SIZE, y * TILE_SIZE); //set position in pixels
				if (currActionPoints >= newBase.getActionCost() && res.get(0) >= newBase.getGoldCost()) {
					units.add(newBase); //add base to array list
					map.setOccupiedTile(x, y, true); //set occupied tile
					currActionPoints = currActionPoints - newBase.getActionCost();
					res.set(0, res.get(0) - newBase.getGoldCost());
					res.set(1, res.get(1)+1);
				} else {
					JOptionPane.showMessageDialog(this, "You don't have enough gold/action points");
				}
			} else {
				JOptionPane.showMessageDialog(this, "You cannot place a base");
			}
		} else {
			JOptionPane.showMessageDialog(this, "You have reached the building cap");
		}
	}

	/**
	 * Method to add a new base to the array list, set their position and occupy corresponding square
	 * @param x - the x-position in tiles
	 * @param y - the y-position in tiles
	 */
	public void addNewMine(int team, int player, int x, int y) {
		if (res.get(1) < MAX_BUILDINGS) {
			if (!map.getMapStatus()[x][y]) {
				GoldMine newMine = new GoldMine(team, player, x * TILE_SIZE, y * TILE_SIZE); //set position in pixels
				if (currActionPoints >= newMine.getActionCost() && res.get(0) >= newMine.getGoldCost()) {
					units.add(newMine); //add base to array list
					map.setOccupiedTile(x, y, true); //set occupied tile
					currActionPoints = currActionPoints - newMine.getActionCost();
					res.set(0, res.get(0) - newMine.getGoldCost());
					res.set(1, res.get(1)+1);
				} else {
					JOptionPane.showMessageDialog(this, "You don't have enough gold/action points");
				}
			} else {
				JOptionPane.showMessageDialog(this, "You cannot place a gold mine here");
			}
		} else {
			JOptionPane.showMessageDialog(this, "You have reached the building cap");
		}
	}

	/**
	 * Method to set the unit type selected
	 * @param type - the type selected
	 */
	public void setUnitType(UnitType type) {
		unitType = type;
	}
	/**
	 * Method to return the game screen width in pixels
	 * @return the width of the game
	 */
	public int getGameWidth() {
		return gameWidth;
	}

	/**
	 * Method to return the game screen height pixels
	 * @return the height of the game
	 */
	public int getGameHeight() {
		return gameHeight;
	}

	/**
	 * Method to set the current command of the game
	 * @param s - the string from the corresponding button clicked
	 */
	public void setCommand(String s) {
		command = s;
	}

	/**
	 * Method to get the current command of the game
	 * @return the current command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Method to determine the best position to attack a selected unit
	 * @param range - the attack range of the character
	 * @param currX - the x-position of the character in pixels
	 * @param currY - the y-position of the character in pixels
	 * @param enemyX - the x-position of the enemy in pixels
	 * @param enemyY - the y-position of the enemy in pixels
	 * @return an int array storing the new valid location that the character can move to
	 */
	public int[] determinePosition(int range, int currX, int currY, int enemyX, int enemyY) {
		int[] position = new int[]{-1, -1};

		/**Determines the closest that the selected character can attack from in tiles*/
		int enemyLeft = enemyX - range;
		int enemyRight = enemyX + range;
		int enemyAbove = enemyY - range;
		int enemyBelow = enemyY + range;

		/**Some very long code to determine the best place to move a character when it attempts to attack another character*/
		/**Selected character is top-left of opponent*/
		if (currX < enemyX && currY < enemyY) {
			if (!map.getMapStatus()[enemyLeft][enemyY] && validMoves[enemyLeft][enemyY]) {
				position[0] = enemyLeft;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else if (!map.getMapStatus()[enemyRight][enemyX]&& validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
			/**Selected character is directly above opponent*/
		} else if (currX == enemyX && currY < enemyY) {
			if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else if (!map.getMapStatus()[enemyLeft][enemyY] && validMoves[enemyLeft][enemyY]) {
				position[0] = enemyLeft;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyRight][enemyX]&& validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
			/**Selected character is top-right of opponent*/
		} else if (currX > enemyX && currY < enemyY) {
			if (!map.getMapStatus()[enemyRight][enemyY] && validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else if (!map.getMapStatus()[enemyLeft][enemyX]&& validMoves[enemyLeft][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
			/**Selected character directly left of opponent*/
		} else if (currX < enemyX && currY == enemyY) {
			if (!map.getMapStatus()[enemyLeft][enemyY] && validMoves[enemyLeft][enemyY]) {
				position[0] = enemyLeft;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else if (!map.getMapStatus()[enemyRight][enemyX]&& validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
			/**Selected character is directly right of opponent*/
		} else if (currX > enemyX && currY == enemyY) {
			if (!map.getMapStatus()[enemyRight][enemyY] && validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else if (!map.getMapStatus()[enemyLeft][enemyX]&& validMoves[enemyLeft][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
			/**Selected character is bottom-left of opponent*/
		} else if (currX < enemyX && currY > enemyY) {
			if (!map.getMapStatus()[enemyLeft][enemyY] && validMoves[enemyLeft][enemyY]) {
				position[0] = enemyLeft;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else if (!map.getMapStatus()[enemyRight][enemyX]&& validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
			/**Selected character is directly below opponent*/
		} else if (currX == enemyX && currY > enemyY) {
			if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else if (!map.getMapStatus()[enemyLeft][enemyY] && validMoves[enemyLeft][enemyY]) {
				position[0] = enemyLeft;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyRight][enemyX]&& validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
			/**Selected character is bottom-right of opponent*/
		} else if (currX > enemyX && currY > enemyY) {
			if (!map.getMapStatus()[enemyRight][enemyY] && validMoves[enemyRight][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else if (!map.getMapStatus()[enemyX][enemyBelow] && validMoves[enemyX][enemyBelow]) {
				position[0] = enemyX;
				position[1] = enemyBelow;
			} else if (!map.getMapStatus()[enemyX][enemyAbove] && validMoves[enemyX][enemyAbove]) {
				position[0] = enemyX;
				position[1] = enemyAbove;
			} else if (!map.getMapStatus()[enemyLeft][enemyX]&& validMoves[enemyLeft][enemyY]) {
				position[0] = enemyRight;
				position[1] = enemyY;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid space to move");
			}
		}
		return position;
	}

	/**
	 * Method to set the unit's new position in tiles prior to a battle
	 * @param u - the unit involved
	 * @param oldX - the old x-position of the unit in tiles
	 * @param oldY - the old y-position of the unit in tiles
	 * @param newX - the new x-position of the unit in tiles
	 * @param newY - the new y-position of the unit in tiles
	 */
	public void moveUnit(Unit u, int oldX, int oldY, int newX, int newY) {
		if (currActionPoints >= 1) {
			map.setOccupiedTile(oldX, oldY, false);
			//u.setPosition(newX * TILE_SIZE, newY * TILE_SIZE);
			moveUnitAnimation(u, oldX, oldY, newX, newY);
			map.setOccupiedTile(newX, newY, true);
			validMoves = new boolean[map.getWidth()][map.getHeight()];
			currActionPoints--;
		} else {
			JOptionPane.showMessageDialog(this, "You don't have enough action points");
		}
	}

	/**
	 * Method to move the character visually, currently doesn't work as it needs to be threaded
	 * @param u - the unit to be moved
	 * @param oldX - the old x-position of the unit
	 * @param oldY - the old y-position of the unit
	 * @param newX - the new x-position of the unit
	 * @param newY - the new y-position of the unit
	 */
	public void moveUnitAnimation(Unit u, int oldX, int oldY, int newX, int newY) {
		u.setSelected(false);
		selected = false;
		int xToMove = newX - oldX;
		int yToMove = newY - oldY;

		if (xToMove > 0) {
			while (xToMove > 0) {
				u.setPosition((oldX * TILE_SIZE) + TILE_SIZE, oldY * TILE_SIZE);
				oldX++;
				xToMove--;
				System.out.println(u.getX() + ", " + u.getY());
			}
		} else {
			while (xToMove != 0) {
				u.setPosition((oldX * TILE_SIZE) - TILE_SIZE, oldY * TILE_SIZE);
				oldX--;
				xToMove++;
				repaint();
			}
		}

		if (yToMove > 0) {
			while (yToMove > 0) {
				u.setPosition(oldX * TILE_SIZE, (oldY * TILE_SIZE) + TILE_SIZE);
				oldY++;
				yToMove--;
				System.out.println(u.getX() + ", " + u.getY());
				repaint();
			}
		} else {
			while (yToMove != 0) {
				u.setPosition(oldX * TILE_SIZE, (oldY * TILE_SIZE) - TILE_SIZE);
				oldY--;
				yToMove++;
				repaint();
			}
		}
	}

	/**
	 * Method to engage two characters in combat
	 * @param c1 - the character that began combat
	 * @param c2 - the character that was challenged
	 */
	public void startBattle(Unit c1, Unit c2) {
		/**Attacker gets to attack first*/
		int attackC1 = c1.getAttack();
		c2.setCurrHealth(attackC1);

		/**If defender survives attack, it gets to attack back*/
		if (c2.getCurrHealth() > 0) {
			int attackC2 = c2.getAttack();
			c1.setCurrHealth(attackC2);

			if (c1.getCurrHealth() < 0) {
				int curr = units.indexOf(c1);
				map.setOccupiedTile(units.get(curr).getX() / TILE_SIZE, units.get(curr).getY() / TILE_SIZE, false);
				units.remove(curr);
				res.set(2, res.get(2) - 1);
			}
		} else {
			int curr = units.indexOf(c2);
			map.setOccupiedTile(units.get(curr).getX() / TILE_SIZE, units.get(curr).getY() / TILE_SIZE, false);
			units.remove(curr);
			res.set(2, res.get(2) - 1);
		}
		updateStatusPanel();
	}

	/**
	 * Method to set the action panel
	 * @param p - the given panel
	 */
	public void setActionPanel(ActionMenuPanel p) {
		actionPanel = p;
		command = actionPanel.getCommands().get(3); //sets default command to cancel
	}

	/**
	 * Method to set the status panel
	 * @param p - the given panel
	 */
	public void setStatusPanel(GameStatusPanel p) {
		statusPanel = p;
	}

	/**
	 * Method to set the chat panel
	 * @param p - the given panel
	 */
	public void setChatPanel(ChatPanel p) {
		chatPanel = p;
	}

	

	//    public Unit getSelectedChar() {
	//
	//        for (Unit u : units) {
	//
	//            if (u.getSelected()) {
	//
	//                return u;
	//
	//            }
	//
	//        }
	//
	//        return null;
	//
	//    }

    /**
     * Updates the fields in GameStatusPanel.
     */
	public void updateStatusPanel() {

		statusPanel.setGoldAmount(res.get(0));
		statusPanel.setBaseNumber(res.get(1));
		statusPanel.setUnitNumber(res.get(2));
		statusPanel.setTurnNumber(turnNum);
		statusPanel.setPoints(currActionPoints, actionPoints);

	}

    /**
     * Updates the fields in GameStatusPanel.
     * @param n The selected unit.
     */
	private void updateStatusPanel(Unit n) {

		statusPanel.setGoldAmount(res.get(0));
        if (selected) {
            statusPanel.setHP(getSelectedChar().getCurrHealth());
        } else {
            statusPanel.setHP(0);
        }
		statusPanel.setHP(n.getCurrHealth());
		statusPanel.setBaseNumber(res.get(1));
		statusPanel.setUnitNumber(res.get(2));
		statusPanel.setTurnNumber(turnNum);
		statusPanel.setPoints(currActionPoints, actionPoints);

	}

    /**
     * Obtains the currently selected unit/building on the map.
     * @return Currently selected unit/building.
     */
	private Unit getSelectedChar() {
		for (Unit c : units) {
			if (c.getSelected()) {
				return c;
			}
		}
		return null;
	}

    /**
     * Calculates the number of units on the map.
     * @return The number of units on the map.
     */
	private int getUnitNum() {

		int units = 0;
		for (Unit c : this.units) {
			if (c instanceof MeleeUnit) {
				units++;
			}
		}
		return units;

	}

    /**
     * Calculates the number of bases on the map.
     * @return The number of bases on the map.
     */
	private int getBaseNum() {

		int bases = 0;
		for (Unit b : this.units) {
			if (b instanceof Base) {
				bases++;
			}
		}
		return bases;

	}
	
	//--------------------- TEST METHODS AFTER HERE
	
	/**
	 * Method to get the array list of units, for testing purposes
	 * @return the array list of units
	 */
	public ArrayList<Unit> getUnits() {
		return units;
	}

	/**
	 * Method to get the array list of resources, for testing purposes
	 * @return the array list of resources
	 */
	public ArrayList<Integer> getRes() {
		return res;
	}
	
	/**
	 * Method to get the tile size, for testing purposes
	 * @return the tile size
	 */
	public int getTileSize() {
		return TILE_SIZE;
	}

	/**
	 * Method to get the action points, for testing purposes
	 * @return the action points
	 */
	public int[] getActionPoints() {
		return new int[]{currActionPoints, actionPoints};
	}
	

	/**
	 * Sets the turn, team and player data for the player
	 * @param playerNo The number of this player, for identifying units
	 * @param teamNo The team number of this player, for combat
	 * @param untilMyTurn Number of turns until this players turn
	 * @param totalPlayerNo Total number of players in the game
	 */
	public void setTurnData(int playerNo, int teamNo, int untilMyTurn, int totalPlayerNo) {
		this.playerNo = playerNo;
		this.teamNo = teamNo;
		this.myTurn = untilMyTurn;
		this.totPlayerNo = totalPlayerNo;
	}

	/**
	 * Method to end the current players turn
	 * Currently unused here, to be implemented in the networked version
	 */
	public void endTurn() {
		System.out.println("Ending turn...");
		 currActionPoints = actionPoints;
		 updateStatusPanel();
		 
		 for (int i = 0; i < units.size(); i++) {
				units.get(i).setSelected(false);
			}

			selected = false;
			updateTurn();
			if (myTurn == 0) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
	}
	
	
	/**
	 * Updates local game turn data
	 */
	public void updateTurn() {

		System.out.println("Updating turn data");

		if (myTurn == 0) {
			myTurn = totPlayerNo-1;
		} else {
			myTurn--;
		}

		for (int i = 0; i < units.size(); i++) {
			if (units.get(i).getUnitType() == "GoldMine" || units.get(i).getUnitType() == "Base") {
				String type = units.get(i).getUnitType();
				if (units.get(i).getPlayerNumber() == playerNo) {
					if (type == "GoldMine") {
						if (res.get(0) + ((GoldMine) units.get(i)).getGained() < MAX_GOLD) {
							res.set(0, res.get(0) + ((GoldMine) units.get(i)).getGained());
						} else {
							res.set(0, MAX_GOLD);
						}
					} else {
						if (res.get(0) + ((Base) units.get(i)).getGained() < MAX_GOLD) {
							res.set(0, res.get(0) + ((Base) units.get(i)).getGained());
						} else {
							res.set(0, MAX_GOLD);
						}
					}
				}
			}
		}

		turnCount++;
		currActionPoints = 0;

		/**If this player's turn is next*/
		if (myTurn == 0) {
			JOptionPane.showMessageDialog(this, "It is your turn");
			currActionPoints = actionPoints;
		}

		updateStatusPanel();

	}

	/**
	 * Only used at the beginning of the game to notify of turn (Remove?)
	 */
	public void notifyTurn() {
		if (myTurn == 0) {
			JOptionPane.showMessageDialog(this, "It is your turn");
		} else {
			JOptionPane.showMessageDialog(this, "You do not have the first turn");
		}
		updateStatusPanel();
	}

}