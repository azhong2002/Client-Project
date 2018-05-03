package apFly;

import java.awt.BorderLayout;

import javax.swing.*;

public class APFly extends JPanel{
	
	JTextField user;
	JTextField pass;
	
	public APFly(){
		user = new JTextField();
		pass = new JTextField();
		JPanel login = new JPanel();
		login.add(user);
		login.add(pass);
		add(login);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("APFly");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new APFly());

        //Display the window.
        frame.pack();
        frame.setVisible(true);

	}

}
