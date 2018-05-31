package apfly;

//Andrew Zhong 5/23/18 APFLy GUI
//TODO : implement noTeacher email customization, FileUpload, send method

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener, FocusListener{
	
	private Panel north = new Panel();	//organizational panels
	private Panel south = new Panel();
	private Panel SE = new Panel();

	//LOGIN
	private Panel login = new Panel();
	private Panel loginEntries = new Panel();
	private JTextField userIn = new JTextField("Username     ");
	private JPasswordField passIn = new JPasswordField("Password     ");
	private JButton loginBtn = new JButton("Set Credentials");
	String user = "";
	String pass = "";
	
	//FILE UPLOAD
	private Panel fileUpload = new Panel();
	private JButton chooseFileBtn = new JButton("Upload MER file");
	private JFileChooser fc = new JFileChooser();
	
	//TEACHER LIST
	private ArrayList<Teacher> teachList = new ArrayList<Teacher>();
	ArrayList<TeacherPanel> teacherPanelList = new ArrayList<TeacherPanel>();
	private JPanel teacherPanelHolder = new JPanel();
	private JScrollPane teacherViewPane = new JScrollPane(teacherPanelHolder);
	
	//SEND OPTIONS
	private JButton sendBtn = new JButton("Send");
	private JButton selectAllBtn = new JButton("Select All");
	private JButton deselectAllBtn = new JButton("Deselect All");
	
	//MESSAGES
	private JTextArea messages = new JTextArea("Messages: \n", 15, 30);
	private JScrollPane msgPanel = new JScrollPane(messages);
	
	public GUI(String title) {
		super(title);
		setSize(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		userIn.setPreferredSize(new Dimension(150,20));
		loginEntries.add(passIn, BorderLayout.SOUTH);
		passIn.setForeground(Color.gray);
		passIn.addFocusListener(this);
		passIn.setPreferredSize(new Dimension(150,20));
		passIn.setEchoChar((char) 0);	//allows password hint text to be seen
		
		login.add(loginBtn, BorderLayout.EAST);	//button to log in
		loginBtn.addActionListener(this);
		
		//FILEUPLOAD
		north.add(fileUpload, BorderLayout.EAST);
		fileUpload.add(chooseFileBtn, BorderLayout.NORTH);
		chooseFileBtn.addActionListener(this);
		
		//TEACHERLILST
		SE.add(teacherViewPane, BorderLayout.NORTH);
		teacherViewPane.setPreferredSize(new Dimension(400,500));
		teacherPanelHolder.setLayout(new GridLayout(0,1));
		
		//SEND OPTIONS
		SE.add(selectAllBtn, BorderLayout.CENTER);
		selectAllBtn.addActionListener(this);
		SE.add(deselectAllBtn);
		deselectAllBtn.addActionListener(this);
		SE.add(sendBtn, BorderLayout.SOUTH);
		sendBtn.addActionListener(this);
		
		//MESSAGES
		south.add(msgPanel, BorderLayout.WEST);	//Message panel
		msgPanel.setPreferredSize(new Dimension(420,500));
		messages.setEditable(false);
		msgPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("Logo.png"));
		
		this.pack();
	}
	
	public void setTeachers(ArrayList<Teacher> teachList) {	//sets teacherpanels when new file is added
		for(TeacherPanel tP : teacherPanelList) {	//remove previous panels
			teacherPanelHolder.remove(tP);
		}
		teacherPanelList.clear();
		for (Teacher t: teachList) {
			TeacherPanel tPanel = new TeacherPanel(t);
			teacherPanelList.add(tPanel);
			teacherPanelHolder.add(tPanel);
		}
		teacherViewPane.setSize(300,300);
		this.pack();
	}
	
	public void display(String msg) {	//displays to messageboard
		messages.setText(messages.getText() + "\n" + msg);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == loginBtn) {//sets login info if provided
			if(userIn.getForeground() != Color.gray && passIn.getForeground() != Color.gray){
				user = userIn.getText();
				pass = new String(passIn.getPassword());
				passIn.setText("Password");			//resets entry hints
	        	passIn.setForeground(Color.gray);
	        	passIn.setEchoChar((char) 0);
	        	userIn.setText("Username");
	        	userIn.setForeground(Color.gray);
	        	display("Login credentials for " + user + " set.");
			}
			else{
				display("Please provide a username and password.");
			}
		}
		else if(src == chooseFileBtn) {	//choosing file
			String file;
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				teachList.clear();
                file = fc.getSelectedFile().getPath();
                for(Teacher inp : DataReader.readData(file)) {	//add all new entries for each teacher into list
                	for(Teacher t: teachList) {
                		if (t.name.equals(inp.name)) {
                			t.addAll(inp.examList);
                		}
                	}
                	teachList.add(inp);
                }
                setTeachers(teachList);
                display("Retrieved data from " + file + "\n");
			}
			
		}
		else if(src == selectAllBtn) {		//select all teachers
			if(teacherPanelList.size() == 0) {
				display("Please select a MER file");
			}
			else {
				for(TeacherPanel tPanel: teacherPanelList) {
					tPanel.check(true);
				}
			}
		}
		else if(src == deselectAllBtn) {		//select all teachers
			if(teacherPanelList.size() == 0) {
				display("Please select a MER file");
			}
			else {
				for(TeacherPanel tPanel: teacherPanelList) {
					tPanel.check(false);
				}
			}
		}
		else if(src == sendBtn) {
			if(teacherPanelList.size() == 0) {
				display("Please select a MER file");
			}
			else {
				for(TeacherPanel tPanel: teacherPanelList) {
					if(tPanel.isChecked()) {	//send emails to all selected teachers
						try {
							double start = System.nanoTime();
							tPanel.sendEmail(user, pass);
							display("Emailed " + tPanel + " in " + (System.nanoTime() - start)/1000000000.0 + "seconds.\n");
						}
						catch(Exception ex) {
							display("Error sending " + tPanel + "'s message.\n");
							display(ex.getMessage());
						}
					}
				}
				display("\n");
			}
			
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
			passIn.setEchoChar('\u2022');	//unicode for • character, hides password
		}
        
	}

	public void focusLost(FocusEvent e) {
		Object src = e.getSource();
		if(src == userIn && userIn.getText().equals("")) {	//if user is empty, resets entry hint
			userIn.setText("Username");
        	userIn.setForeground(Color.gray);
		}
		if(src == passIn && (new String(passIn.getPassword())).equals("")) {	//resets pass entry hint
			passIn.setText("Password");
        	passIn.setForeground(Color.gray);
        	passIn.setEchoChar((char) 0);	//makes hint text appear as text not dots
		}
		
	}
	
	public static void main(String[] args) {
		JFrame window = new GUI("APFly");
		window.setVisible(true);

	}

}
