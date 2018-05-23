package apfly;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.*;
import java.applet.Applet;

public class GUI extends JFrame implements ActionListener, FocusListener{
	
	private Panel north = new Panel();	//organizational panels
	private Panel south = new Panel();
	private Panel SE = new Panel();

	//LOGIN
	private Panel login = new Panel();
	private Panel loginEntries = new Panel();
	private JTextField userIn = new JTextField("Username");
	private JTextField passIn = new JTextField("Password");
	private JButton loginBtn = new JButton("Set Credentials");
	String user = "";
	String pass = "";
	
	//FILE UPLOAD
	private Panel fileUpload = new Panel();
	
	//TEACHER LIST
	private Panel TeacherList = new Panel();
	
	//MESSAGES
	private JTextArea messages = new JTextArea("Messages: \n");
	private JScrollPane msgPanel = new JScrollPane(messages);
	
	public GUI(String title) {
		super(title);
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());	//adding all sections
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
		south.add(SE, BorderLayout.EAST);
		//north.setBackground(Color.gray);
		
		//LOGIN SECTION
		north.add(login, BorderLayout.WEST);	
		login.add(loginEntries, BorderLayout.WEST);
		
		loginEntries.add(userIn, BorderLayout.NORTH);	//user and pass entries, with gray hint text when nothing entered
		userIn.setForeground(Color.gray);
		userIn.addFocusListener(this);
		loginEntries.add(passIn, BorderLayout.SOUTH);
		passIn.setForeground(Color.gray);
		passIn.addFocusListener(this);
		
		login.add(loginBtn, BorderLayout.EAST);	//button to log in
		loginBtn.addActionListener(this);
		
		//FILEUPLOAD
		north.add(fileUpload, BorderLayout.EAST);
		
		//TEACHERLILST
		SE.add(TeacherList, BorderLayout.NORTH);
		
		//MESSAGES
		south.add(msgPanel, BorderLayout.WEST);	//Message panel
		msgPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		messages.setEditable(false);
		messages.setPreferredSize(new Dimension(300,300));
		
	}
	
	public void print(String msg) {
		messages.setText(messages.getText() + "\n" + msg);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == loginBtn && userIn.getForeground() != Color.gray && passIn.getForeground() != Color.gray) {//sets login info if provided
			user = userIn.getText();	
			pass = passIn.getText();
			passIn.setText("Password");			//resets entry hints
        	passIn.setForeground(Color.gray);
        	userIn.setText("Username");
        	userIn.setForeground(Color.gray);
		}	
	}
	
	public void focusGained(FocusEvent e) {
		Object src = e.getSource();
		if(src == userIn && userIn.getForeground() == Color.gray) {	//removes username entry hint
			userIn.setText("");
			userIn.setForeground(Color.black);
		}
		if(src == passIn && passIn.getForeground() == Color.gray) {	//removes pass entry hint
			passIn.setText("");
			passIn.setForeground(Color.black);
		}
        
	}

	public void focusLost(FocusEvent e) {
		Object src = e.getSource();
		if(src == userIn && userIn.getText().equals("")) {	//if user is empty, resets entry hint
			userIn.setText("Username");
        	userIn.setForeground(Color.gray);
		}
		if(src == passIn && passIn.getText().equals("")) {	//resets pass entry hint
			passIn.setText("Password");
        	passIn.setForeground(Color.gray);
		}
		
	}
	
	public static void main(String[] args) {
		JFrame window = new GUI("APFly");
		window.setVisible(true);

	}

}
