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

		setSize(new Dimension(700, 810));
		this.contentPane = new JTabbedPane();
		this.contentPane.setSize(new Dimension(790, 810));
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);

		NewStudentPanelTab newStudentPanelTab = new NewStudentPanelTab();
		StatisticsPanelTab statisticsPanelTab = new StatisticsPanelTab();
		PaymentsReceiptsPanelTab paymentsReceiptsPanelTab = 
				new PaymentsReceiptsPanelTab();
		//ViewDatabasePanelTab viewDatabasePanelTab = new ViewDatabasePanelTab();
		//ImportExportDatabasePanelTab importExportDatabasePanelTab = new ImportExportDatabasePanelTab();

		this.contentPane.addTab("Νέα επίσκεψη", newStudentPanelTab);
		this.contentPane.addTab("Πληρωμές/Αποδείξεις", paymentsReceiptsPanelTab);
		this.contentPane.addTab("Στατιστικά", statisticsPanelTab);
		//this.contentPane.addTab("Διαχείριση Βάσης", viewDatabasePanelTab);
		//this.contentPane.addTab("Αντίγραφα Ασφαλείας", importExportDatabasePanelTab);
	}
}