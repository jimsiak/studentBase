package org.studentbase.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class MainTabbedFrame extends JFrame
{
  private JTabbedPane contentPane;

  public MainTabbedFrame()
  {
    setTitle("ServiceBase");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setSize(new Dimension(900, 650));
    this.contentPane = new JTabbedPane();
    this.contentPane.setSize(new Dimension(900, 300));
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(this.contentPane);

    NewStudentPanelTab newStudentPanelTab = new NewStudentPanelTab();
    ViewDatabasePanelTab viewDatabasePanelTab = new ViewDatabasePanelTab();
    ImportExportDatabasePanelTab importExportDatabasePanelTab = new ImportExportDatabasePanelTab();

    this.contentPane.addTab("Νέα επίσκεψη", newStudentPanelTab);
    this.contentPane.addTab("Διαχείριση Βάσης", viewDatabasePanelTab);
    this.contentPane.addTab("Αντίγραφα Ασφαλείας", importExportDatabasePanelTab);
  }
}