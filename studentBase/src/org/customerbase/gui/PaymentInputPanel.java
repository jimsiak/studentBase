package org.customerbase.gui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.TitledBorder;

import org.customerbase.database.Course;
import org.customerbase.database.CourseData;
import org.customerbase.database.PaymentData;

import com.toedter.calendar.JDateChooser;

public class PaymentInputPanel extends JPanel
{
	public CourseListComboBox comboBox;

	public PaymentInputPanel()
	{
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new TitledBorder(null, "Πληρωμή", 4, 2, null, null));

		this.comboBox = new CourseListComboBox();
		this.comboBox.setParentPanel(this);
		add(this.comboBox);
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());

		String[] labels = PaymentData.fieldsLabels;
		int numPairs = labels.length;

		/* For future use. Show description on mouse focus. */
		//((JTextField)PaymentData.fieldsInputType[0]).setToolTipText("This is description");
		
		for (int i = 0; i < numPairs; i++) {
			String name = PaymentData.fieldsName[i];
			JLabel l = new JLabel(labels[i], 11);
			add(l);
			l.setLabelFor(PaymentData.fieldsInputType[i]);
			if (name.equals("date")) {
				Date today = new Date();
				JDateChooser dateChooser = new JDateChooser();
				dateChooser.setDate(today);
				add(dateChooser);
			} else if (name.equals("time")) {
				SpinnerModel model = new SpinnerDateModel();
				JSpinner timeSpinner = new JSpinner(model);
				JComponent editor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
				timeSpinner.setEditor(editor);
				add(timeSpinner);
			} else
				add(PaymentData.fieldsInputType[i]);
		}

		if (numPairs % 2 != 0) {
			numPairs++;
		}

		numPairs += 2;

		setLayout(new GridLayout(numPairs, 2, 4, 4));
	}

	public Course getSpecifiedMachine()
	{
		if (this.comboBox.newMachineSelected()) {
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
			costField.setText(course.getInfoByFieldName("cost"));
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