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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.studentbase.Main;
import org.studentbase.database.Course;
import org.studentbase.database.Student;

public class PaymentsReceiptsPanelTab extends JPanel
implements ActionListener {
	PaymentsListJTable jTable;
	JLabel totalStatsLabel = new JLabel();

	StudentListComboBox studentFilterComboBox;
	CourseListComboBox courseFilterComboBox;
	JComboBox<String> paymentFilterComboBox;
	JComboBox<String> receiptFilterComboBox;
	
	String[] paymentComboBoxStrings = {"Όλα", "Απλήρωτα", "Πληρωμένα"};
	String[] receiptComboBoxStrings = {"Όλα", "Χωρίς Απόδειξη", "Με απόδειξη"};

	Student filterStudent = null;
	Course filterCourse = null;
	Integer filterPayed = -1;
	Integer filterReceipt = -1;

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

		c.gridy++;
		this.courseFilterComboBox = new CourseListComboBox("Όλα τα μαθήματα");
		als = this.courseFilterComboBox.getActionListeners();
		for (int i=0; i < als.length; i++)
			this.courseFilterComboBox.removeActionListener(als[i]);
		this.courseFilterComboBox.addActionListener(this);
		panel.add(this.courseFilterComboBox, c);

		c.gridy++;
		JPanel newPane = new JPanel();
		newPane.add(new JLabel("Πληρωμή: "));
		this.paymentFilterComboBox = new JComboBox<String>(this.paymentComboBoxStrings);
		this.paymentFilterComboBox.addActionListener(this);
		newPane.add(this.paymentFilterComboBox);
		panel.add(newPane, c);
		
		c.gridy++;
		newPane = new JPanel();
		newPane.add(new JLabel("Απόδειξη: "));
		this.receiptFilterComboBox = new JComboBox<String>(this.receiptComboBoxStrings);
		this.receiptFilterComboBox.addActionListener(this);
		newPane.add(this.receiptFilterComboBox);
		panel.add(newPane, c);
		
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

		JPanel totalStatsPanel = new JPanel();
		//totalStatsPanel.setLayout(new BoxLayout(totalStatsPanel, BoxLayout.X_AXIS));
		totalStatsPanel.setLayout(new BorderLayout());
		JLabel totalLabel = new JLabel("Σύνολο:");
		totalLabel.setHorizontalAlignment(SwingConstants.LEFT);
		totalStatsPanel.add(totalLabel, BorderLayout.LINE_START);
		this.totalStatsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalStatsPanel.add(this.totalStatsLabel, BorderLayout.CENTER);
		panel.add(totalStatsPanel, BorderLayout.PAGE_END);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == PaymentsReceiptsPanelTab.this.studentFilterComboBox) {
			StudentListComboBox comboBox = PaymentsReceiptsPanelTab.this.studentFilterComboBox;
			Student stud = PaymentsReceiptsPanelTab.this.studentFilterComboBox.getSelectedStudent();
			if (comboBox.newStudentSelected())
				stud = null;
			PaymentsReceiptsPanelTab.this.filterStudent = stud;
		} else if (src == PaymentsReceiptsPanelTab.this.courseFilterComboBox) {
			CourseListComboBox comboBox = PaymentsReceiptsPanelTab.this.courseFilterComboBox;
			Course course = PaymentsReceiptsPanelTab.this.courseFilterComboBox.getSelectedCourse();
			if (comboBox.newCourseSelected())
				course = null;
			PaymentsReceiptsPanelTab.this.filterCourse = course;
		} else if (src == PaymentsReceiptsPanelTab.this.paymentFilterComboBox) {
			JComboBox<String> comboBox = PaymentsReceiptsPanelTab.this.paymentFilterComboBox;
			this.filterPayed = comboBox.getSelectedIndex() - 1;
		} else if (src == PaymentsReceiptsPanelTab.this.receiptFilterComboBox) {
			JComboBox<String> comboBox = PaymentsReceiptsPanelTab.this.receiptFilterComboBox;
			this.filterReceipt = comboBox.getSelectedIndex() - 1;
		}

		PaymentsReceiptsPanelTab.this.refresh();
	}

	public void refresh ()
	{
		Object[][] data = Main.dbmanager.getStudentPaymentsByFilter(this.filterStudent, this.filterCourse, 
				this.filterPayed, this.filterReceipt);
		this.jTable.refresh(data);

		String[] totalStats = Main.dbmanager.getStudentPaymentsByFilterTotalStats(this.filterStudent, this.filterCourse, 
				this.filterPayed, this.filterReceipt);
		String str = (totalStats[0].equals("1")) ? "εγγραφή" : "εγγραφές";
		this.totalStatsLabel.setText(totalStats[0] + " " + str + ",   " + 
				totalStats[1] + "€");
	}

}
