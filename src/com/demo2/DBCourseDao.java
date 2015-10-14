package com.demo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBCourseDao {

	DBCourseOpenHelper dbCourseOpenHelper=null;
	
	public DBCourseDao(Context context){
		this.dbCourseOpenHelper=new DBCourseOpenHelper(context);
	}
	
	/*
	 * 插入一条数据
	 * */
	public void save(Course course){
		SQLiteDatabase db=dbCourseOpenHelper.getReadableDatabase();
		db.execSQL("insert into course (weekDay, courseId, courseName, location, teacherName) "+ "values(?,?,?,?,?)", 
				new Object[]{course.weekDay,course.courseId,course.courseName ,course.location,course.teacherName});
		db.close();
	}
	
	/*
	 * 删除数据
	 * */
	public void delete(Course course){
		SQLiteDatabase db=dbCourseOpenHelper.getReadableDatabase();
		db.execSQL("delete from course where weekDay=?,courseId=?",new Object[]{course.weekDay,course.courseId});
		db.close();
	}
	
}
