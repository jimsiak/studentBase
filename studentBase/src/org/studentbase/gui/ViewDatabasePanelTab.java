package org.studentbase.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewDatabasePanelTab extends JPanel
{
  private CustomerListJTable customerList;
  private MachineListJTable machineList;
  private ServiceListJTable serviceList;
  private SpareListJTable spareList;

  public ViewDatabasePanelTab()
  {
    add(new JLabel("ΠΕΛΑΤΕΣ:"));
    this.customerList = new CustomerListJTable();
    JScrollPane scrlpane = new JScrollPane(this.customerList);
    add(scrlpane);

    add(new JLabel("ΜΗΧΑΝΗΜΑΤΑ:"));
    this.machineList = new MachineListJTable();
    JScrollPane scrlpane2 = new JScrollPane(this.machineList);
    add(scrlpane2);
    this.customerList.setMachinesList(this.machineList);

    add(new JLabel("ΕΠΙΣΚΕΥΕΣ:"));
    this.serviceList = new ServiceListJTable();
    JScrollPane scrlpane3 = new JScrollPane(this.serviceList);
    add(scrlpane3);
    this.customerList.setServicesList(this.serviceList);
    this.machineList.setServicesList(this.serviceList);

    add(new JLabel("ΑΝΤΑΛΛΑΚΤΙΚΑ:"));
    this.spareList = new SpareListJTable();
    JScrollPane scrlpane4 = new JScrollPane(this.spareList);
    add(scrlpane4);
  }
}