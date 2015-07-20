package Game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window{


	public Window(int width, int height, String title, LoreCraft game){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		game.setBackground(Color.black);
		frame.setVisible(true);
	}
}
