package org.studentbase.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.studentbase.Main;
import org.studentbase.database.Student;
import org.studentbase.database.StudentData;

public class StudentListComboBox extends JComboBox<Student>
implements ActionListener
{
	private StudentInputPanel parentPanel;
	private CourseListComboBox machineListComboBox;
	Student newStud;

	public StudentListComboBox()
	{
		StudentData data = new StudentData();
		data.updateByFieldName("lastname", "Νέος Μαθητής");
		this.newStud = new Student(data);
		addItem(this.newStud);

		setEditable(false);
		addActionListener(this);
		refresh();
	}

	public void refresh() {
		Student[] students = Main.dbmanager.getStudentsArray();
		Arrays.sort(students);

		DefaultComboBoxModel model = new DefaultComboBoxModel(students);
		model.insertElementAt(this.newStud, 0);
		model.setSelectedItem(this.newStud);
		setModel(model);
		if (this.parentPanel != null)
			this.parentPanel.setEditable(true);
	}

	public void setParentPanel(StudentInputPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public Student getSelectedStudent() {
		return (Student)getSelectedItem();
	}

	public void setMachineListComboBox(CourseListComboBox machineListComboBox) {
		this.machineListComboBox = machineListComboBox;
	}

	public boolean newStudentSelected() {
		return getSelectedItem().equals(this.newStud);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		StudentListComboBox comboBox = (StudentListComboBox)e.getSource();
		if (comboBox.getSelectedItem().equals(StudentListComboBox.this.newStud)) {
			StudentListComboBox.this.parentPanel.reset();
			//StudentListComboBox.this.machineListComboBox.refresh();
		} else {
			StudentListComboBox.this.parentPanel.writeStudentToTextFields(comboBox.getSelectedStudent());
			StudentListComboBox.this.parentPanel.setEditable(false);
			//StudentListComboBox.this.machineListComboBox.refresh(comboBox.getSelectedCustomer());
		}
	}
}