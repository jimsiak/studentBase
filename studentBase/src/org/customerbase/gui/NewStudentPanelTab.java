package org.customerbase.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.customerbase.Main;
import org.customerbase.database.Student;
import org.customerbase.database.StudentData;
import org.customerbase.database.Course;
import org.customerbase.database.CourseData;
import org.customerbase.database.Payment;

public class NewStudentPanelTab extends JPanel
implements ActionListener
{
	private StudentInputPanel customerInputPanel;
	private PaymentInputPanel machineInputPanel;
	private ServiceInputPanel serviceInputPanel;
	private SpareInputPanel spareInputPanel;

	public NewStudentPanelTab()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints c;
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 3;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		this.customerInputPanel = new StudentInputPanel();
		add(this.customerInputPanel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 1;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.machineInputPanel = new PaymentInputPanel();
		add(this.machineInputPanel, c);

		this.customerInputPanel.comboBox.setMachineListComboBox(this.machineInputPanel.comboBox);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = c.weighty = 1.0;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		this.spareInputPanel = new SpareInputPanel();
		add(this.spareInputPanel, c);

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
		Student cust = this.customerInputPanel.getSpecifiedStudent();
		if (cust == null)
		{
			StudentData data = this.customerInputPanel.getStudentDataFromTextFields();
			cust = new Student(data);
			Main.dbmanager.addCustomerToDatabase(cust);
		}

		Course mach = this.machineInputPanel.getSpecifiedMachine();
		if (mach == null)
		{
			CourseData data = this.machineInputPanel.getMachineDataFromTextFields();
			mach = new Course(data);
			Main.dbmanager.addMachineToDatabase(mach, cust);
		}

		Payment serv = this.serviceInputPanel.getSpecifiedService();
		Main.dbmanager.addServiceToDatabase(serv, mach);

		List spares = this.spareInputPanel.getSpares();
		Main.dbmanager.addSpareListToDatabase(spares, serv);

		this.customerInputPanel.reset();
		this.machineInputPanel.reset();
		this.serviceInputPanel.reset();
	}
}