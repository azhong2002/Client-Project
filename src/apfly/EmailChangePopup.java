package apfly;

//Andrew Zhong 5/23/18 Popup window allows user to change a recipient's email in the Teacher object

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class EmailChangePopup extends JFrame implements ActionListener, FocusListener{
	
	private JButton addBtn;
	private JTextField entry;
	private Teacher teach;
	
	public EmailChangePopup(Teacher t) {
		super("Change Email");
		setSize(300,300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		teach = t;
		addBtn = new JButton("Change");
		entry = new JTextField(teach + "'s Email");
		entry.setForeground(Color.gray);
		entry.addFocusListener(this);
		
		add(entry, BorderLayout.WEST);
		add(addBtn, BorderLayout.EAST);
		addBtn.addActionListener(this);
		
		this.pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == addBtn && entry.getForeground() != Color.gray) {	//set teacher's email and close window
			teach.teacherEmail = entry.getText();
			dispose();
		}
	}
	
	public void focusGained(FocusEvent e) {
        if(entry.getForeground() == Color.gray) {
        	entry.setText("");
        }
        entry.setForeground(Color.black);
	}
	
	public void focusLost(FocusEvent e) {
		if(entry.getText().equals("")) {
        	entry.setText(teach + "'s Email");
        	entry.setForeground(Color.gray);
        }
	}

}
