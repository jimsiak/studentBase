package org.studentbase.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import org.studentbase.database.PaymentData;

import com.toedter.calendar.JDateChooser;

public class PaymentInputPanel extends JPanel
{
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
			
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			comp_to_add.setBorder(border);
			add(comp_to_add, c);
		}
	}

	public Course getSpecifiedCourse()
	{
		if (this.comboBox.newCourseSelected()) {
			return null;
		}
		return this.comboBox.getSelectedCourse();
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
		else
			costField.setText(course.getInfoByFieldName("cost_members"));
	}

	public void writeMachineToTextFields(Course mach)
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

	public CourseData getMachineDataFromTextFields() {
		CourseData data = new CourseData();
		for (int i = 0; i < CourseData.fieldsName.length; i++) {
			String fieldname = CourseData.fieldsName[i];
			Component inputField = CourseData.fieldsInputType[i];
			if ((inputField instanceof JTextField)) {
				String val = ((JTextField)inputField).getText();
				data.updateByFieldName(fieldname, val);
			}
		}
		return data;
	}

	public void reset() {
		for (int i = 0; i < CourseData.fieldsName.length; i++) {
			Component inputField = CourseData.fieldsInputType[i];
			if ((inputField instanceof JTextField))
				((JTextField)inputField).setText("");
		}
	}

	public void setEditable(boolean b)
	{
		for (int i = 0; i < CourseData.fieldsName.length; i++) {
			Component inputField = CourseData.fieldsInputType[i];
			if ((inputField instanceof JTextField))
				((JTextField)inputField).setEditable(b);
		}
	}
}