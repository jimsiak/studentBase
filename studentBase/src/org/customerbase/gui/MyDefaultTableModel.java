package org.customerbase.gui;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel
{
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }
}