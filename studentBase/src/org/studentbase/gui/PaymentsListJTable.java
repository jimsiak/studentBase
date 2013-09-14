package org.studentbase.gui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

public class PaymentsListJTable extends JTable
implements TableModelListener, ListSelectionListener
{
	private PaymentsTableModel model;

	private int[] columnsPreferredWidth = {200, 200, 200, 100, 100, 70, 50, 50};
	private int[] columnsMaxWidth = {500, 500, 500, 200, 200, 70, 70, 70};
	
	public PaymentsListJTable()
	{
		super();
		this.model = new PaymentsTableModel();
		this.setModel(this.model);

		this.setColumnSelectionAllowed(false);
		this.setRowSelectionAllowed(true);
		this.setAutoCreateRowSorter(true);
		this.setFillsViewportHeight(true);

		for (int i=0; i<this.getColumnModel().getColumnCount(); i++) {
			TableColumn column = this.getColumnModel().getColumn(i);
			column.setPreferredWidth(this.columnsPreferredWidth[i]);
			column.setMaxWidth(this.columnsMaxWidth[i]);
		}
	}

	public int[] getSelectedPaymentsIds (){
		int[] selectedRows = this.getSelectedRows();
		int[] selectedIds = new int[selectedRows.length];
		PaymentsTableModel tbm = this.model;
		
		for (int i=0; i < selectedRows.length; i++) 
			selectedIds[i] = (Integer)tbm.getValueAt(selectedRows[i], 8);
		
		return selectedIds;
	}
	
	public void refresh(Object[][] data)
	{	
		this.model.setData(data);
		this.model.fireTableDataChanged();
	}
}