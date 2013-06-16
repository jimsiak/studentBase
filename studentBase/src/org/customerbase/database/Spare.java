package org.customerbase.database;

public class Spare
{
  private int id;
  private int sid;
  private int mid;
  private SpareData data;

  public Spare()
  {
    this.data = new SpareData();
  }

  public Spare(SpareData data)
  {
    this.data = data;
  }

  public void print() {
    this.data.print();
  }

  public String getInfoByFieldName(String fieldname) {
    return this.data.getInfoByFieldName(fieldname);
  }

  public String getInfoByFieldNumber(int num) {
    return this.data.getInfoByFieldNumber(num);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSid() {
    return this.sid;
  }

  public void setSid(int sid) {
    this.sid = sid;
  }

  public int getMid() {
    return this.mid;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }
}