package framework;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import main.Game;

public class GameManager extends JPanel implements MouseListener, KeyListener {

	public int gameWidth;

	public int gameHeight;

	public static enum GameState {
		MAINMENU, SETTINGS, FINDLOBBY, HOSTLOBBY, INLOBBY, LOADING, INGAME, GAMEOVER
	}

	public static GameState gameState;

	private int timeElapsed;

	private Game game;

	public GameManager() {
		super();

		gameState = GameState.MAINMENU;

		Thread gameThread = new Thread() {
			@Override
			public void run(){
				gameLoop();
			}
		};
		gameThread.start();
	}

	private void gameLoop() {

		while(true) {

			switch (gameState) {
				case MAINMENU:
					break;
				case SETTINGS:
					break;
				case FINDLOBBY:
					break;
				case HOSTLOBBY:
					break;
				case INLOBBY:
					break;
				case LOADING:
					break;
				case INGAME:
					break;
				case GAMEOVER:
					break;
			}

			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		switch (gameState) {
			case MAINMENU:
				break;
			case SETTINGS:
				break;
			case FINDLOBBY:
				break;
			case HOSTLOBBY:
				break;
			case INLOBBY:
				break;
			case LOADING:
				break;
			case INGAME:
				break;
			case GAMEOVER:
				break;
		}
	}
	
	private void newGame() {
		game = new Game();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
