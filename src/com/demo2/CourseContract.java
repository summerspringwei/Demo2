package com.demo2;

import android.provider.BaseColumns;

public final class CourseContract {

	public CourseContract(){};
	
	public static abstract class CourseEntry implements BaseColumns{
		public static final String TABLE_NAME="course";
		public static final String COLUMN_NAME_WEEKDAY="weekDay";
		public static final String COLUMN_NAME_LOCATION="location";
		public static final String COLUMN_NAME_TEACHER="teacherName";
		public static final String COLUMN_NAME_COURSE="courseName";
		public static final String COLUMN_NAME_COURSEID="courseId";
	}
}
