package framework;

import java.awt.Graphics;

public class Game {

	public Game() {
    GameManager.gameState = GameManager.GameState.LOADING;
    
    Thread startGame = new Thread() {
        @Override
        public void run(){
            initialise();
            
            GameManager.gameState = GameManager.GameState.INGAME;
        }
    };
    startGame.start();
	}
	
	private void initialise() {
		
	}
	
	public void drawGame(Graphics g) {
		
	}
	
	public void drawGameOver(Graphics g) {
		
	}
}
