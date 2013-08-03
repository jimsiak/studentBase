package org.studentbase.database;

import java.awt.Component;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JTextField;

public class SpareData
{
  private Map<String, String> data;
  public static final String[] fieldsLabels = { 
    "Κωδικός", "Είδος", "Τιμή", "Ποσότητα" };

  public static final String[] fieldsTypes = { 
    "VARCHAR(30)", "VARCHAR(50)", "VARCHAR(20)", "VARCHAR(10)" };

  public static final String[] fieldsName = { 
    "code", "type", "price", "count" };

  public static final Component[] fieldsInputType = { 
    new JTextField(10), new JTextField(10), 
    new JTextField(10), new JTextField(10) };

  public SpareData()
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
    System.out.println("Spare Data Begin");
    Set s = this.data.entrySet();
    Iterator it = s.iterator();
    while (it.hasNext()) {
      Map.Entry m = (Map.Entry)it.next();
      String key = (String)m.getKey();
      String val = (String)m.getValue();
      System.out.println(key + ": " + val);
    }
    System.out.println("Spare Data End");
  }

  public String getInfoByFieldName(String fieldname) {
    return (String)this.data.get(fieldname);
  }

  public String getInfoByFieldNumber(int num) {
    return (String)this.data.get(fieldsName[num]);
  }
}