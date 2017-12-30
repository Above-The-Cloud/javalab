
package window;

import java.awt.*;

import java.util.*;
import java.util.Timer;

import javax.swing.*;  
import javax.swing.JFrame;


public class LoadingFrame extends JFrame{

	public LoadingFrame()
	{
		setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));
		setTitle("loading");
		setSize(891 ,535);
		setLocationRelativeTo(null); 
    }
	 public void paint(Graphics g) {
	        super.paint(g);
	        Graphics2D g2 = (Graphics2D)g; //ǿתԉ2D
	        g2.drawImage(new ImageIcon("image\\loadingPic.png").getImage(), 0, 0, 891 ,535,null);

	    }
	 
    public static void main(String[] args)
    {

    	LoadingFrame frame = new LoadingFrame();
        frame.setVisible(true);
    }
    
	    
}

