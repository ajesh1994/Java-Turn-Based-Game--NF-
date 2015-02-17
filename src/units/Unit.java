package units;

import java.awt.image.BufferedImage;

public interface Unit {
	
	/**
	 * Method to get the unit's type (i.e. the class name)
	 * @return the unit type
	 */
	public String getUnitType();
	
	/**
	 * Method to get the unit's team number
	 * @return the associated team number
	 */
	public int getTeamNumber();
	
	/**
	 * Method to get the unit's player number
	 * @return the associated player number
	 */
	public int getPlayerNumber();
	
	/**
	 * Method to set the new position of the character
	 * @param x - the x-coordinate of the new position in pixels
	 * @param y - the y-coordinate of the new position in pixels
	 */
	public void setPosition(int x, int y);
	
	/**
	 * Method to get the x-coordinate of the character
	 * @return the x-coordinate of the character
	 */
	public int getX();
	
	/**
	 * Method to get the y-coordinate of the character
	 * @return the y-coordinate of the character
	 */
	public int getY();
	
	/**
	 * Method to get the character's max health
	 * @return the max health of the character
	 */
	public int getMaxHealth();
	
	/**
	 * Method to get the character's current health
	 * @return the current health of the character
	 */
	public int getCurrHealth();
	
	/**
	 * Method to modify the character's health after damage
	 * @param damage - the damage the character has taken
	 */
	public void setCurrHealth(int damage);
	
	/**
	 * Method to get the character's attack range
	 * @return the attack range
	 */
	public int getAttackRange();
	
	/**
	 * Method to calculate the character's randomly determined attack stat
	 * @return the randomly calculated attack stat
	 */
	public int getAttack();
	
	/**
	 * Method to get the character's movement range
	 * @return the movement range
	 */
	public int getMoveRange();
	
	/**
	 * Method to state if the character has been selected or not
	 * @param b - the selected state of the character
	 */
	public void setSelected(boolean b);
	
	/**
	 * Method to get the selected state of the character
	 * @return the selected state of the character
	 */
	public boolean getSelected();
	
	/**
	 * Method to get the character image
	 * @return the character image
	 */
	public BufferedImage getCharImg();
	
	/**
	 * Method to get the unit's gold cost
	 * @return the unit's gold cost
	 */
	public int getGoldCost();
	
	/**
	 * Method to get the unit's action point cost
	 * @return the unit's action point cost
	 */
	public int getActionCost();
	
}
