package com.demo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCourseOpenHelper extends SQLiteOpenHelper {

	public DBCourseOpenHelper(Context context) {
		super(context, "Course", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table course(weekDay int,courseId int,courseName varchar(64)"
				+ ",location varchar(64),teacherName varchar(64), primary key(weekDay,CourseId))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
