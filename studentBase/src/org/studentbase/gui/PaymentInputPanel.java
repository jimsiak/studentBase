package org.studentbase.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.studentbase.database.Course;
import org.studentbase.database.CourseData;
import org.studentbase.database.Payment;
import org.studentbase.database.PaymentData;
import org.studentbase.database.Student;

import com.toedter.calendar.JDateChooser;

public class PaymentInputPanel extends JPanel
{
	private StudentInputPanel studentInputPanel = null;
	
	public void setStudentInputPanel(StudentInputPanel studentInputPanel) {
		this.studentInputPanel = studentInputPanel;
	}

	public CourseListComboBox comboBox;

	public PaymentInputPanel()
	{
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new TitledBorder(null, "Πληρωμή", 4, 2, null, null));

		this.setLayout(new GridBagLayout());
		GridBagConstraints c;

		String[] labels = PaymentData.fieldsLabels;
		int numPairs = labels.length;

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 5, 15, 0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.comboBox = new CourseListComboBox();
		this.comboBox.setParentPanel(this);
		add(this.comboBox, c);
		
		
		/* For future use. Show description on mouse focus. */
		//((JTextField)PaymentData.fieldsInputType[0]).setToolTipText("This is description");
		
		int gridy = 2;
		for (int i = 0; i < numPairs; i++) {
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = gridy;
			c.weightx = 0.1; 
			c.weighty = 1.0;
			c.insets = new Insets(0, 5, 0, 0);
			c.anchor = GridBagConstraints.WEST;
			JLabel l = new JLabel(labels[i], SwingConstants.LEFT);
			add(l, c);

			JComponent comp_to_add = null;
			int columns = 20;
			c = new GridBagConstraints();
			c.insets = new Insets(3, 3, 3, 3);
			c.gridx = 1;
			c.ipady = 10;
			if (PaymentData.fieldsInputType[i] instanceof JTextField) {
				JTextField textField = (JTextField) PaymentData.fieldsInputType[i];
				if (textField.getColumns() <= 0)
					textField.setColumns(columns);
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.anchor = GridBagConstraints.FIRST_LINE_START;

				comp_to_add = textField;
			}
			else if (PaymentData.fieldsInputType[i] instanceof JDateChooser) {
				JDateChooser dateChooser = (JDateChooser) PaymentData.fieldsInputType[i];
				dateChooser.setDate(new Date());
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.gridwidth = 1;
				c.ipadx = 50;
				c.anchor = GridBagConstraints.FIRST_LINE_START;

				comp_to_add = dateChooser;
			}
			else if (PaymentData.fieldsInputType[i] instanceof JCheckBox) {
				JCheckBox checkBox = (JCheckBox) PaymentData.fieldsInputType[i];
				checkBox.setSelected(false);
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.gridwidth = 1;
				//c.ipadx = 50;
				c.anchor = GridBagConstraints.FIRST_LINE_START;

				comp_to_add = checkBox;
			}
			else if (PaymentData.fieldsInputType[i] instanceof JSpinner) {
				SpinnerModel model = new SpinnerDateModel();
				JSpinner timeSpinner = (JSpinner) PaymentData.fieldsInputType[i];
				timeSpinner.setModel(model);
				JComponent editor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
				timeSpinner.setEditor(editor);
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.gridwidth = 1;
				c.ipadx = 15;
				c.anchor = GridBagConstraints.FIRST_LINE_START;

				comp_to_add = timeSpinner;
			}
			
			this.setEditable(false);
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			comp_to_add.setBorder(border);
			add(comp_to_add, c);
		}
	}

	public Payment getSpecifiedPayment()
	{
		Course course = this.comboBox.getSelectedCourse();
		if (course == null)
			return null;
		
		PaymentData data = getPaymentDataFromTextFields();
		Payment payment = new Payment(data);
		payment.setCid(course.getId());
		
		return payment;
	}

	public void writePaymentCost(Course course)
	{
		JTextField costField = null;
		for (int i = 0; i < PaymentData.fieldsName.length; i++) {
			if (PaymentData.fieldsName[i].equals("cost"))
				costField = (JTextField)PaymentData.fieldsInputType[i];
		}
		if (course == null) 
			costField.setText("");
		else {
			Student stud = this.studentInputPanel.getSpecifiedStudent();
			if (stud == null)
				costField.setText(course.getInfoByFieldName("cost_nomembers") + "€");
			else if (stud.isMember())
				costField.setText(course.getInfoByFieldName("cost_members") + "€");
			else
				costField.setText(course.getInfoByFieldName("cost_nomembers") + "€");
		}
	}

	public void writePaymentToTextFields(Course mach)
	{
		if (mach == null) {
			for (int i = 0; i < CourseData.fieldsName.length; i++) {
				Component inputField = CourseData.fieldsInputType[i];
				if ((inputField instanceof JTextField))
					((JTextField)inputField).setText("");
			}
		}
		else
			for (int i = 0; i < CourseData.fieldsName.length; i++) {
				Component inputField = CourseData.fieldsInputType[i];
				if ((inputField instanceof JTextField))
					((JTextField)inputField).setText(mach.getInfoByFieldNumber(i));
			}
	}

	public PaymentData getPaymentDataFromTextFields() {
		PaymentData data = new PaymentData();
		for (int i = 0; i < PaymentData.fieldsName.length; i++) {
			String fieldname = PaymentData.fieldsName[i];
			String val = "";
			Component inputField = PaymentData.fieldsInputType[i];
			if (inputField instanceof JTextField) { 
				val = ((JTextField)inputField).getText();
				if (PaymentData.fieldsName[i].equals("cost")) {
					val = val.replace("€", "");
				}
			}
			else if (inputField instanceof JTextArea)
				val = ((JTextArea)inputField).getText();
			else if (inputField instanceof JDateChooser) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = ((JDateChooser)inputField).getDate();
				if (date != null)
					val = dateFormat.format(date);
			}
			else if (inputField instanceof JCheckBox) {
				JCheckBox checkBox = ((JCheckBox)inputField);
				if (checkBox.isSelected())
					val = "1";
				else
					val = "0";
			}
			else if (inputField instanceof JSpinner) {
				JSpinner spinner = ((JSpinner)inputField);
				Date date = (Date)spinner.getValue();
				DateFormat dateFormat = new SimpleDateFormat("hh:mm");
				val = dateFormat.format(date);
				System.out.println("SPINNER: " + val);
			}
				
			data.updateByFieldName(fieldname, val);
		}
		return data;
		
	}

	public void reset() {
		this.comboBox.refresh();
		for (int i = 0; i < PaymentData.fieldsName.length; i++) {
			Component inputField = PaymentData.fieldsInputType[i];
			if ((inputField instanceof JTextField))
				((JTextField)inputField).setText("");
			else if (inputField instanceof JTextArea)
				((JTextArea)inputField).setText("");
			else if (inputField instanceof JDateChooser) {
				JDateChooser dateChooser = (JDateChooser)inputField;
				((JDateChooser)inputField).setDate(new Date());
			}
		}
	}

	public void setEditable(boolean b)
	{
		for (int i = 0; i < PaymentData.fieldsName.length; i++) {
			Component inputField = PaymentData.fieldsInputType[i];
			if (inputField instanceof JTextField)
				((JTextField)inputField).setEditable(b);
			else if (inputField instanceof JTextArea) {
				((JTextArea)inputField).setEditable(b);
				((JTextArea)inputField).setOpaque(b);
			}
			else if (inputField instanceof JDateChooser)
				((JDateChooser)inputField).setEnabled(b);
			else if (inputField instanceof JCheckBox)
				((JCheckBox)inputField).setEnabled(b);
			else if (inputField instanceof JSpinner)
				((JSpinner)inputField).setEnabled(b);
		}
	}
}