package apfly;

//Panel for each teacher; contains name, email, checkbox to send, and button to change email

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TeacherPanel extends JPanel implements ActionListener{
	
	private JPanel top = new JPanel();
	private JPanel bot = new JPanel();
	
	private Teacher teacher;
	private JCheckBox checkBox = new JCheckBox();
	private JButton changeEmailBtn = new JButton("Change Email");
	private JTextField nameDisplay;
	private JTextField emailDisplay;
	public String name;
	
	TeacherPanel(Teacher t){
		teacher = t;
		name = teacher.name;
		
		setSize(100, 25);
		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(bot, BorderLayout.SOUTH);
		
		nameDisplay = new JTextField(name);
		nameDisplay.setEditable(false);
		top.add(nameDisplay, BorderLayout.EAST);
		top.add(checkBox, BorderLayout.WEST);
		
		emailDisplay = new JTextField(teacher.teacherEmail);
		emailDisplay.setEditable(false);
		bot.add(emailDisplay, BorderLayout.WEST);
		bot.add(changeEmailBtn, BorderLayout.EAST);
		changeEmailBtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == changeEmailBtn) {
			
		}
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new TeacherPanel(DataReader.readData("test_data.mer").get(0)));
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
}
