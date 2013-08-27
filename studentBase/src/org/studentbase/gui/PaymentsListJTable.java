package org.studentbase.gui;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.studentbase.Main;
import org.studentbase.database.Course;
import org.studentbase.database.CourseData;
import org.studentbase.database.DatabaseManager;
import org.studentbase.database.Student;

public class PaymentsListJTable extends JTable
{
	MyDefaultTableModel model;
	String[] columnNames = {"Επώνυμο", "Όνομα", "Μάθημα", 
			"Ημερομηνία", "Ώρα", "Κόστος", "Εξοφλήθη", "Απόδειξη"};

	public PaymentsListJTable()
	{
		super();
		this.model = new MyDefaultTableModel();
		setModel(this.model);
	}

	public void refresh(Object[][] data)
	{
		this.model.setDataVector(data, columnNames);
		//this.model.fireTableDataChanged();
	}
}