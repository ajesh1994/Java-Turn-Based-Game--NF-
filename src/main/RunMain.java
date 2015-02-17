package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;

import panels.ActionMenuPanel;
import panels.ChatPanel;
import panels.GameStatusPanel;

public class RunMain {

	public static void main(String[] args) {
		
		ExecutorService ex = Executors.newFixedThreadPool(4);
		
		JFrame frame = new JFrame("Test");
		frame.setLayout(new BorderLayout());
		
		Game game = new Game();
		
        GameStatusPanel statusPanel = new GameStatusPanel(game);
    
        game.setStatusPanel(statusPanel);
        frame.add(statusPanel, BorderLayout.NORTH);
		
		frame.add(game, BorderLayout.CENTER);

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());
		
		ActionMenuPanel actionPanel = new ActionMenuPanel(game);
		game.setActionPanel(actionPanel);
		secondPanel.add(actionPanel, BorderLayout.NORTH);
		
		ChatPanel chatPanel = new ChatPanel(game);
		game.setChatPanel(chatPanel);
		secondPanel.add(chatPanel, BorderLayout.SOUTH);
		
        frame.add(secondPanel, BorderLayout.SOUTH);
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(game.getGameWidth() + 5, game.getGameHeight() + 75); //TODO: replace magic number

		statusPanel.setVisible(false);
		actionPanel.setVisible(false);
		chatPanel.setVisible(false);
		
		frame.setResizable(false);
		frame.setVisible(true);
		
		game.updateStatusPanel();
		
		javax.swing.SwingUtilities.invokeLater((Runnable) ex.submit(game));

	}

}