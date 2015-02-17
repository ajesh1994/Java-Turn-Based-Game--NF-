package panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import main.Game;

/**
 * Class to display the action panel and commands
 * @author Helen
 *
 */
@SuppressWarnings("serial")
public class ActionMenuPanel extends JPanel {

	/**List of available commands*/
	private ArrayList<String> commands = new ArrayList<String>();

	/**
	 * Constructor to set up the commands
	 * @param game - the main game class
	 */
	public ActionMenuPanel(final Game game) {
		super();
		
		/**Button to add new units*/
		JButton addUnitButton = new JButton("New unit");
		addUnitButton.setName("New unit");
		commands.add(addUnitButton.getName());
		addUnitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = ((JButton) e.getSource()).getActionCommand();
				
				game.setCommand(command); //sets command in game class
			}
		});
		
		/**Button to add new bases*/
		JButton addBaseButton = new JButton("New base");
		addBaseButton.setName("New base");
		commands.add(addBaseButton.getName());
		addBaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = ((JButton) e.getSource()).getActionCommand();
				
				game.setCommand(command); //sets command in game class
			}
		});
		
		/**Button to add new units*/
		JButton addMineButton = new JButton("New gold mine");
		addMineButton.setName("New gold mine");
		commands.add(addMineButton.getName());
		addMineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = ((JButton) e.getSource()).getActionCommand();
				
				game.setCommand(command); //sets command in game class
			}
		});
		
		/**Button to cancel action*/
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setName("Cancel");
		commands.add(cancelButton.getName());
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = ((JButton) e.getSource()).getActionCommand();

				game.setCommand(command); //sets command in game class
			}
		});
		
		/**Button to end player's turn*/
		JButton endTurnButton = new JButton("End turn");
		endTurnButton.setName("End turn");
		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.endTurn(); //calls method in game class
			}
		});

		add(addUnitButton);
		add(addBaseButton);
		add(addMineButton);
		add(cancelButton);
		add(endTurnButton);
		
		setPreferredSize(new Dimension(game.getGameWidth(), 35));
	}
	
	/**
	 * Method to get the array list of available commands
	 * @return the command list
	 */
	public ArrayList<String> getCommands() {
		return commands;
	}
	
}