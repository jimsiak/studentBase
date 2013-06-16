package org.customerbase.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.customerbase.database.Student;
import org.customerbase.database.StudentData;
import org.customerbase.database.Course;
import org.customerbase.database.CourseData;
import org.customerbase.database.Payment;
import org.customerbase.database.PaymentData;
import org.customerbase.database.Spare;
import org.customerbase.database.SpareData;

public class DatabaseWriteFile
{
  public static String studentsBegin = "------------CUSTOMERS_BEGIN-------------";
  public static String studentsEnd = "------------CUSTOMERS_END-------------";
  public static String machinesBegin = "------------MACHINES_BEGIN-------------";
  public static String machinesEnd = "------------MACHINES_END-------------";
  public static String servicesBegin = "------------SERVICES_BEGIN-------------";
  public static String servicesEnd = "------------SERVICES_END-------------";
  public static String sparesBegin = "------------SPARES_BEGIN-------------";
  public static String sparesEnd = "------------SPARES_END-------------";
  public static String separator = ", ";

  private FileWriter fstream = null;
  private BufferedWriter out = null;

  public void open(String filename) {
    try {
      this.fstream = new FileWriter(filename);
      this.out = new BufferedWriter(this.fstream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      this.out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeCustomers(List<Student> students) {
    try {
      this.out.write(studentsBegin + "\n");
      writeCustomersHeader();
      writeCustomersList(students);
      this.out.write(studentsEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeCustomersHeader() {
    try {
      String[] headers = StudentData.fieldsName;
      this.out.write("id");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeCustomersList(List<Student> students) {
    try {
      Iterator it = students.iterator();

      while (it.hasNext()) {
        Student cust = (Student)it.next();
        this.out.write(Integer.toString(cust.getId()));
        for (int i = 0; i < StudentData.fieldsLabels.length - 1; i++) {
          String val = cust.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeMachines(List<Course> machines) {
    try {
      this.out.write(machinesBegin + "\n");
      writeMachinesHeader();
      writeMachinesList(machines);
      this.out.write(machinesEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeMachinesHeader() {
    try {
      String[] headers = CourseData.fieldsName;
      this.out.write("id" + separator + "cid");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeMachinesList(List<Course> machines) {
    try {
      Iterator it = machines.iterator();

      while (it.hasNext()) {
        Course mach = (Course)it.next();
        this.out.write(Integer.toString(mach.getId()) + separator);
        //this.out.write(Integer.toString(mach.getCid()));
        for (int i = 0; i < CourseData.fieldsLabels.length - 1; i++) {
          String val = mach.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeServices(List<Payment> services) {
    try {
      this.out.write(servicesBegin + "\n");
      writeServicesHeader();
      writeServicesList(services);
      this.out.write(servicesEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeServicesHeader() {
    try {
      String[] headers = PaymentData.fieldsName;
      this.out.write("id" + separator + "mid");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeServicesList(List<Payment> services) {
    try {
      Iterator it = services.iterator();

      while (it.hasNext()) {
        Payment serv = (Payment)it.next();
        this.out.write(Integer.toString(serv.getId()) + separator);
        //this.out.write(Integer.toString(serv.getMid()));
        for (int i = 0; i < PaymentData.fieldsLabels.length - 1; i++) {
          String val = serv.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeSpares(List<Spare> spares) {
    try {
      this.out.write(sparesBegin + "\n");
      writeSparesHeader();
      writeSparesList(spares);
      this.out.write(sparesEnd + "\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeSparesHeader() {
    try {
      String[] headers = SpareData.fieldsName;
      this.out.write("id" + separator + "mid" + separator + "sid");
      for (int i = 0; i < headers.length; i++)
        this.out.write(separator + headers[i]);
      this.out.write("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeSparesList(List<Spare> spares) {
    try {
      Iterator it = spares.iterator();

      while (it.hasNext()) {
        Spare spare = (Spare)it.next();
        this.out.write(Integer.toString(spare.getId()) + separator);
        this.out.write(Integer.toString(spare.getMid()) + separator);
        this.out.write(Integer.toString(spare.getSid()));
        for (int i = 0; i < SpareData.fieldsLabels.length - 1; i++) {
          String val = spare.getInfoByFieldNumber(i);
          this.out.write(separator + val);
        }
        this.out.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}