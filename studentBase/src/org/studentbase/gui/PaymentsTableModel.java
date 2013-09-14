package org.studentbase.gui;

import javax.swing.table.AbstractTableModel;

import org.studentbase.Main;

public class PaymentsTableModel extends AbstractTableModel {

	private String[] columnNames = {"Επώνυμο", "Όνομα", "Μάθημα", 
			"Ημερομηνία", "Ώρα", "Κόστος", "Εξοφλήθη", "Απόδειξη", "id"};
	private Object[][] data = Main.dbmanager.getStudentPaymentsByFilter(null, null, -1, -1);
	
	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		//		if (column == 6 || column == 7)
		//			return true;
		//		
		return false;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.length - 1;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return this.columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data[row][col];
	}
	
	@Override
	public Class getColumnClass(int c) {
		try {
			return getValueAt(0, c).getClass();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return String.class;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
