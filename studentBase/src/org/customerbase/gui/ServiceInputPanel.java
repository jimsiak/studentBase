package org.customerbase.gui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.customerbase.database.Payment;
import org.customerbase.database.PaymentData;

public class ServiceInputPanel extends JPanel
{
  public ServiceInputPanel()
  {
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    setBorder(new TitledBorder(null, "Επισκευή", 
      4, 2, null, null));
    setLayout(new GridBagLayout());

    String[] labels = PaymentData.fieldsLabels;
    Component[] inputFields = PaymentData.fieldsInputType;
    int numPairs = labels.length;

//    JLabel l = new JLabel(labels[0], 11);
//    JTextArea desc = (JTextArea)inputFields[0];
//    desc.setColumns(8);
//    desc.setRows(3);
//    desc.setLineWrap(true);
//    desc.setWrapStyleWord(true);
//    JScrollPane scroll = new JScrollPane(desc);
//    l.setLabelFor(scroll);
//
//    GridBagConstraints c = new GridBagConstraints();
//    c.gridx = 0; c.gridy = 0;
//    c.gridheight = 1; c.gridwidth = 1;
//    c.anchor = 23;
//    add(l, c);
//
//    c = new GridBagConstraints();
//    c.gridx = 1; c.gridy = 0;
//    c.gridheight = 2; c.gridwidth = 2;
//    add(scroll, c);
//    
//    l = new JLabel(labels[1], 11);
//    desc = (JTextArea)inputFields[1];
//    desc.setColumns(8);
//    desc.setRows(3);
//    desc.setLineWrap(true);
//    desc.setWrapStyleWord(true);
//    scroll = new JScrollPane(desc);
//    l.setLabelFor(scroll);
//
//    c = new GridBagConstraints();
//    c.gridx = 0; c.gridy = 2;
//    c.gridheight = 1; c.gridwidth = 1;
//    c.anchor = 23;
//    add(l, c);
//
//    c = new GridBagConstraints();
//    c.gridx = 1; c.gridy = 2;
//    c.gridheight = 2; c.gridwidth = 2;
//    add(scroll, c);
  }

  public Payment getSpecifiedService()
  {
    PaymentData data = getServiceDataFromTextFields();
    return new Payment(data);
  }

  public void writeServiceToTextFields(Payment serv)
  {
    if (serv == null) {
      for (int i = 0; i < PaymentData.fieldsName.length; i++) {
        Component inputField = PaymentData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText("");
        else if ((inputField instanceof JTextArea)) {
          ((JTextArea)inputField).setText("");
        }
      }
    }
    else
      for (int i = 0; i < PaymentData.fieldsName.length; i++) {
        Component inputField = org.customerbase.database.CourseData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText(serv.getInfoByFieldNumber(i));
        else if ((inputField instanceof JTextArea))
          ((JTextArea)inputField).setText(serv.getInfoByFieldNumber(i));
      }
  }

  public PaymentData getServiceDataFromTextFields() {
    PaymentData data = new PaymentData();
    for (int i = 0; i < PaymentData.fieldsName.length; i++) {
      String fieldname = PaymentData.fieldsName[i];
      Component inputField = PaymentData.fieldsInputType[i];
      if ((inputField instanceof JTextField)) {
        String val = ((JTextField)inputField).getText();
        data.updateByFieldName(fieldname, val);
      }
      else if ((inputField instanceof JTextArea)) {
        String val = ((JTextArea)inputField).getText();
        data.updateByFieldName(fieldname, val);
      }
    }
    return data;
  }

  public void setEditable(boolean b) {
    for (int i = 0; i < PaymentData.fieldsName.length; i++) {
      Component inputField = PaymentData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setEditable(b);
      else if ((inputField instanceof JTextArea))
        ((JTextArea)inputField).setEditable(b);
    }
  }

  public void reset() {
    for (int i = 0; i < PaymentData.fieldsName.length; i++) {
      Component inputField = PaymentData.fieldsInputType[i];
      if ((inputField instanceof JTextField)) {
        ((JTextField)inputField).setText("");
      }
      else if ((inputField instanceof JTextArea))
        ((JTextArea)inputField).setText("");
    }
  }
}