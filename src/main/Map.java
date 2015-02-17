package main;

import java.awt.image.BufferedImage;

import units.ImageClass;

/**
 * Creates and displays the map
 * @author Helen
 *
 */
public class Map {
	
	/**Width of map in tiles*/
	private int width;
	/**Height of map in tiles*/
	private int height;
	/**Data structure for map*/
	private int[][] map;
	/**Data structure for map to show if tile is occupied or not*/
	private boolean[][] bmap;
	
	/**
	 * Constructor to create a new tile map and boolean map, and load the terrain tiles
	 * @param width - the width of the map
	 * @param height - the height of the map
	 */
	public Map(int width, int height) {

		this.width = width;
		this.height = height;
		
		map =  new int[width][height];
		/**
		for (int i = 0; i < width; i++) {
			map[i][0] = 1; //Left column of map
			map[i][height-1] = 1; //Right column of map
		}
		*/
		bmap = new boolean[width][height];
		
		ImageClass.loadTiles(); //Load images for tiles
	}
	
	/**
	 * Method to get the height of the whole map
	 * @return the map height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Method to get the width of the whole map
	 * @return the map width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Method to set the occupied status of a tile
	 * @param b - the status of the tile
	 * @param x - the x-position of the tile
	 * @param y - the y-position of the tile
	 */
	public void setOccupiedTile(int x, int y, boolean b) {
		bmap[x][y] = b;
	}
	
	/**
	 * Method to get the boolean map of occupied tiles
	 * @return the boolean map
	 */
	public boolean[][] getMapStatus() {
		return bmap;
	}
	
	/**
	 * Method to return the corresponding tile 
	 * @param x - The x-coordinate of the tile
	 * @param y - The y-coordinate of the tile
	 * @return the corresponding terrain tile
	 */
	public BufferedImage getImageTile(int x, int y) {
		if (map[x][y] == 0) {
			return ImageClass.grassTile;
		} else {
			return ImageClass.sandTile;
		}
	}
}
