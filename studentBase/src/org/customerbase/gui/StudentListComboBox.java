package org.customerbase.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.customerbase.Main;
import org.customerbase.database.Student;
import org.customerbase.database.StudentData;
import org.customerbase.database.DatabaseManager;

public class StudentListComboBox extends JComboBox<Student>
{
  private StudentInputPanel parentPanel;
  private CourseListComboBox machineListComboBox;
  Student newStud;

  public StudentListComboBox()
  {
    StudentData data = new StudentData();
    data.updateByFieldName("lastname", "Νέος Μαθητής");
    this.newStud = new Student(data);
    addItem(this.newStud);

    setEditable(false);
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        StudentListComboBox comboBox = (StudentListComboBox)arg0.getSource();
        if (comboBox.getSelectedItem().equals(StudentListComboBox.this.newStud)) {
          StudentListComboBox.this.parentPanel.reset();
          StudentListComboBox.this.machineListComboBox.refresh();
        } else {
          StudentListComboBox.this.parentPanel.writeStudentToTextFields(comboBox.getSelectedCustomer());
          StudentListComboBox.this.parentPanel.setEditable(false);
          //StudentListComboBox.this.machineListComboBox.refresh(comboBox.getSelectedCustomer());
        }
      }
    });
    refresh();
  }

  public void refresh() {
    Student[] students = Main.dbmanager.getStudentsArray();
    Arrays.sort(students);

    DefaultComboBoxModel model = new DefaultComboBoxModel(students);
    model.insertElementAt(this.newStud, 0);
    model.setSelectedItem(this.newStud);
    setModel(model);
    if (this.parentPanel != null)
      this.parentPanel.setEditable(true);
  }

  public void setParentPanel(StudentInputPanel parentPanel) {
    this.parentPanel = parentPanel;
  }

  public Student getSelectedCustomer() {
    return (Student)getSelectedItem();
  }

  public void setMachineListComboBox(CourseListComboBox machineListComboBox) {
    this.machineListComboBox = machineListComboBox;
  }

  public boolean newCustomerSelected() {
    return getSelectedItem().equals(this.newStud);
  }
}