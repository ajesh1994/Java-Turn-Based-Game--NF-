package units;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Class to represent the character sprite
 * @author Helen
 *
 */
public class MeleeUnit implements Unit {
	
	/**Unit type*/
	private String unitType = "Melee";
	/**Team number of unit*/
	private int teamNo;
	/**Player number of unit*/
	private int playerNo;
	/**x-coordinate of sprite in pixels*/
	private int x;
	/**y-coordinate of sprite in pixels*/
	private int y;
	/**Max health of character*/
	private int maxHealth = 1000;
	/**Current health of character*/
	private int currHealth = maxHealth;
	/**Attack range of unit*/
	private int attackRange = 1;
	/**Minimum damage of unit*/
	private int minAttack = 500;
	/**Maximum damage of unit*/
	private int maxAttack = 1000;
	/**Movement range of character*/
	private int moveRange = 5;
	/**Shows if the character has been selected*/
	private boolean selected;
	/**Gold cost of unit*/
	private int goldCost = 75;
	/**Action cost of unit*/
	private int actionCost = 3;
	
	/**
	 * Constructor to set the game model, load the character image and set its default position
	 * @param game - the game model
	 */
	public MeleeUnit(int team, int player, int x, int y) {
		teamNo = team;
		playerNo = player;
		ImageClass.loadChar();
		setPosition(x, y);
		selected = false;
	}
	
	/**
	 * Method to get the unit's type (i.e. the class name)
	 * @return the unit type
	 */
	public String getUnitType() {
		return unitType;
	}
	
	/**
	 * Method to get the unit's team number
	 * @return the associated team number
	 */
	public int getTeamNumber() {
		return teamNo;
	}

	/**
	 * Method to get the unit's player number
	 * @return the associated player number
	 */
	public int getPlayerNumber() {
		return playerNo;
	}
	
	/**
	 * Method to set the new position of the character
	 * @param x - the x-coordinate of the new position in pixels
	 * @param y - the y-coordinate of the new position in pixels
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method to get the x-coordinate of the character
	 * @return the x-coordinate of the character
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Method to get the y-coordinate of the character
	 * @return the y-coordinate of the character
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Method to get the character's max health
	 * @return the max health of the character
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Method to get the character's current health
	 * @return the current health of the character
	 */
	public int getCurrHealth() {
		return currHealth;
	}
	
	/**
	 * Method to modify the character's health after damage
	 * @param damage - the damage the character has taken
	 */
	public void setCurrHealth(int damage) {
		currHealth = currHealth - damage;
	}
	
	/**
	 * Method to get the character's attack range
	 * @return the attack range
	 */
	public int getAttackRange() {
		return attackRange;
	}
	
	/**
	 * Method to calculate the character's randomly determined attack stat
	 * @return the randomly calculated attack stat
	 */
	public int getAttack() {
		Random r = new Random();
		int randomAtt = r.nextInt((maxAttack - minAttack) + 1) + minAttack;
		
		return randomAtt;
	}
	
	/**
	 * Method to get the character's movement range
	 * @return the movement range
	 */
	public int getMoveRange() {
		return moveRange;
	}

	
	/**
	 * Method to state if the character has been selected or not
	 * @param b - the selected state of the character
	 */
	public void setSelected(boolean b) {
		selected = b;
	}
	
	/**
	 * Method to get the selected state of the character
	 * @return the selected state of the character
	 */
	public boolean getSelected() {
		return selected;
	}
	
	/**
	 * Method to get the character image
	 * @return the character image
	 */
	public BufferedImage getCharImg() {
		return ImageClass.charImg;
	}
	
	/**
	 * Method to get the unit's gold cost
	 * @return the unit's gold cost
	 */
	public int getGoldCost() {
		return goldCost;
	}
	
	/**
	 * Method to get the unit's action point cost
	 * @return the unit's action point cost
	 */
	public int getActionCost() {
		return actionCost;
	}
}
