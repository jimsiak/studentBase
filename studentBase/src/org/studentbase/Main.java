package org.studentbase;

import java.awt.EventQueue;

import org.studentbase.database.DatabaseManager;
import org.studentbase.gui.MainTabbedFrame;

public class Main
{
  public static DatabaseManager dbmanager;

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Main.dbmanager = new DatabaseManager();
          Main.dbmanager.createDatabase("studentsBase");
          Main.dbmanager.connect();
          Main.dbmanager.createStudentsTable("students");
          Main.dbmanager.createCoursesTable("courses");
          Main.dbmanager.createPaymentsTable("payments");
          //Main.dbmanager.createSpareTable("spares");

          MainTabbedFrame frame = new MainTabbedFrame();
          frame.setVisible(true);
        } catch (Exception e) {
        	System.out.println("LLLLOOOOOLLLL");
          e.printStackTrace();
        }
      }
    });
  }
}