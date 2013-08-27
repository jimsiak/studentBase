package org.studentbase.gui;

import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.RowData;

public class MyDefaultTableModel extends DefaultTableModel
{

	public Class getColumnClass(int c) {
		try {
			return getValueAt(0, c).getClass();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return String.class;
		}
	}

	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
}