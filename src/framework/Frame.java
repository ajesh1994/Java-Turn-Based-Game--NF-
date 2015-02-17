package framework;

//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	private Frame() {

        setTitle("Heroes of Warfare");
        
        String size = "windowed";

        if(size == "fullscreen") {
        	
            setUndecorated(true);
            setExtendedState(this.MAXIMIZED_BOTH);
            
        } else if (size == "windowed") {
            setSize(800, 800);
            
            setLocationRelativeTo(null);

            setResizable(false);
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setContentPane(new GameManager());
        
        this.setVisible(true);
    }
	
    public static void main(String[] args)
    {
    	//ExecutorService ex = Executors.newFixedThreadPool(4);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame();
            }
        });
    }

}
