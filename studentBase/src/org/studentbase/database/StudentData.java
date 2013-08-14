package org.studentbase.database;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class StudentData
{
  private Map<String, String> data;
  
  public static final String[] fieldsLabels = { 
	"Επώμυμο", "Όνομα", "Ημερομηνία Γέννησης", "E-mail", 
	"Τηλ. Επικοινωνίας", "Κινητό", "Τηλ. Εργασίας", 
	"Επάγγελμα", "Διεύθυνση", "Πληροφορίες", "Μέλος",
	"Ημερομηνία Εγγραφής"
  };

  public static final String[] fieldsTypes = { 
    "VARCHAR(50) NOT NULL", "VARCHAR(15)", "DATE", "VARCHAR(50)", 
    "VARCHAR(15)", "VARCHAR(15)", "VARCHAR(15)", 
    "VARCHAR(30)", "VARCHAR(30)", "VARCHAR(500)", "BOOLEAN",
    "DATE"
  };

  public static final String[] fieldsName = { 
	"lastname", "firstname", "birthdate", "email",
	"phonenumber", "cellphone", "workphone",
	"job", "address", "moreinfo", "ismember",
	"registerdate"
  };

  public static final Component[] fieldsInputType = { 
    new JTextField(), new JTextField(), 
    new JDateChooser(), new JTextField(), 
    new JTextField(), new JTextField(), 
    new JTextField(), new JTextField(), 
    new JTextField(), new JTextArea(),
    new JCheckBox(), new JDateChooser()
  };

  public StudentData()
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
    System.out.println("Customer Data Begin");
    Set s = this.data.entrySet();
    Iterator it = s.iterator();
    while (it.hasNext()) {
      Map.Entry m = (Map.Entry)it.next();
      String key = (String)m.getKey();
      String val = (String)m.getValue();
      System.out.println(key + ": " + val);
    }
    System.out.println("Customer Data End");
  }

  public String getInfoByFieldName(String fieldname) {
    return (String)this.data.get(fieldname);
  }

  public String getInfoByFieldNumber(int num) {
    return (String)this.data.get(fieldsName[num]);
  }
}