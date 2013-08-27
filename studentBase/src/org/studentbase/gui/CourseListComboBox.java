package org.studentbase.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.studentbase.Main;
import org.studentbase.database.Course;
import org.studentbase.database.CourseData;

public class CourseListComboBox extends JComboBox<Course>
implements ActionListener
{
	private PaymentInputPanel parentPanel;
	Course noCourse;
	//Course registrationCourse;

	public CourseListComboBox()
	{

		CourseData data = new CourseData();
		data.updateByFieldName("yogatype", "Επιλογή μαθήματος...");
		this.noCourse = new Course(data);
		addItem(this.noCourse);

		data = new CourseData();
		data.updateByFieldName("yogatype", "Εγγραφή Μέλους");
		data.updateByFieldName("cost_members", "10");
		data.updateByFieldName("cost_nomembers", "10");
		//this.registrationCourse = new Course(data);
		//addItem(this.registrationCourse);

		setEditable(false);
		addActionListener(this);
		refresh();
	}

	public CourseListComboBox(String choiceStr)
	{

		CourseData data = new CourseData();
		data.updateByFieldName("yogatype", choiceStr);
		this.noCourse = new Course(data);
		addItem(this.noCourse);

		data = new CourseData();
		data.updateByFieldName("yogatype", "Εγγραφή Μέλους");
		data.updateByFieldName("cost_members", "10");
		data.updateByFieldName("cost_nomembers", "10");
		//this.registrationCourse = new Course(data);
		//addItem(this.registrationCourse);

		setEditable(false);
		addActionListener(this);
		refresh();
	}
	
	public boolean newCourseSelected() {
		return this.getSelectedItem().equals(this.noCourse);
	}

	public void refresh() {
		List courses = Main.dbmanager.getCoursesList();
		Course[] coursesArray = (Course[])courses.toArray(new Course[courses.size()]);

		DefaultComboBoxModel model = new DefaultComboBoxModel(coursesArray);
		model.insertElementAt(this.noCourse, 0);
		//model.insertElementAt(this.registrationCourse, 1);
		model.setSelectedItem(this.noCourse);
		setModel(model);

//		if (this.parentPanel != null) {
//			this.parentPanel.setEditable(true);
//			this.parentPanel.reset();
//		}
	}

	public void setParentPanel(PaymentInputPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public Course getSelectedCourse() {
		if (this.newCourseSelected())
			return null;
		return (Course)getSelectedItem();
	}

	public void actionPerformed(ActionEvent arg0)
	{
		CourseListComboBox comboBox = (CourseListComboBox)arg0.getSource();
		CourseListComboBox.this.parentPanel.writePaymentCost(comboBox.getSelectedCourse());
		/*** Dont need this, payment panel is always editable. ***/
		CourseListComboBox.this.parentPanel.setEditable(!comboBox.newCourseSelected());
	}
}