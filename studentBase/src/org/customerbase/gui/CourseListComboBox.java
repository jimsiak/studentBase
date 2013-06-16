package org.customerbase.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.customerbase.Main;
import org.customerbase.database.Course;
import org.customerbase.database.CourseData;

public class CourseListComboBox extends JComboBox<Course>
{
  private PaymentInputPanel parentPanel;
  Course noCourse;

  public CourseListComboBox()
  {

    CourseData data = new CourseData();
    data.updateByFieldName("yogatype", "Επιλογή μαθήματος...");
    this.noCourse = new Course(data);
    addItem(this.noCourse);

    setEditable(false);
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        CourseListComboBox comboBox = (CourseListComboBox)arg0.getSource();
        if (comboBox.getSelectedItem().equals(CourseListComboBox.this.noCourse)) {
          CourseListComboBox.this.parentPanel.writePaymentCost(null);
          CourseListComboBox.this.parentPanel.setEditable(true);
        } else {
          CourseListComboBox.this.parentPanel.writePaymentCost(comboBox.getSelectedCourse());
          CourseListComboBox.this.parentPanel.setEditable(false);
        }
      }
    });
    refresh();
  }

  public boolean newMachineSelected() {
    return getSelectedItem().equals(this.noCourse);
  }

  public void refresh() {
    List courses = Main.dbmanager.getCoursesList();
    Course[] coursesArray = (Course[])courses.toArray(new Course[courses.size()]);

    DefaultComboBoxModel model = new DefaultComboBoxModel(coursesArray);
    model.insertElementAt(this.noCourse, 0);
    model.setSelectedItem(this.noCourse);
    setModel(model);

    if (this.parentPanel != null) {
      this.parentPanel.setEditable(true);
      this.parentPanel.reset();
    }
  }

  public void setParentPanel(PaymentInputPanel parentPanel) {
    this.parentPanel = parentPanel;
  }

  public Course getSelectedCourse() {
    return (Course)getSelectedItem();
  }
}