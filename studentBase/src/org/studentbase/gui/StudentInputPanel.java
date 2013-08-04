package org.studentbase.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
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

import org.studentbase.database.PaymentData;
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
			c.anchor = GridBagConstraints.WEST;
			JLabel l = new JLabel(labels[i], SwingConstants.LEFT);
			add(l, c);

			JComponent comp_to_add = null;
			int columns = 20;
			c = new GridBagConstraints();
			c.insets = new Insets(3, 3, 3, 3);
			c.gridx = 1;
			c.ipady = 10;
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
				((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");
				//dateChooser.setDate(new Date());
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.gridwidth = 1;
				c.ipadx = 50;
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
				gridy++;
				c.weightx = 1.0;
				c.weighty = 2.0;
				c.gridheight = 1;

				comp_to_add = scrollPane;
			}
			else if (StudentData.fieldsInputType[i] instanceof JCheckBox) {
				JCheckBox checkBox = (JCheckBox) StudentData.fieldsInputType[i];
				checkBox.setSelected(false);
				c.gridy = gridy++;
				c.weightx = c.weighty = 1.0;
				c.gridheight = 1;
				c.gridwidth = 1;
				//c.ipadx = 50;
				c.anchor = GridBagConstraints.FIRST_LINE_START;

				comp_to_add = checkBox;
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

	public void writeStudentToTextFields(Student stud)
	{
		if (stud == null)
			this.reset();
		else
			for (int i = 0; i < StudentData.fieldsName.length; i++) {
				Component inputField = StudentData.fieldsInputType[i];
				if ((inputField instanceof JTextField))
					((JTextField)inputField).setText(stud.getInfoByFieldNumber(i));
				else if (inputField instanceof JTextArea)
					((JTextArea)inputField).setText(stud.getInfoByFieldNumber(i));
				else if (inputField instanceof JDateChooser) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = dateFormat.parse(stud.getInfoByFieldNumber(i));
						((JDateChooser)inputField).setDate(date);
					} catch (Exception e) {
						JDateChooser dateChooser = (JDateChooser)inputField;
						((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");
					}
				}
				else if (inputField instanceof JCheckBox) {
					JCheckBox checkBox = (JCheckBox)inputField;
					checkBox.setSelected(false);
					if (stud.getInfoByFieldNumber(i) != null) {
						int value = Integer.parseInt(stud.getInfoByFieldNumber(i));
						if (value > 0)
							checkBox.setSelected(true);
					}
				}
			}
	}

	public StudentData getStudentDataFromTextFields()
	{
		StudentData data = new StudentData();
		for (int i = 0; i < StudentData.fieldsName.length; i++) {
			String fieldname = StudentData.fieldsName[i];
			String val = "";
			Component inputField = StudentData.fieldsInputType[i];
			if (inputField instanceof JTextField) 
				val = ((JTextField)inputField).getText();
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
				
			data.updateByFieldName(fieldname, val);
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
		}
	}

	public StudentListComboBox getComboBox() {
		return this.comboBox;
	}
}