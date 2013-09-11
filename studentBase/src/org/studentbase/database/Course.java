package org.studentbase.database;


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

	public int compareTo(Object obj)
	{
		if (!(obj instanceof Course)) {
			System.err.println("Course can only be compared with Course");
			System.exit(1);
		}
		Course otherCourse = (Course)obj;
		String myName = this.data.getInfoByFieldName("yogatype");
		if (myName.contains("Εγγραφή"))
			return -1;
		String otherName = otherCourse.data.getInfoByFieldName("yogatype");
		if (otherName.contains("Εγγραφή"))
			return 1;
		return myName.compareToIgnoreCase(otherName);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}