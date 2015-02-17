package panels;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Game;

/**
 * Class to display the chat text box
 * @author Helen
 *
 */
public class ChatPanel extends JPanel {

	public ChatPanel(Game game) {
		
		this.setLayout(new FlowLayout());
		JLabel textLabel = new JLabel("Chat:");
		add(textLabel);
		
		JTextField textBox = new JTextField();
		textBox.setPreferredSize(new Dimension(game.getGameWidth()-50, 30));
		add(textBox);
	}
}
