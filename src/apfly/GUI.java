package apfly;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.*;
import java.applet.Applet;

public class GUI extends JPanel implements ActionListener{
	
	private Panel north = new Panel();
	private Panel south = new Panel();
	
	public GUI() {
		
		setLayout(new BorderLayout());
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
		north.setBackground();
		
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	public static void main(String[] args) {
		JFrame fram = new JFrame();
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI gui = new GUI();

		fram.add(gui);
		fram.setVisible(true);

	}

}
