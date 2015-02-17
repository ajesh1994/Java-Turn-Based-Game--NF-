package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Created by Thomas on 16/02/2015.
 */

public class Options extends JPanel implements MouseListener {

    private Graphics2D g2;
    private BufferedImage background1;
    private BufferedImage background2;
    private BufferedImage background3;

    public void render(Graphics g) {

        getImages();

        g2 = (Graphics2D) g;

        g2.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g2.drawImage(background1, 15, 100, this);

    }

    private void render(int i) {

        g2.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        switch (i) {
            case 0:
                g2.drawImage(background1, 15, 100, this);
                break;
            case 1:
                g2.drawImage(background2, 15, 100, this);
                break;
            case 2:
                g2.drawImage(background3, 15, 100, this);
                break;
            default:
                g2.drawImage(background1, 15, 100, this);
                break;
        }

    }

    private void getImages() {

        try {
            background1 = ImageIO.read(URL.class.getResource("/resources/options-player.png"));
            background2 = ImageIO.read(URL.class.getResource("/resources/options-audio.png"));
            background3 = ImageIO.read(URL.class.getResource("/resources/options-video.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        System.out.println(x + ", " + y);

        if (x > 165 && x < 265 && y > 100 && y < 140) {

            render(1);

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
