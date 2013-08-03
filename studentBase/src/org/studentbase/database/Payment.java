package org.studentbase.database;

public class Payment
{
  private int id;
  private int sid;
  private int cid;
  private PaymentData data;

  public Payment()
  {
    this.data = new PaymentData();
  }

  public Payment(PaymentData data)
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

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public int getSid() {
    return this.sid;
  }

  public void setSid(int sid) {
    this.sid = sid;
  }

public int getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}
}