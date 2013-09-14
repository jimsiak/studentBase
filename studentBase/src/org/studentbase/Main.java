package org.studentbase;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.studentbase.database.DatabaseManager;
import org.studentbase.gui.MainTabbedFrame;

public class Main
{
	public static String VersionString = "0.1";
	
	public static DatabaseManager dbmanager;
	public static MainTabbedFrame mainFrame;

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

					mainFrame = new MainTabbedFrame();
					mainFrame.pack();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					System.out.println("LLLLOOOOOLLLL");
					e.printStackTrace();
				}
			}
		});
	}
}