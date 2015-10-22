package com.demo2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBCourseDao {

	DBCourseOpenHelper dbCourseOpenHelper=null;
	
	public DBCourseDao(Context context){
		this.dbCourseOpenHelper=new DBCourseOpenHelper(context);
	}
	
	/*
	 * 插入一条数据,如果数据已经存在，则更新
	 * */
	public void save(Course course){
		SQLiteDatabase db=dbCourseOpenHelper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from course where weekDay="+course.weekDay
				+" and courseId="+course.courseId, null);
		if(c!=null&&!c.moveToNext()){
			db.execSQL("insert into course (weekDay, courseId, courseName, location, teacherName) "
					+ "values(?,?,?,?,?)", new Object[]{course.weekDay,course.courseId,course.courseName,
					course.location,course.teacherName});
		}
		else{
			db.execSQL("update course set courseName=\""+course.courseName+"\", location=\""+course.location
					+"\", teacherName=\""+course.teacherName+ "\" where weekDay="+course.weekDay
					+" and courseId="+course.courseId+"");
		}
		db.close();
		Log.v("DB save", "save success"+course.courseName);
	}
	
	/*
	 * 删除数据
	 * */
	public void delete(Course course){
		SQLiteDatabase db=dbCourseOpenHelper.getReadableDatabase();
		db.execSQL("delete from course where weekDay=?,courseId=?",new Object[]{course.weekDay,course.courseId});
		db.close();
	}
	
	
	/**
	 * 查询所有数据
	 */
	public List<Course> queryAll(){
		SQLiteDatabase db=dbCourseOpenHelper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from course", null);
		ArrayList<Course> courseList=new ArrayList<Course>();
		if(c!=null){
			while(c.moveToNext()){
				Course course=new Course(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4));
				courseList.add(course);
			}
		}
		db.close();
		return courseList;
	}
	/**
	 * 根据weekDay和courseId查询
	 * @param weekDay
	 * @param courseId
	 * @return Course
	 */
	public Course queryOne(int weekDay, int courseId){
		SQLiteDatabase db=dbCourseOpenHelper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from course where weekDay="+weekDay+" and courseId="+courseId, null);
		Course course=null;
		if(c!=null){
			course=new Course(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4));
		}
		db.close();
		return course;
	}
	/**
	 * 查询weekDay一天的课程
	 * @param weekDay
	 * @return List<Course>
	 */
	public List<Course> queryWeekDay(int weekDay){
		SQLiteDatabase db=dbCourseOpenHelper.getReadableDatabase();
		Cursor c=db.rawQuery("select * from course where weekDay="+weekDay, null);
		ArrayList<Course> courseList=new ArrayList<Course>();
		if(c!=null){
			while(c.moveToNext()){
				Course course=new Course(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4));
				courseList.add(course);
			}
		}
		db.close();
		return courseList;
	}
}
