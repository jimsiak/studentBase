package org.studentbase.gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.studentbase.Main;
import org.studentbase.database.Student;

public class StudentStatisticsPanel extends JPanel {

	private StudentInputPanel studentInputPanel;

	private JLabel nrPaymentsLabel;
	private JLabel lastVisitLabel;
	
	public StudentStatisticsPanel() {
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setBorder(new TitledBorder(null, "Στατιστικά", 4, 2, null, null));

		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c;
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = c.weighty = 1.0;
		c.gridheight = 1;
		JLabel label = new JLabel("Αριθμός επισκέψεων:");
		label.setAlignmentX(LEFT_ALIGNMENT);
		add(label, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = c.weighty = 1.0;
		nrPaymentsLabel = new JLabel("-");
		nrPaymentsLabel.setAlignmentX(RIGHT_ALIGNMENT);
		add(nrPaymentsLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = c.weighty = 1.0;
		label = new JLabel("Τελευταία Επίσκεψη:");
		label.setAlignmentX(LEFT_ALIGNMENT);
		add(label, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = c.weighty = 1.0;
		lastVisitLabel = new JLabel("-");
		lastVisitLabel.setAlignmentX(RIGHT_ALIGNMENT);
		add(lastVisitLabel, c);
	}
	
	public void setStudentInputPanel(StudentInputPanel studentInputPanel) {
		this.studentInputPanel = studentInputPanel;
	}
	
	public void refresh() {
		Student stud = this.studentInputPanel.getSpecifiedStudent();
		
		if (stud == null) {
			this.nrPaymentsLabel.setText("-");
			this.lastVisitLabel.setText("-");
		}
		else {
			int nrVisits = Main.dbmanager.getStudentNumberOfVisits(stud);
			this.nrPaymentsLabel.setText(Integer.toString(nrVisits));
			
			String lastVisit = Main.dbmanager.getStudentLastVisit(stud);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (lastVisit != null) {
					Date date = dateFormat.parse(lastVisit);
					dateFormat = new SimpleDateFormat("dd MMM yyyy");
					lastVisit = dateFormat.format(date);
				}
				else
					lastVisit = "-";
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.lastVisitLabel.setText(lastVisit);
		}
	}
	
}
