package main;

import main.Game.gameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends JPanel implements MouseListener {

    private BufferedImage gameTitle;
    private BufferedImage newServer;
    private BufferedImage findServer;
    private BufferedImage options;
    private BufferedImage exit;
    private int[] gameTitleD;
    private int[] newServerD;
    private int[] findServerD;
    private int[] optionsD;
    private int[] exitD;

	public void render(Graphics g){

//        try {
//            gameTitle = ImageIO.read(URL.class.getResource("/resources/logo.png"));
//            newServer = ImageIO.read(URL.class.getResource("/resources/new_server.png"));
//            findServer = ImageIO.read(URL.class.getResource("/resources/find_server.png"));
//            options = ImageIO.read(URL.class.getResource("/resources/options.png"));
//            exit = ImageIO.read(URL.class.getResource("/resources/exit.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        getImages();

//        gameTitleD = new int[]{gameTitle.getWidth(), gameTitle.getHeight()};
//        newServerD = new int[]{newServer.getWidth(), newServer.getHeight()};
//        findServerD = new int[]{findServer.getWidth(), findServer.getHeight()};
//        optionsD = new int[]{options.getWidth(), options.getHeight()};
//        exitD = new int[]{exit.getWidth(), exit.getHeight()};

//        Rectangle playGame = new Rectangle(Game.WIDTH/2+200, 170, 170, 50);
        Graphics2D g2 = (Graphics2D) g;

        g2.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g2.drawImage(gameTitle, 130, 200, this);
        g2.drawImage(findServer, 240, 300, this);
        g2.drawImage(newServer, 233, 335, this);
        g2.drawImage(options, 268, 370, this);
        g2.drawImage(exit, 290, 410, this);

//        Font fnt0 = new Font("arial", Font.BOLD, 50);
//		g.setFont(fnt0);
//		g.setColor(Color.BLACK);
//		g.drawString("Heroes of Warfare", Game.WIDTH / 2+120, 60);

	    /*JButton button = new JButton("test");
	      button.setSize(new Dimension(50, 50));
	      button.setLocation(Game.WIDTH/2, 90);
	      frame.add(button); */

//		Font fnt1 = new Font("arial", Font.BOLD, 30);
//		g.setFont(fnt1);
//		g.drawString("Play Game", playGame.x+15, playGame.y+35);
//		g2.draw(playGame);
	      
	}

    private void getImages() {

        try {
            gameTitle = ImageIO.read(URL.class.getResource("/resources/logo.png"));
            newServer = ImageIO.read(URL.class.getResource("/resources/new_server.png"));
            findServer = ImageIO.read(URL.class.getResource("/resources/find_server.png"));
            options = ImageIO.read(URL.class.getResource("/resources/options.png"));
            exit = ImageIO.read(URL.class.getResource("/resources/exit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameTitleD = new int[]{gameTitle.getWidth(), gameTitle.getHeight()};
        newServerD = new int[]{newServer.getWidth(), newServer.getHeight()};
        findServerD = new int[]{findServer.getWidth(), findServer.getHeight()};
        optionsD = new int[]{options.getWidth(), options.getHeight()};
        exitD = new int[]{exit.getWidth(), exit.getHeight()};

    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();

//		if(x >= Game.WIDTH/2+200 && x <= Game.WIDTH/2 + 370){
//			if(y >= 170 && y <= 220){
//
//
//			/*	GameStatusPanel statusPanel = new GameStatusPanel((Game) this.getParent());
//				System.out.println((Game) this.getParent());
//				((Game) this.getParent()).setStatusPanel(statusPanel);
//				this.getParent().add(statusPanel, BorderLayout.NORTH);
//				ActionMenuPanel actionPanel = new ActionMenuPanel((Game) this.getParent());
//				((Game) this.getParent()).setActionPanel(actionPanel);
//				this.getParent().add(actionPanel, BorderLayout.SOUTH);*/
//				Game.state = gameState.Game;
//			}
//		}

        getImages();

        System.out.println(x + ", " + y);

        if (x > 233 && x < newServerD[0] + 233 && y > 335 && y < newServerD[1] + 335) {
            Game.state = gameState.Game;
        } else if (x > 268 && x < optionsD[0] + 268 && y > 370 && y < optionsD[1] + 370) {
            Game.state = gameState.OPTIONS_PLAYER;
        } else if (x > 290 && x < exitD[0] + 290 && y > 410 && y < exitD[1] + 410) {
            System.exit(0);
        }

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}