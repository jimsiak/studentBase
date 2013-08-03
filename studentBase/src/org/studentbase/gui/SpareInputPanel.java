package org.studentbase.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.studentbase.database.Spare;
import org.studentbase.database.SpareData;

public class SpareInputPanel extends JPanel
{
  private JTable table;
  private DefaultTableModel model;

  public SpareInputPanel()
  {
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    setBorder(new TitledBorder(null, "Χρησιμοποιηθέντα Ανταλλακτικά", 
      4, 2, null, null));

    String[] columnNames = SpareData.fieldsLabels;
    int nrFields = columnNames.length;
    String[][] rowData = new String[5][nrFields];

    this.table = new JTable();
    this.table.setPreferredScrollableViewportSize(new Dimension(400, 80));
    this.model = new DefaultTableModel(rowData, columnNames);
    this.table.setModel(this.model);
    JScrollPane scrlpane = new JScrollPane(this.table);
    scrlpane.setVerticalScrollBarPolicy(21);
    add(scrlpane);
  }

  public List<Spare> getSpares() {
    List list = new ArrayList();

    this.model.fireTableDataChanged();
    for (int i = 0; i < this.model.getRowCount(); i++) {
      SpareData data = new SpareData();
      boolean flag = false;
      for (int j = 0; j < this.model.getColumnCount(); j++) {
        String cell = (String)this.model.getValueAt(i, j);
        if (cell != null) {
          data.updateByFieldNum(j, cell);
          flag = true;
        }
      }
      if (flag) {
        Spare spare = new Spare(data);
        list.add(spare);
      }
    }
    return list;
  }
}