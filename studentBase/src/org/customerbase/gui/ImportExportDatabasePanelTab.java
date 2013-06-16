package org.customerbase.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.customerbase.Main;
import org.customerbase.database.DatabaseManager;

public class ImportExportDatabasePanelTab extends JPanel
  implements ActionListener
{
  JButton importButton;
  JButton exportButton;
  JTextArea log;
  JFileChooser fc;

  public ImportExportDatabasePanelTab()
  {
    super(new BorderLayout());

    this.log = new JTextArea(5, 20);
    this.log.setMargin(new Insets(5, 5, 5, 5));
    this.log.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(this.log);

    this.fc = new JFileChooser();

    this.importButton = new JButton("Ανάκτηση Βάσης από αρχείο...");
    this.importButton.addActionListener(this);

    this.exportButton = new JButton("Αποθήκευση Βάσης σε αρχείο...");
    this.exportButton.addActionListener(this);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(this.importButton);
    buttonPanel.add(this.exportButton);

    add(buttonPanel, "First");
    add(logScrollPane, "Center");
  }

  public void actionPerformed(ActionEvent arg0)
  {
    Object source = arg0.getSource();
    if (source.equals(this.importButton)) {
      int returnVal = this.fc.showOpenDialog(this);

      if (returnVal == 0) {
        File file = this.fc.getSelectedFile();

        this.log.append("Opening: " + file.getName() + "." + "\n");
        Main.dbmanager.importDatabase(file.getAbsolutePath());
      } else {
        this.log.append("Open command cancelled by user.\n");
      }
      this.log.setCaretPosition(this.log.getDocument().getLength());
    } else if (source.equals(this.exportButton)) {
      int returnVal = this.fc.showSaveDialog(this);
      if (returnVal == 0) {
        File file = this.fc.getSelectedFile();

        this.log.append("Saving: " + file.getName() + "." + "\n");
        Main.dbmanager.exportDatabase(file.getAbsolutePath());
      } else {
        this.log.append("Save command cancelled by user.\n");
      }
      this.log.setCaretPosition(this.log.getDocument().getLength());
    }
  }
}