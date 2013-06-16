package org.customerbase.gui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.customerbase.database.Student;
import org.customerbase.database.StudentData;

public class StudentInputPanel extends JPanel
{
  public StudentListComboBox comboBox;

  public StudentInputPanel()
  {
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    setBorder(new TitledBorder(null, "Μαθητής", 4, 2, null, null));

    this.comboBox = new StudentListComboBox();
    this.comboBox.setParentPanel(this);
    add(this.comboBox);
    add(new JLabel());
    add(new JLabel());
    add(new JLabel());

    String[] labels = StudentData.fieldsLabels;
    int numPairs = labels.length;

    for (int i = 0; i < numPairs; i++) {
      JLabel l = new JLabel(labels[i], 11);
      add(l);
      l.setLabelFor(StudentData.fieldsInputType[i]);
      add(StudentData.fieldsInputType[i]);
    }

    if (numPairs % 2 != 0) {
      numPairs++;
    }

    numPairs += 2;

    setLayout(new GridLayout(numPairs, 2, 4, 4));
  }

  public Student getSpecifiedStudent()
  {
    if (this.comboBox.newCustomerSelected()) {
      return null;
    }
    return this.comboBox.getSelectedCustomer();
  }

  public void writeStudentToTextFields(Student cust)
  {
    if (cust == null)
      for (int i = 0; i < StudentData.fieldsName.length; i++) {
        Component inputField = StudentData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText("");
      }
    else
      for (int i = 0; i < StudentData.fieldsName.length; i++) {
        Component inputField = StudentData.fieldsInputType[i];
        if ((inputField instanceof JTextField))
          ((JTextField)inputField).setText(cust.getInfoByFieldNumber(i));
      }
  }

  public StudentData getStudentDataFromTextFields()
  {
    StudentData data = new StudentData();
    for (int i = 0; i < StudentData.fieldsName.length; i++) {
      String fieldname = StudentData.fieldsName[i];
      Component inputField = StudentData.fieldsInputType[i];
      if ((inputField instanceof JTextField)) {
        String val = ((JTextField)inputField).getText();
        data.updateByFieldName(fieldname, val);
      }
    }
    return data;
  }

  public void reset() {
    this.comboBox.refresh();
    for (int i = 0; i < StudentData.fieldsName.length; i++) {
      Component inputField = StudentData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setText("");
    }
  }

  public void setEditable(boolean b)
  {
    for (int i = 0; i < StudentData.fieldsName.length; i++) {
      Component inputField = StudentData.fieldsInputType[i];
      if ((inputField instanceof JTextField))
        ((JTextField)inputField).setEditable(b);
    }
  }

  public StudentListComboBox getComboBox() {
    return this.comboBox;
  }
}