package org.customerbase.database;


public class Course implements Comparable
{
	private int id;
	private CourseData data;

	public Course()
	{
		this.data = new CourseData();
	}

	public Course(CourseData data)
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

	public String toString()
	{
		return this.data.getInfoByFieldName("yogatype");
	}

	/* FIXME: compare using which field? */
	public int compareTo(Object obj)
	{
		if (!(obj instanceof Course)) {
			System.err.println("Machine can only be compared with Machine");
			System.exit(1);
		}
		Course otherCourse = (Course)obj;
		String myName = this.data.getInfoByFieldName("model");
		String otherName = otherCourse.data.getInfoByFieldName("model");
		return myName.compareToIgnoreCase(otherName);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}