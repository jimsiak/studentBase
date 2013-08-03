package org.studentbase.database;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.studentbase.file.DatabaseReadFile;
import org.studentbase.file.DatabaseWriteFile;
import org.studentbase.gui.DbConnectionErrorDialog;

public class DatabaseManager
{
  private final String dbClass = "com.mysql.jdbc.Driver";
  private String dbUrl;
  private String dbAddress = "83.212.110.199";
  private String username = "mpampis";
  private String password = "mpampis";
  private Connection con;

  public String getDbAddress()
  {
    return this.dbAddress;
  }

  public void setDbAddress(String dbAddress) {
    this.dbAddress = dbAddress;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void createDatabase(String dbname) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://" + this.dbAddress, this.username, this.password);
      Statement stmt = con.createStatement();
      stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbname + ";");
      this.dbUrl = ("jdbc:mysql://" + this.dbAddress + "/" + dbname + "?useUnicode=true&characterEncoding=utf-8");
      con.close();
    }
    catch (SQLException e) {
      new DbConnectionErrorDialog();
      createDatabase(dbname);
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      this.con = DriverManager.getConnection(this.dbUrl, this.username, this.password);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {
    try {
      this.con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void exportDatabase(String filename) {
    DatabaseWriteFile file = new DatabaseWriteFile();
    file.open(filename);
    file.writeCustomers(getCustomersList());
    //file.writeMachines(getMachinesList());
    file.writeServices(getServicesList());
    file.writeSpares(getSparesList());
    file.close();
  }

  public void importDatabase(String filename) {
    DatabaseReadFile file = new DatabaseReadFile();
    file.open(filename);

    List students = file.readCustomersList();
    Iterator it = students.iterator();
    while (it.hasNext()) {
      Student cust = (Student)it.next();
      String query = "INSERT INTO students SET id=" + cust.getId() + ", ";
      for (int i = 0; i < StudentData.fieldsName.length; i++) {
        String fieldname = StudentData.fieldsName[i];
        String val = cust.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != StudentData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    List machines = file.readMachinesList();
    Iterator it2 = machines.iterator();
    while (it2.hasNext()) {
      Course mach = (Course)it2.next();
      String query = "INSERT INTO machines SET id=" + mach.getId() + ", ";
      for (int i = 0; i < CourseData.fieldsName.length; i++) {
        String fieldname = CourseData.fieldsName[i];
        String val = mach.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != CourseData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    List services = file.readServicesList();
    Iterator it3 = services.iterator();
    while (it3.hasNext()) {
      Payment serv = (Payment)it3.next();
      String query = "INSERT INTO services SET id=" + serv.getId() + 
        ", sid=" + serv.getSid() + ", cid=" + serv.getCid() + ", ";
      for (int i = 0; i < PaymentData.fieldsName.length; i++) {
        String fieldname = PaymentData.fieldsName[i];
        String val = serv.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != PaymentData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    List spares = file.readSparesList();
    Iterator it4 = spares.iterator();
    while (it4.hasNext()) {
      Spare spare = (Spare)it4.next();
      String query = "INSERT INTO spares SET id=" + spare.getId() + 
        ", mid=" + spare.getMid() + ", sid=" + spare.getSid() + ", ";
      for (int i = 0; i < SpareData.fieldsName.length; i++) {
        String fieldname = SpareData.fieldsName[i];
        String val = spare.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != SpareData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";
      executeUpdate(query);
    }

    file.close();
  }

  public void checkCustomersTableColumns()
  {
    try {
      Statement stmt = this.con.createStatement();
      String query = "SELECT * FROM students";
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData md = rs.getMetaData();
      for (int i = 1; i <= md.getColumnCount(); i++)
        System.out.println("Customers table column " + i + " name " + md.getColumnName(i));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void executeUpdate(String query) {
    try {
      Statement stmt = this.con.createStatement();
      System.out.println(query);
      stmt.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ResultSet executeQuery(String query) {
    try {
      Statement stmt = this.con.createStatement();
      System.out.println(query);
      return stmt.executeQuery(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void createStudentsTable(String tbname)
  {
    String[] custFieldName = StudentData.fieldsName;
    String[] custFieldType = StudentData.fieldsTypes;
    int custLen = custFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY";
    for (int i = 0; i < custLen; i++)
      query = query + ", " + custFieldName[i] + " " + custFieldType[i];
    query = query + ") DEFAULT CHARACTER SET utf8;";

    executeUpdate(query);
  }

  public List<Student> getCustomersList() {
    try {
      ResultSet rs = executeQuery("SELECT * FROM students");

      List list = new ArrayList();
      while (rs.next()) {
        StudentData custData = new StudentData();
        String id = rs.getString(1);
        for (int i = 0; i < StudentData.fieldsName.length; i++) {
          String fieldValue = rs.getString(StudentData.fieldsName[i]);
          custData.updateByFieldName(StudentData.fieldsName[i], fieldValue);
        }
        Student cust = new Student(custData);
        cust.setId(Integer.parseInt(id));
        list.add(cust);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public Student[] getStudentsArray()
  {
    List list = getCustomersList();
    Student[] students = (Student[])list.toArray(new Student[list.size()]);
    return students;
  }

  public String[] getCustomersFieldsByName(String fieldname)
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection(this.dbUrl, "root", "01071989");
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM students");

      List list = new ArrayList();
      while (rs.next()) {
        String fieldValue = rs.getString(fieldname);
        list.add(fieldValue);
      }

      con.close();

      return (String[])list.toArray(new String[list.size()]);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private int getNextId(String tbname) {
    try {
      String query = "SELECT max(id) FROM " + tbname + ";";
      ResultSet rs = executeQuery(query);
      if (!rs.next())
        return 1;
      return rs.getInt(1) + 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }return -1;
  }

  public void addCustomerToDatabase(Student cust)
  {
    int id = getNextId("students");
    String query = "INSERT INTO students SET id=" + id + ", ";
    for (int i = 0; i < StudentData.fieldsName.length; i++) {
      String fieldname = StudentData.fieldsName[i];
      String val = cust.getInfoByFieldName(fieldname);
      query = query + fieldname + "= \"" + val + "\"";
      if (i != StudentData.fieldsName.length - 1)
        query = query + ", ";
    }
    query = query + ";";

    executeUpdate(query);
    cust.setId(id);
  }

  public void removeCustomerFromDatabase(Student cust) {
    String query = "DELETE FROM students WHERE id=" + cust.getId();
    executeUpdate(query);
  }

  public void createCoursesTable(String tbname) {
    String[] courseFieldName = CourseData.fieldsName;
    String[] courseFieldType = CourseData.fieldsTypes;
    int courseLen = courseFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY";
    for (int i = 0; i < courseLen; i++)
      query = query + ", " + courseFieldName[i] + " " + courseFieldType[i];
    query = query + ") DEFAULT CHARACTER SET utf8;";

    executeUpdate(query);
  }

  public List<Course> getCoursesList() {
    try {
      ResultSet rs = 
        executeQuery("SELECT * FROM courses;");

      List list = new ArrayList();
      while (rs.next()) {
        CourseData courseData = new CourseData();
        int id = rs.getInt(1);
        for (int i = 0; i < CourseData.fieldsName.length; i++) {
          String fieldValue = rs.getString(CourseData.fieldsName[i]);
          courseData.updateByFieldName(CourseData.fieldsName[i], fieldValue);
        }
        Course course = new Course(courseData);
        course.setId(id);
        //mach.setCid(cust.getId());
        list.add(course);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public void addMachineToDatabase(Course mach, Student cust)
  {
    int id = getNextId("machines");
    String query = "INSERT INTO machines SET id=" + id + ", ";
    query = query + "cid=" + cust.getId() + ", ";
    for (int i = 0; i < CourseData.fieldsName.length; i++) {
      String fieldname = CourseData.fieldsName[i];
      String val = mach.getInfoByFieldName(fieldname);
      query = query + fieldname + "= \"" + val + "\"";
      if (i != CourseData.fieldsName.length - 1)
        query = query + ", ";
    }
    query = query + ";";

    executeUpdate(query);
    mach.setId(id);
    //mach.setCid(cust.getId());
  }

  public void createPaymentsTable(String tbname) {
    String[] paymentsFieldName = PaymentData.fieldsName;
    String[] paymentsFieldType = PaymentData.fieldsTypes;
    int paymentLen = paymentsFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, sid INT, cid INT";
    for (int i = 0; i < paymentLen; i++)
      query = query + ", " + paymentsFieldName[i] + " " + paymentsFieldType[i];
    query = query + ", FOREIGN KEY (sid) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ", FOREIGN KEY (cid) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE) DEFAULT CHARACTER SET utf8;";
    
    executeUpdate(query);
  }

  public List<Payment> getStudentPaymentsList(Student stud) {
    try {
      ResultSet rs = 
        executeQuery("SELECT payments.* FROM students JOIN courses JOIN payments ON students.id=sid AND courses.id=cid AND sid=" + 
        stud.getId() + ";");

      List list = new ArrayList();
      while (rs.next()) {
        PaymentData paymentData = new PaymentData();
        int id = rs.getInt(1);
        int sid = rs.getInt(2);
        int cid = rs.getInt(3);
        for (int i = 0; i < PaymentData.fieldsName.length; i++) {
          String fieldValue = rs.getString(PaymentData.fieldsName[i]);
          paymentData.updateByFieldName(PaymentData.fieldsName[i], fieldValue);
        }
        Payment payment = new Payment(paymentData);
        payment.setId(id);
        payment.setSid(sid);
        payment.setCid(cid);
        list.add(payment);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public List<Payment> getMachineServicesList(Course mach)
  {
    try {
      ResultSet rs = 
        executeQuery("SELECT services.* FROM machines JOIN services ON machines.id=mid AND mid=" + 
        mach.getId() + ";");

      List list = new ArrayList();
      while (rs.next()) {
        PaymentData servData = new PaymentData();
        int id = rs.getInt(1);
        int mid = rs.getInt(2);
        for (int i = 0; i < PaymentData.fieldsName.length; i++) {
          String fieldValue = rs.getString(PaymentData.fieldsName[i]);
          servData.updateByFieldName(PaymentData.fieldsName[i], fieldValue);
        }
        Payment serv = new Payment(servData);
        serv.setId(id);
        //serv.setMid(mid);
        list.add(serv);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public List<Payment> getServicesList()
  {
    try {
      ResultSet rs = 
        executeQuery("SELECT * FROM services;");

      List list = new ArrayList();
      while (rs.next()) {
        PaymentData servData = new PaymentData();
        int id = rs.getInt(1);
        int mid = rs.getInt(2);
        for (int i = 0; i < PaymentData.fieldsName.length; i++) {
          String fieldValue = rs.getString(PaymentData.fieldsName[i]);
          servData.updateByFieldName(PaymentData.fieldsName[i], fieldValue);
        }
        Payment serv = new Payment(servData);
        serv.setId(id);
        //serv.setMid(mid);
        list.add(serv);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public void addServiceToDatabase(Payment serv, Course mach)
  {
    int id = getNextId("services");
    String query = "INSERT INTO services SET id=" + id + ", ";
    query = query + "mid=" + mach.getId() + ", ";
    for (int i = 0; i < PaymentData.fieldsName.length; i++) {
      String fieldname = PaymentData.fieldsName[i];
      String val = serv.getInfoByFieldName(fieldname);
      query = query + fieldname + "= \"" + val + "\"";
      if (i != PaymentData.fieldsName.length - 1)
        query = query + ", ";
    }
    query = query + ";";

    executeUpdate(query);
    serv.setId(id);
    //serv.setMid(mach.getId());
  }

  public void createSpareTable(String tbname) {
    String[] spareFieldName = SpareData.fieldsName;
    String[] spareFieldType = SpareData.fieldsTypes;
    int spareLen = spareFieldName.length;
    String query = "CREATE TABLE IF NOT EXISTS " + tbname + "(";
    query = query + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, mid INT, sid INT";
    for (int i = 0; i < spareLen; i++)
      query = query + ", " + spareFieldName[i] + " " + spareFieldType[i];
    query = query + ", FOREIGN KEY (mid) REFERENCES machines(id) , FOREIGN KEY (sid) REFERENCES services(id) ON DELETE CASCADE ON UPDATE CASCADE) DEFAULT CHARACTER SET utf8;";

    executeUpdate(query);
  }

  public List<Spare> getSparesList() {
    try {
      ResultSet rs = 
        executeQuery("SELECT * FROM spares;");

      List list = new ArrayList();
      while (rs.next()) {
        SpareData spareData = new SpareData();
        int id = rs.getInt(1);
        int mid = rs.getInt(2);
        int sid = rs.getInt(3);
        for (int i = 0; i < SpareData.fieldsName.length; i++) {
          String fieldValue = rs.getString(SpareData.fieldsName[i]);
          spareData.updateByFieldName(SpareData.fieldsName[i], fieldValue);
        }
        Spare spare = new Spare(spareData);
        spare.setId(id);
        spare.setMid(mid);
        spare.setSid(sid);
        list.add(spare);
      }

      return list;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }return null;
  }

  public void addSpareListToDatabase(List<Spare> spares, Payment serv)
  {
    Iterator it = spares.iterator();
    while (it.hasNext()) {
      Spare spare = (Spare)it.next();

      int id = getNextId("spares");
      String query = "INSERT INTO spares SET id=" + id + ", ";
      //query = query + "sid=" + serv.getId() + ", mid=" + serv.getMid() + ", ";
      for (int i = 0; i < SpareData.fieldsName.length; i++) {
        String fieldname = SpareData.fieldsName[i];
        String val = spare.getInfoByFieldName(fieldname);
        query = query + fieldname + "= \"" + val + "\"";
        if (i != SpareData.fieldsName.length - 1)
          query = query + ", ";
      }
      query = query + ";";

      executeUpdate(query);
      spare.setId(id);
      spare.setSid(serv.getId());
      //spare.setMid(serv.getMid());
    }
  }
}