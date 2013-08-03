package org.studentbase.gui;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.studentbase.Main;
import org.studentbase.database.DatabaseManager;

public class DbConnectionErrorDialog
{
  public DbConnectionErrorDialog()
  {
    JLabel addrLabel = new JLabel("Mysql Base Address");
    JTextField addrInput = new JTextField(10);
    addrLabel.setLabelFor(addrInput);
    addrInput.setText(Main.dbmanager.getDbAddress());

    JLabel userLabel = new JLabel("Username");
    JTextField userInput = new JTextField(10);
    userLabel.setLabelFor(userInput);
    userInput.setText(Main.dbmanager.getUsername());

    JLabel passLabel = new JLabel("Password");
    JPasswordField passInput = new JPasswordField(10);
    passInput.setEchoChar('*');
    passLabel.setLabelFor(passInput);
    passInput.setText(Main.dbmanager.getPassword());

    JPanel pane = new JPanel();

    pane.add(addrLabel);
    pane.add(addrInput);
    pane.add(userLabel);
    pane.add(userInput);
    pane.add(passLabel);
    pane.add(passInput);

    pane.setLayout(new GridLayout(3, 2));

    int res = JOptionPane.showConfirmDialog(null, pane, "Could not connect to database!", 
      2, 0);

    if (res == 0) {
      Main.dbmanager.setDbAddress(addrInput.getText());
      Main.dbmanager.setUsername(userInput.getText());
      Main.dbmanager.setPassword(passInput.getText());
    }
    else if ((res == 2) || 
      (res == -1)) {
      System.exit(1);
    }
  }
}