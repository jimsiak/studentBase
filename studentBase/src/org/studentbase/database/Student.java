package org.studentbase.database;

public class Student
implements Comparable
{
	private int id;
	private StudentData data;

	public Student()
	{
		this.data = new StudentData();
	}

	public Student(StudentData data)
	{
		this.data = data;
	}

	public boolean isMember()
	{
		String str_val = this.getInfoByFieldName("ismember");
		if (str_val == null || str_val == "")
			return false;
		if (Integer.parseInt(str_val) <= 0)
			return false;
		else return true;
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

	public int compareTo(Object obj)
	{
		if (!(obj instanceof Student)) {
			System.err.println("Customer can only be compared with Customer");
			System.exit(1);
		}
		Student otherStud = (Student)obj;
		String myName = this.data.getInfoByFieldName("lastname");
		String otherName = otherStud.data.getInfoByFieldName("lastname");
		return myName.compareToIgnoreCase(otherName);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String toString()
	{
		return this.data.getInfoByFieldName("lastname") + " " + 
				this.data.getInfoByFieldName("firstname");
	}
}