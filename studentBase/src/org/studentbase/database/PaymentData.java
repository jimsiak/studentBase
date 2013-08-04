package org.studentbase.database;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class PaymentData
{
  private Map<String, String> data;
  
  public static final String[] fieldsLabels = { 
    "Ημερομηνία", "Ώρα", "Κόστος", "Εξοφλήθη" 
  };

  public static final String[] fieldsTypes = { 
    "DATE", "TIME", "INT", "BOOLEAN" 
  };

  public static final String[] fieldsName = { 
    "date", "time", "cost", "payed" 
  };

  public static final Component[] fieldsInputType = { 
	  new JDateChooser(), new JSpinner(), 
	  new JTextField(5), new JCheckBox() 
  };

  public PaymentData()
  {
    this.data = new HashMap();
    for (int i = 0; i < fieldsName.length; i++)
      this.data.put(fieldsName[i], "");
  }

  public void updateByFieldName(String fieldname, String val)
  {
    if (!this.data.containsKey(fieldname))
      return;
    this.data.put(fieldname, val);
  }

  public void updateByFieldNum(int num, String val) {
    String fieldname = fieldsName[num];
    if (!this.data.containsKey(fieldname))
      return;
    this.data.put(fieldname, val);
  }

  public void print() {
    System.out.println("Service Data Begin");
    Set s = this.data.entrySet();
    Iterator it = s.iterator();
    while (it.hasNext()) {
      Map.Entry m = (Map.Entry)it.next();
      String key = (String)m.getKey();
      String val = (String)m.getValue();
      System.out.println(key + ": " + val);
    }
    System.out.println("Service Data End");
  }

  public String getInfoByFieldName(String fieldname) {
    return (String)this.data.get(fieldname);
  }

  public String getInfoByFieldNumber(int num) {
    return (String)this.data.get(fieldsName[num]);
  }
}