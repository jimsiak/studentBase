package org.studentbase.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.studentbase.database.Course;
import org.studentbase.database.CourseData;
import org.studentbase.database.Payment;
import org.studentbase.database.PaymentData;
import org.studentbase.database.Spare;
import org.studentbase.database.SpareData;
import org.studentbase.database.Student;
import org.studentbase.database.StudentData;

public class DatabaseReadFile
{
  private static String studentsBegin = DatabaseWriteFile.studentsBegin;
  private static String studentsEnd = DatabaseWriteFile.studentsEnd;
  private static String machinesBegin = DatabaseWriteFile.machinesBegin;
  private static String machinesEnd = DatabaseWriteFile.machinesEnd;
  private static String servicesBegin = DatabaseWriteFile.servicesBegin;
  private static String servicesEnd = DatabaseWriteFile.servicesEnd;
  private static String sparesBegin = DatabaseWriteFile.sparesBegin;
  private static String sparesEnd = DatabaseWriteFile.sparesEnd;
  private static String separator = DatabaseWriteFile.separator;

  private FileInputStream fstream = null;
  private DataInputStream in = null;
  private BufferedReader br = null;

  public void open(String filename) {
    try {
      this.fstream = new FileInputStream(filename);
      this.in = new DataInputStream(this.fstream);
      this.br = new BufferedReader(new InputStreamReader(this.in));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      this.in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Student> readCustomersList() {
    try {
      List students = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(studentsBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(studentsEnd)) {
        StudentData data = new StudentData();
        String[] vals = line.split(separator);
        for (int i = 1; i < vals.length; i++)
          data.updateByFieldNum(i - 1, vals[i]);
        Student cust = new Student(data);
        cust.setId(Integer.parseInt(vals[0]));
        students.add(cust);
        line = this.br.readLine();
      }

      return students;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public List<Course> readMachinesList()
  {
    try {
      List machines = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(machinesBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(machinesEnd)) {
        CourseData data = new CourseData();
        String[] vals = line.split(separator);
        for (int i = 2; i < vals.length; i++)
          data.updateByFieldNum(i - 2, vals[i]);
        Course mach = new Course(data);
        mach.setId(Integer.parseInt(vals[0]));
        //mach.setCid(Integer.parseInt(vals[1]));
        machines.add(mach);
        line = this.br.readLine();
      }

      return machines;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public List<Payment> readServicesList()
  {
    try {
      List services = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(servicesBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(servicesEnd)) {
        PaymentData data = new PaymentData();
        String[] vals = line.split(separator);
        for (int i = 2; i < vals.length; i++)
          data.updateByFieldNum(i - 2, vals[i]);
        Payment serv = new Payment(data);
        serv.setId(Integer.parseInt(vals[0]));
        //serv.setMid(Integer.parseInt(vals[1]));
        services.add(serv);
        line = this.br.readLine();
      }

      return services;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public List<Spare> readSparesList()
  {
    try {
      List spares = new ArrayList();
      String line = this.br.readLine();
      if (!line.equals(sparesBegin))
        return null;
      line = this.br.readLine();
      line = this.br.readLine();
      while (!line.equals(sparesEnd)) {
        SpareData data = new SpareData();
        String[] vals = line.split(separator);
        for (int i = 3; i < vals.length; i++)
          data.updateByFieldNum(i - 3, vals[i]);
        Spare spare = new Spare(data);
        spare.setId(Integer.parseInt(vals[0]));
        spare.setMid(Integer.parseInt(vals[1]));
        spare.setSid(Integer.parseInt(vals[2]));
        spares.add(spare);
        line = this.br.readLine();
      }

      return spares;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }
}