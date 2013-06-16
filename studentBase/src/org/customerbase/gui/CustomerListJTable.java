package org.customerbase.gui;

import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.customerbase.Main;
import org.customerbase.database.Student;
import org.customerbase.database.StudentData;
import org.customerbase.database.DatabaseManager;

public class CustomerListJTable extends JTable
{
  MyDefaultTableModel model;
  private MachineListJTable machinesList = null;
  private ServiceListJTable servicesList = null;
  Student[] students = null;

  public CustomerListJTable()
  {
    this.model = new MyDefaultTableModel();
    setModel(this.model);
    setPreferredScrollableViewportSize(new Dimension(800, 100));

    ListSelectionModel cellSelectionModel = getSelectionModel();
    cellSelectionModel.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if (CustomerListJTable.this.machinesList != null) {
          CustomerListJTable.this.machinesList.writeMachinesFromDatabase(CustomerListJTable.this.getSelectedCustomer());
        }
        if (CustomerListJTable.this.servicesList != null)
          CustomerListJTable.this.servicesList.writeServicesFromDatabase(CustomerListJTable.this.getSelectedCustomer());
      }
    });
    writeCustomersFromDatabase();
  }

  public void writeCustomersFromDatabase()
  {
    this.students = Main.dbmanager.getStudentsArray();
    Arrays.sort(this.students);
    int nrCustomers = this.students.length;

    String[] columnNames = StudentData.fieldsLabels;
    int nrFields = columnNames.length;

    String[][] rowData = new String[nrCustomers][nrFields];

    for (int i = 0; i < nrCustomers; i++) {
      Student cust = this.students[i];
      for (int j = 0; j < nrFields; j++) {
        rowData[i][j] = cust.getInfoByFieldNumber(j);
      }
    }
    this.model.setDataVector(rowData, columnNames);
    this.model.fireTableDataChanged();
  }

  public void setMachinesList(MachineListJTable machinesList)
  {
    this.machinesList = machinesList;
  }

  public Student getSelectedCustomer() {
    if (this.students == null) {
      return null;
    }
    return this.students[getSelectedRow()];
  }

  public void setServicesList(ServiceListJTable servicesList) {
    this.servicesList = servicesList;
  }
}