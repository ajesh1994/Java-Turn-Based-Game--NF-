package panels;


import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;

/**
 * GameStatusPanel is a JPanel that displays various game-related stats like player resources etc.
 */
public class GameStatusPanel extends JPanel {

    private Game game;
    private JLabel turnNumLabel;
    private JLabel unitHealth;
    private JLabel goldLabel;
    private JLabel unitsLabel;
    private JLabel basesLabel;
    private JLabel pointsLabel;

    /**
     * Constructor method for GameStatusPanel class.
     * @param g An instance of the Game class.
     */
    public GameStatusPanel(final Game g) {

        super();
        game = g;

        goldLabel = new JLabel("Gold: ");
        basesLabel = new JLabel("Bases: ");
        unitsLabel = new JLabel("Units: ");
        unitHealth = new JLabel("HP: ");
        turnNumLabel = new JLabel("Turn No.");
        pointsLabel = new JLabel("Action points: ");


        add(goldLabel);
        add(basesLabel);
        add(unitsLabel);
        add(unitHealth);
        add(turnNumLabel);
        add(pointsLabel);

    }

    /**
     * Updates the turn number.
     * @param i Turn number.
     */
    public void setTurnNumber(int i) {

        turnNumLabel.setText("Turn No.: " + i);

    }

    /**
     * Updates the amount of gold.
     * @param i Amount of gold.
     */
    public void setGoldAmount(int i) {

        goldLabel.setText("Gold: " + i);

    }

    /**
     * Updates the number of units.
     * @param i Number of units.
     */
    public void setUnitNumber(int i) {

        unitsLabel.setText("Units:" + i);

    }

    /**
     * Updates the number of bases.
     * @param i Number of bases.
     */
    public void setBaseNumber(int i) {

        basesLabel.setText("Bases: " + i);

    }

    /**
     * Updates the health of selected unit/building.
     * @param hp Health of selected unit/building.
     */
    public void setHP(int hp) {

        unitHealth.setText("HP: " + hp);

    }
    
    /**
     * Updates the health of selected unit/building.
     * @param hp Health of selected unit/building.
     */
    public void setPoints(int points, int points2) {

        pointsLabel.setText("Action points: " + points + "/" + points2);

    }

}
