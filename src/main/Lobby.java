package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Thomas on 15/02/2015.
 */

public class Lobby extends JPanel implements MouseListener {

    private BufferedImage background;
    private ArrayList<BufferedImage> labels;
    private ArrayList<BufferedImage> controls;

    public void render(Graphics g) {

        getImages();

        Graphics2D g2 = (Graphics2D) g;

        g2.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g2.drawImage(background, 15, 100, this);

    }

    private void getImages() {

        try {
            background = ImageIO.read(URL.class.getResource("/resources/lobby.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        labels = new ArrayList<BufferedImage>();
        controls = new ArrayList<BufferedImage>();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        System.out.println(x + ", " + y);

        if (x > 511 && x < 546 && y > 399 && y < 413) {

            System.out.println("It works!");
            Game.state = Game.gameState.Game;

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}