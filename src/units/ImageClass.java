package units;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Class to store the BufferedImage files
 * @author Helen
 *
 */
public class ImageClass {

	/**Image to represent grass terrain*/
	public static BufferedImage grassTile;
	/**Image to represent sand terrain*/
	public static BufferedImage sandTile;
	/**Image to represent character sprite*/
	public static BufferedImage charImg;
	/**Image to represent base sprite*/
	public static BufferedImage baseImg;
	/**Image to represent command centre sprite*/
	public static BufferedImage centreImg;
	/**Image to represent gold mine sprite*/
	public static BufferedImage mineImg;
	
	/**
	 * Method to load the terrain tiles for the map
	 */
	public static void loadTiles() {
		try {
			URL grassImgUrl = ImageClass.class.getResource("/resources/grass-tile.png"); //http://www.outworldz.com/SeamlessTextures/master/FREE-Patchy%20Grass-by-Linda-Kellie.png
			grassTile = ImageIO.read(grassImgUrl);
			
			URL sandImgUrl = ImageClass.class.getResource("/resources/sand-tile.png"); //http://www.wurmpedia.com/images/thumb/a/a7/Sand.png/180px-Sand.png
			sandTile = ImageIO.read(sandImgUrl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to load the character sprites
	 */
	public static void loadChar() {
		try {
			URL charImgUrl = ImageClass.class.getResource("/resources/pepper-tile.png"); //http://www.webweaver.nu/clipart/img/misc/food/vegetables/yellow-pepper.png
			charImg = ImageIO.read(charImgUrl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to load the command centre sprites
	 */
	public static void loadCentre() {
		try {
			URL centreImgUrl = ImageClass.class.getResource("/resources/salad-tile.png"); //http://www.clipartlord.com/wp-content/uploads/2014/11/salad.png
			centreImg = ImageIO.read(centreImgUrl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to load the base sprites
	 */
	public static void loadBase() {
		try {
			URL baseImgUrl = ImageClass.class.getResource("/resources/tomato-tile.png"); //http://clipart-finder.com/data/preview/33-tomato_ripe.png
			baseImg = ImageIO.read(baseImgUrl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Method to load the gold mine sprites
	 */
	public static void loadMine() {
		try {
			URL mineImgUrl = ImageClass.class.getResource("/resources/fridge-tile.png"); //http://cliparts101.com/files/11/9D7C32FBF0D6CA1828A68A41C3B84447/tn_fridge_01.png
			mineImg = ImageIO.read(mineImgUrl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Method to scale a given image to size
	 * @param bi - the given buffered image to be scaled
	 * @param dimensions - the dimensions in pixels to scale the image to
	 * @return the scaled given image
	 */
	public static Image resize(BufferedImage bi, int dimensions) {
		Image newBI = bi.getScaledInstance(dimensions, dimensions, Image.SCALE_DEFAULT); //SCALE_DEFAULT is Java's default scaling algorithm, may not be so precise
		return newBI;
	}
}