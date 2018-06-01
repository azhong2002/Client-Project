package apfly;

//Panel for each teacher; contains name, email, checkbox to send, and button to change email

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.swing.*;

import com.itextpdf.text.DocumentException;

public class TeacherPanel extends JPanel implements ActionListener, FocusListener{
	
	private JPanel top = new JPanel();
	private JPanel bot = new JPanel();
	
	private Teacher teacher;
	private JCheckBox checkBox = new JCheckBox();
	private JButton changeEmailBtn = new JButton("Change Email");
	private JLabel nameDisplay;
	private JLabel emailDisplay;
	public JTextArea customText = new JTextArea("Custom Add-on Message");
	public JScrollPane customTextScroll = new JScrollPane(customText);
	private String name;
	
	TeacherPanel(Teacher t){
		teacher = t;
		name = teacher.name;

		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(bot, BorderLayout.SOUTH);
		
		nameDisplay = new JLabel(name);
		nameDisplay.setPreferredSize(new Dimension(100, 25));
		top.add(nameDisplay, BorderLayout.WEST);
		top.add(checkBox, BorderLayout.CENTER);
		top.add(customTextScroll, BorderLayout.EAST);
		customTextScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		customTextScroll.setPreferredSize(new Dimension(200, 35));
    	customText.setForeground(Color.gray);
    	customText.addFocusListener(this);
		
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
		String customMsg = "'";
		if(customText.getForeground() == Color.black) {
			customMsg = customText.getText();
		}
		teacher.sendEmail(user, pass, customMsg, name + "_AP_Registration.pdf");
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

	@Override
	public void focusGained(FocusEvent fe) {
		Object src = fe.getSource();
		if(src == customText && customText.getForeground() == Color.gray) {	//if user is empty, resets entry hint
			customText.setText("");
			customText.setForeground(Color.black);
		}
		
	}

	@Override
	public void focusLost(FocusEvent fe) {
		Object src = fe.getSource();
		if(src == customText && customText.getText().equals("")) {	//if user is empty, resets entry hint
			customText.setText("Custom Add-on Message");
			customText.setForeground(Color.gray);
		}
		
	}
	
	
	
}
