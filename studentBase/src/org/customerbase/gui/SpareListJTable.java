package org.customerbase.gui;

import java.awt.Dimension;
import java.io.PrintStream;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.customerbase.Main;
import org.customerbase.database.Student;
import org.customerbase.database.DatabaseManager;
import org.customerbase.database.Course;
import org.customerbase.database.Payment;
import org.customerbase.database.PaymentData;

public class SpareListJTable extends JTable
{
  MyDefaultTableModel model;

  public SpareListJTable()
  {
    this.model = new MyDefaultTableModel();
    setModel(this.model);
    setPreferredScrollableViewportSize(new Dimension(800, 100));
    setAutoCreateRowSorter(true);

    ListSelectionModel cellSelectionModel = getSelectionModel();
    cellSelectionModel.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        System.out.println("HEEEELLO");
      }
    });
  }

  public void writeSparesFromDatabase(Student cust)
  {
    List servicesList = Main.dbmanager.getStudentPaymentsList(cust);
    Payment[] services = (Payment[])servicesList.toArray(new Payment[servicesList.size()]);
    int nrServices = services.length;

    String[] columnNames = PaymentData.fieldsLabels;
    int nrFields = columnNames.length;

    String[][] rowData = new String[nrServices][nrFields];

    for (int i = 0; i < nrServices; i++) {
      Payment serv = services[i];
      for (int j = 0; j < nrFields; j++) {
        rowData[i][j] = serv.getInfoByFieldNumber(j);
      }
    }
    this.model.setDataVector(rowData, columnNames);
    this.model.fireTableDataChanged();
  }

  public void writeServicesFromDatabase(Course mach)
  {
    List servicesList = Main.dbmanager.getMachineServicesList(mach);
    Payment[] services = (Payment[])servicesList.toArray(new Payment[servicesList.size()]);
    int nrServices = services.length;

    String[] columnNames = PaymentData.fieldsLabels;
    int nrFields = columnNames.length;

    String[][] rowData = new String[nrServices][nrFields];

    for (int i = 0; i < nrServices; i++) {
      Payment serv = services[i];
      for (int j = 0; j < nrFields; j++) {
        rowData[i][j] = serv.getInfoByFieldNumber(j);
      }
    }
    this.model.setDataVector(rowData, columnNames);
    this.model.fireTableDataChanged();
  }
}