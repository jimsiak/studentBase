package org.customerbase.gui;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.customerbase.Main;
import org.customerbase.database.Student;
import org.customerbase.database.DatabaseManager;
import org.customerbase.database.Course;
import org.customerbase.database.CourseData;

public class MachineListJTable extends JTable
{
  private ServiceListJTable servicesList;
  MyDefaultTableModel model;
  private Course[] machines;

  public MachineListJTable()
  {
    this.model = new MyDefaultTableModel();
    setModel(this.model);
    setPreferredScrollableViewportSize(new Dimension(800, 100));

    ListSelectionModel cellSelectionModel = getSelectionModel();
    cellSelectionModel.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if (MachineListJTable.this.servicesList != null) {
          int row = MachineListJTable.this.getSelectedRow();
          if (row >= 0)
            MachineListJTable.this.servicesList.writeServicesFromDatabase(MachineListJTable.this.getSelectedMachine());
        }
      }
    });
  }

  public void writeMachinesFromDatabase(Student cust)
  {
    List machinesList = Main.dbmanager.getCoursesList();
    this.machines = ((Course[])machinesList.toArray(new Course[machinesList.size()]));
    Arrays.sort(this.machines);
    int nrMachines = this.machines.length;

    String[] columnNames = CourseData.fieldsLabels;
    int nrFields = columnNames.length;

    String[][] rowData = new String[nrMachines][nrFields];

    for (int i = 0; i < nrMachines; i++) {
      Course mach = this.machines[i];
      for (int j = 0; j < nrFields; j++) {
        rowData[i][j] = mach.getInfoByFieldNumber(j);
      }
    }
    this.model.setDataVector(rowData, columnNames);
    this.model.fireTableDataChanged();
  }

  public Course getSelectedMachine()
  {
    if (this.machines == null) {
      return null;
    }
    return this.machines[getSelectedRow()];
  }

  public void setServicesList(ServiceListJTable servicesList) {
    this.servicesList = servicesList;
  }
}