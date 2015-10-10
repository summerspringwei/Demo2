package com.demo2;

public class Course {

	int weekDay=1;
	int courseId=1;
	String courseName=null;
	String teacherName=null;
	String location=null;
	
	public Course(int w,int c,String cn,String tn,String l){
		this.weekDay=w;
		this.courseId=c;
		this.courseName=cn;
		this.teacherName=tn;
		this.location=l;
	}
}
