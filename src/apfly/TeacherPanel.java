package apfly;

//Panel for each teacher; contains name, email, checkbox to send, and button to change email

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.swing.*;

import com.itextpdf.text.DocumentException;

public class TeacherPanel extends JPanel implements ActionListener{
	
	private JPanel top = new JPanel();
	private JPanel bot = new JPanel();
	
	private Teacher teacher;
	private JCheckBox checkBox = new JCheckBox();
	private JButton changeEmailBtn = new JButton("Change Email");
	private JLabel nameDisplay;
	private JLabel emailDisplay;
	private String name;
	
	TeacherPanel(Teacher t){
		teacher = t;
		name = teacher.name;

		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(bot, BorderLayout.SOUTH);
		
		nameDisplay = new JLabel(name);
		nameDisplay.setPreferredSize(new Dimension(100, 25));
		top.add(nameDisplay, BorderLayout.EAST);
		top.add(checkBox, BorderLayout.WEST);
		
		emailDisplay = new JLabel(teacher.teacherEmail);
		emailDisplay.setPreferredSize(new Dimension(200, 25));
		bot.add(emailDisplay, BorderLayout.WEST);
		bot.add(changeEmailBtn, BorderLayout.EAST);
		changeEmailBtn.addActionListener(this);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void check(boolean newVal) {
		checkBox.setSelected(newVal);
	}
	
	public boolean isChecked() {
		return checkBox.isSelected();
	}
	
	public void sendEmail(String user, String pass) throws MessagingException, IOException, DocumentException {
		teacher.sendEmail(user, pass, name + "_AP_Registration.pdf");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == changeEmailBtn) {
			String newMail = JOptionPane.showInputDialog(this,"Change " + teacher + "'s Email");
			if(newMail.trim().length() > 0) {
				teacher.teacherEmail = newMail;
			}
			emailDisplay.setText(teacher.teacherEmail);
		}
		
	}
	
	public String toString() {
		return name;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new TeacherPanel(DataReader.readData("test_data.mer").get(0)));
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
}
