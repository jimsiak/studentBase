package org.studentbase.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.studentbase.Main;
import org.studentbase.database.Course;
import org.studentbase.database.CourseData;
import org.studentbase.database.Payment;
import org.studentbase.database.Student;
import org.studentbase.database.StudentData;

import com.toedter.calendar.JDateChooser;

public class NewStudentPanelTab extends JPanel
implements ActionListener
{
	private StudentInputPanel studentInputPanel;
	private PaymentInputPanel paymentInputPanel;
	private StudentStatisticsPanel studentStatisticsPanel;

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
		c.gridwidth = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		this.studentInputPanel = new StudentInputPanel();
		add(this.studentInputPanel, c);

		/*** Payment Fields ***/
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		//c.weightx = c.weighty = 1.0;
		//c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.paymentInputPanel = new PaymentInputPanel();
		this.paymentInputPanel.setStudentInputPanel(this.studentInputPanel);
		add(this.paymentInputPanel, c);

		/*** User statistics Fields ***/
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = c.weighty = 1.0;
		//c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.insets = new Insets(200, 0, 0, 0);
		this.studentStatisticsPanel = new StudentStatisticsPanel();
		this.studentStatisticsPanel.setStudentInputPanel(this.studentInputPanel);
		add(this.studentStatisticsPanel, c);

		this.studentInputPanel.setStudentStatisticsPanel(this.studentStatisticsPanel);
		this.studentInputPanel.getStudentListComboBoxRefreshButton().setStudentInputPanel(this.studentInputPanel);
		this.studentInputPanel.getStudentListComboBoxRefreshButton().setStudentStatisticsPanel(this.studentStatisticsPanel);
		
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
		/*** Get selected payment data and add it to database. ***/
		Payment payment = this.paymentInputPanel.getSpecifiedPayment();
		if (payment == null) {
			/* FIXME: Error here.... We cannot continue... */
			JOptionPane.showMessageDialog(Main.mainFrame, "Πρέπει να επιλέξετε ένα μάθημα...", 
					"Σφάλμα!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		/*** Add new student to database if needed. ***/
		Student stud = this.studentInputPanel.getSpecifiedStudent();
		if (stud == null)
		{
			/*** Get studentData from textfields. ***/
			StudentData data = this.studentInputPanel.getStudentDataFromTextFields();
			
			/*** Lastname and Firstname cannot be null. ***/
			if (data.getInfoByFieldName("lastname").equals("")) {
				JOptionPane.showMessageDialog(Main.mainFrame, "Το πεδίο 'Επώνυμο' δεν μπορεί να είναι κενό.", 
						"Σφάλμα!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (data.getInfoByFieldName("firstname").equals("")) {
				JOptionPane.showMessageDialog(Main.mainFrame, "Το πεδίο 'Όνομα' δεν μπορεί να είναι κενό.", 
						"Σφάλμα!", JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			
			/*** If this is a payment for registration student becomes a member. ***/
			if (payment != null &&
					payment.getCid() == Main.dbmanager.registrationCourse.getId()) {
				data.updateByFieldName("ismember", "1");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String dateString = dateFormat.format(date);
				data.updateByFieldName("registerdate", dateString);
			}
			
			stud = new Student(data);
			Main.dbmanager.addStudentToDatabase(stud);
		}
		
		Main.dbmanager.addPaymentToDatabase(payment, stud);
		
		this.studentStatisticsPanel.refresh();
		this.paymentInputPanel.reset();
	}
}