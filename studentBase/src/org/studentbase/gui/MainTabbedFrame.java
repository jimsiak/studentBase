package org.studentbase.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainTabbedFrame extends JFrame
implements ChangeListener
{
	private JTabbedPane contentPane;

	private NewStudentPanelTab newStudentPanelTab;
	private StatisticsPanelTab statisticsPanelTab;
	private PaymentsReceiptsPanelTab paymentsReceiptsPanelTab;
	
	public MainTabbedFrame(String title)
	{
		super(title);
		//setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(new Dimension(700, 810));
		this.contentPane = new JTabbedPane();
		this.contentPane.setSize(new Dimension(790, 810));
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.addChangeListener(this);
		setContentPane(this.contentPane);

		newStudentPanelTab = new NewStudentPanelTab();
		statisticsPanelTab = new StatisticsPanelTab();
		paymentsReceiptsPanelTab = new PaymentsReceiptsPanelTab();
		//ViewDatabasePanelTab viewDatabasePanelTab = new ViewDatabasePanelTab();
		//ImportExportDatabasePanelTab importExportDatabasePanelTab = new ImportExportDatabasePanelTab();

		this.contentPane.addTab("Νέα επίσκεψη", newStudentPanelTab);
		this.contentPane.addTab("Πληρωμές/Αποδείξεις", paymentsReceiptsPanelTab);
		//this.contentPane.addTab("Στατιστικά", statisticsPanelTab);
		//this.contentPane.addTab("Διαχείριση Βάσης", viewDatabasePanelTab);
		//this.contentPane.addTab("Αντίγραφα Ασφαλείας", importExportDatabasePanelTab);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		JTabbedPane tabbedPane = (JTabbedPane) arg0.getSource();
		int selectedInd = tabbedPane.getSelectedIndex();
		
		if (selectedInd == 1) {
			MainTabbedFrame.this.paymentsReceiptsPanelTab.refresh();
		}
	}
}