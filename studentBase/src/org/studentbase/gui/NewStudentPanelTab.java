package org.studentbase.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.studentbase.Main;
import org.studentbase.database.Course;
import org.studentbase.database.CourseData;
import org.studentbase.database.Payment;
import org.studentbase.database.Student;
import org.studentbase.database.StudentData;

public class NewStudentPanelTab extends JPanel
implements ActionListener
{
	private StudentInputPanel studentInputPanel;
	private PaymentInputPanel paymentInputPanel;
	private ServiceInputPanel serviceInputPanel;
	private SpareInputPanel spareInputPanel;

	public NewStudentPanelTab()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints c;

		/*** Student Fields ***/
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		this.studentInputPanel = new StudentInputPanel();
		add(this.studentInputPanel, c);

		/*** Payment Fields ***/
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 1;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.paymentInputPanel = new PaymentInputPanel();
		this.paymentInputPanel.setStudentInputPanel(this.studentInputPanel);
		add(this.paymentInputPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		JButton addService = new JButton("ΈΤΟΙΜΟ!");
		addService.addActionListener(this);
		add(addService, c);
	}

	public void actionPerformed(ActionEvent e)
	{
		/*** Add new student to database if needed. ***/
		Student stud = this.studentInputPanel.getSpecifiedStudent();
		if (stud == null)
		{
			StudentData data = this.studentInputPanel.getStudentDataFromTextFields();
			stud = new Student(data);
			Main.dbmanager.addStudentToDatabase(stud);
		}
		
		/*** Get selected payment data, add SID and add it to database. ***/
		Payment payment = this.paymentInputPanel.getSpecifiedPayment();
		if (payment == null)
			/* FIXME: Error here.... We cannot continue... */
			System.out.println("getSpecifiedPayment() returned NULL...");
		else {
			Main.dbmanager.addPaymentToDatabase(payment, stud);
		}
	}
}