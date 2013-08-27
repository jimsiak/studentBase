package org.studentbase.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.studentbase.Main;
import org.studentbase.database.Student;

public class StatisticsPanelTab extends JPanel 
implements ListSelectionListener {

	JList<Student> studentList;

	public StatisticsPanelTab() {
		this.setLayout(new BorderLayout());

		Student[] studentsArray = Main.dbmanager.getStudentsArray();
		Arrays.sort(studentsArray);
		studentList = new JList<>(studentsArray);
		studentList.addListSelectionListener(this);
		studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(studentList);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane, BorderLayout.LINE_START);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		boolean isAdjusting = e.getValueIsAdjusting();
		if (isAdjusting) return;

		JList<Student> list = (JList<Student>)e.getSource();

		Student selectedStudent = list.getSelectedValue();

		System.out.println(selectedStudent.getInfoByFieldName("lastname"));
	}
}
