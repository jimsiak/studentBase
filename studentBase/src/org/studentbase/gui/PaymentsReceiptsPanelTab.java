package org.studentbase.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.studentbase.Main;
import org.studentbase.database.Course;
import org.studentbase.database.Student;

public class PaymentsReceiptsPanelTab extends JPanel
implements ActionListener {
	PaymentsListJTable jTable;
	
	StudentListComboBox studentFilterComboBox;
	CourseListComboBox courseFilterComboBox;
	
	Student filterStudent = null;
	Course filterCourse = null;

	public PaymentsReceiptsPanelTab() {
		this.setLayout(new BorderLayout());

		this.add(createFilterPanel(), BorderLayout.LINE_START);
		this.add(createViewPanel(), BorderLayout.CENTER);
		
		this.refresh();
	}

	private JPanel createFilterPanel() {
		JPanel outPanel = new JPanel();
		JPanel panel = new JPanel();
		
		outPanel.setLayout(new BorderLayout());
		outPanel.setBorder(new TitledBorder(null, "Filter", 4, 2, null, null));
		
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c;
		c = new GridBagConstraints();
		c.weightx = c.weighty = 0;
		c.gridx = 0; c.gridy = 0;
		c.insets = new Insets(0, 0, 5, 0);
		c.anchor = GridBagConstraints.LINE_START;
		this.studentFilterComboBox = new StudentListComboBox("Όλοι οι μαθητές");
		ActionListener[] als = this.studentFilterComboBox.getActionListeners();
		for (int i=0; i < als.length; i++)
			this.studentFilterComboBox.removeActionListener(als[i]);
		this.studentFilterComboBox.addActionListener(this);
		panel.add(this.studentFilterComboBox, c);

		c.gridy = 1;
		this.courseFilterComboBox = new CourseListComboBox("Όλα τα μαθήματα");
		als = this.courseFilterComboBox.getActionListeners();
		for (int i=0; i < als.length; i++)
			this.courseFilterComboBox.removeActionListener(als[i]);
		this.courseFilterComboBox.addActionListener(this);
		panel.add(this.courseFilterComboBox, c);

		outPanel.add(panel, BorderLayout.PAGE_START);
		return outPanel;
	}

	private JPanel createViewPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder(null, "", 4, 2, null, null));
		
		this.jTable = new PaymentsListJTable();
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jTable.setFillsViewportHeight(true);
		jTable.setAutoCreateRowSorter(true);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == PaymentsReceiptsPanelTab.this.studentFilterComboBox) {
			StudentListComboBox comboBox = PaymentsReceiptsPanelTab.this.studentFilterComboBox;
			Student stud = PaymentsReceiptsPanelTab.this.studentFilterComboBox.getSelectedStudent();
			if (PaymentsReceiptsPanelTab.this.studentFilterComboBox.newStudentSelected())
				stud = null;
			PaymentsReceiptsPanelTab.this.filterStudent = stud;
		} else if (src == PaymentsReceiptsPanelTab.this.courseFilterComboBox) {
			CourseListComboBox comboBox = PaymentsReceiptsPanelTab.this.courseFilterComboBox;
			Course course = PaymentsReceiptsPanelTab.this.courseFilterComboBox.getSelectedCourse();
			if (PaymentsReceiptsPanelTab.this.courseFilterComboBox.newCourseSelected())
				course = null;
			PaymentsReceiptsPanelTab.this.filterCourse = course;
		}
		
		PaymentsReceiptsPanelTab.this.refresh();
	}
	
	private void refresh ()
	{
		Object[][] data = Main.dbmanager.getStudentPaymentsByFilter(this.filterStudent, this.filterCourse);
		this.jTable.refresh(data);
	}

}
