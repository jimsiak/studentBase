package org.studentbase.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.studentbase.database.Student;
import org.studentbase.database.StudentData;

import com.toedter.calendar.JDateChooser;

public class StudentInputPanel extends JPanel
{
	public StudentListComboBox comboBox;

	public StudentInputPanel()
	{
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new TitledBorder(null, "Μαθητής", 4, 2, null, null));

		this.setLayout(new GridBagLayout());
		GridBagConstraints c;

		String[] labels = StudentData.fieldsLabels;
		int numPairs = labels.length;

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 5, 15, 0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.comboBox = new StudentListComboBox();
		this.comboBox.setParentPanel(this);
		add(this.comboBox, c);

		int gridy = 2;
		for (int i = 0; i < numPairs; i++) {
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = gridy;
			c.weightx = 0.1; 
			c.weighty = 1.0;
			c.insets = new Insets(0, 5, 0, 0);
			//c.gridheight = 1;
			c.anchor = GridBagConstraints.WEST;
			JLabel l = new JLabel(labels[i], SwingConstants.LEFT);
			add(l, c);

			JComponent comp_to_add = null;
			int columns = 20;
			c = new GridBagConstraints();
			c.insets = new Insets(3, 3, 3, 3);
			c.gridx = 1;
			if (StudentData.fieldsInputType[i] instanceof JTextField) {
				JTextField textField = (JTextField) StudentData.fieldsInputType[i];
				textField.setColumns(columns);
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.anchor = GridBagConstraints.FIRST_LINE_START;

				comp_to_add = textField;
			}
			else if (StudentData.fieldsInputType[i] instanceof JDateChooser) {
				JDateChooser dateChooser = (JDateChooser) StudentData.fieldsInputType[i];
				dateChooser.setDate(new Date());
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.FIRST_LINE_START;

				comp_to_add = dateChooser;
			}
			else if (StudentData.fieldsInputType[i] instanceof JTextArea) {
				JTextArea textArea = (JTextArea) StudentData.fieldsInputType[i];
				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setBorder(null);
				textArea.setRows(5);
				textArea.setColumns(columns);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				c.gridy = gridy;
				gridy += 2;
				c.weightx = 1.0;
				c.weighty = 2.0;
				c.gridheight = 1;

				comp_to_add = scrollPane;
			}

			Border border = BorderFactory.createLineBorder(Color.BLACK);
			comp_to_add.setBorder(border);
			add(comp_to_add, c);
		}

	}

	public Student getSpecifiedStudent()
	{
		if (this.comboBox.newStudentSelected()) {
			return null;
		}
		return this.comboBox.getSelectedStudent();
	}

	public void writeStudentToTextFields(Student cust)
	{
		if (cust == null)
			for (int i = 0; i < StudentData.fieldsName.length; i++) {
				Component inputField = StudentData.fieldsInputType[i];
				if ((inputField instanceof JTextField))
					((JTextField)inputField).setText("");
			}
		else
			for (int i = 0; i < StudentData.fieldsName.length; i++) {
				Component inputField = StudentData.fieldsInputType[i];
				if ((inputField instanceof JTextField))
					((JTextField)inputField).setText(cust.getInfoByFieldNumber(i));
			}
	}

	public StudentData getStudentDataFromTextFields()
	{
		StudentData data = new StudentData();
		for (int i = 0; i < StudentData.fieldsName.length; i++) {
			String fieldname = StudentData.fieldsName[i];
			Component inputField = StudentData.fieldsInputType[i];
			if ((inputField instanceof JTextField)) {
				String val = ((JTextField)inputField).getText();
				data.updateByFieldName(fieldname, val);
			}
		}
		return data;
	}

	public void reset() {
		this.comboBox.refresh();
		for (int i = 0; i < StudentData.fieldsName.length; i++) {
			Component inputField = StudentData.fieldsInputType[i];
			if ((inputField instanceof JTextField))
				((JTextField)inputField).setText("");
			else if (inputField instanceof JTextArea)
				((JTextArea)inputField).setText("");
			else if (inputField instanceof JDateChooser)
				((JDateChooser)inputField).setDate(new Date());
		}
	}

	public void setEditable(boolean b)
	{
		for (int i = 0; i < StudentData.fieldsName.length; i++) {
			Component inputField = StudentData.fieldsInputType[i];
			if ((inputField instanceof JTextField))
				((JTextField)inputField).setEditable(b);
		}
	}

	public StudentListComboBox getComboBox() {
		return this.comboBox;
	}
}