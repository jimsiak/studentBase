package org.studentbase.gui;

import javax.swing.JTable;

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