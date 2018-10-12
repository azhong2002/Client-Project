package apfly;

//Andrew Zhong
//Panel Object as a container for Teacher object; contains name, email, checkbox to send, and button to change email

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
	
	private JPanel top = new JPanel();		//organizational panels
	private JPanel bot = new JPanel();
	
	private Teacher teacher;												//Teacher object
	private JCheckBox checkBox = new JCheckBox();							//Checkbox for selection
	private JButton changeEmailBtn = new JButton("Change Email");			//popup button to change email
	private JLabel nameDisplay;												//Shows teacher's name
	private JLabel emailDisplay;											//shows current email		
	public JTextArea customText = new JTextArea("Custom Add-on Message");	//custom message field
	public JScrollPane customTextScroll = new JScrollPane(customText);		//Scrollpane for custom messages
	private String name;													//teacher name
	
	TeacherPanel(Teacher t){			//set values for all components
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
		customTextScroll.setPreferredSize(new Dimension(400, 80));
		customTextScroll.getVerticalScrollBar().setUnitIncrement(16);
    	customText.setForeground(Color.gray);
    	customText.addFocusListener(this);
    	customText.setLineWrap(true);
    	customText.setWrapStyleWord(true);
		
		emailDisplay = new JLabel(teacher.teacherEmail);
		emailDisplay.setPreferredSize(new Dimension(200, 25));
		if(!teacher.uniqueEmail) {
			emailDisplay.setForeground(Color.red);
		}
		bot.add(emailDisplay, BorderLayout.WEST);
		bot.add(changeEmailBtn, BorderLayout.EAST);
		changeEmailBtn.addActionListener(this);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void check(boolean newVal) {				//change whether or not this teacher is selected
		checkBox.setSelected(newVal);
	}
	
	public boolean isChecked() {					//returns if the teacher is selected
		return checkBox.isSelected();
	}
	
	public void sendEmail(String user, String pass, String messageToAll) throws MessagingException, IOException, DocumentException {	//tells Teacher object to send email
		String customMsg = "";
		if(customText.getForeground() == Color.black) {
			customMsg = customText.getText();
		}
		teacher.sendEmail(user, pass, messageToAll + "<br />" + customMsg);	//use br; email text is formatted as html
	}
	
	public void actionPerformed(ActionEvent e) {		//action when the email change button is pressed
		if (e.getSource() == changeEmailBtn) {
			String newMail = JOptionPane.showInputDialog(this,"Change " + teacher + "'s Email. Teachers with changed emails will have their emails colored blue.");	//prompts user
			if(newMail.trim().length() > 0) {		//If something is provided, set the new email
				teacher.teacherEmail = newMail;
				emailDisplay.setForeground(Color.blue);
			}
			emailDisplay.setText(teacher.teacherEmail);
		}
		
	}
	
	public String toString() {
		return name;
	}
	
	public static void main(String[] args) {	//testing
		JFrame frame = new JFrame();
		frame.add(new TeacherPanel(DataReader.readData("test_data.mer").get(0)));
		frame.pack();
		frame.setVisible(true);
	}

	//Custom Message field hints in gray text
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
