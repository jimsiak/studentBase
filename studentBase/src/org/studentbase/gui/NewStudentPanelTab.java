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
		//c.fill = GridBagConstraints.HORIZONTAL;
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
		add(this.paymentInputPanel, c);

		//this.studentInputPanel.comboBox.setMachineListComboBox(this.paymentInputPanel.comboBox);

//		c = new GridBagConstraints();
//		c.gridx = 0;
//		c.gridy = 3;
//		c.weightx = c.weighty = 1.0;
//		c.gridwidth = 2;
//		c.gridheight = 2;
//		c.fill = GridBagConstraints.BOTH;
//		this.spareInputPanel = new SpareInputPanel();
//		add(this.spareInputPanel, c);

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

		Course mach = this.paymentInputPanel.getSpecifiedCourse();
		if (mach == null)
		{
			CourseData data = this.paymentInputPanel.getMachineDataFromTextFields();
			mach = new Course(data);
			Main.dbmanager.addMachineToDatabase(mach, stud);
		}

		Payment serv = this.serviceInputPanel.getSpecifiedService();
		Main.dbmanager.addServiceToDatabase(serv, mach);

		List spares = this.spareInputPanel.getSpares();
		Main.dbmanager.addSpareListToDatabase(spares, serv);

		this.studentInputPanel.reset();
		this.paymentInputPanel.reset();
		this.serviceInputPanel.reset();
	}
}