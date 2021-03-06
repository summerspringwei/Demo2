package com.demo2;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import sqlDao.Course;
import sqlDao.DBCourseDao;

public class SetCourseActivity extends Activity {

	private List<String> list = new ArrayList<String>();
	private List<String> courseList=new ArrayList<String>();
	private Spinner myWeekDaySpinner=null;
	private Spinner myCourseSpinner=null;
	private SpinnerAdapter myWeekDayAdapter;
	private SpinnerAdapter myCourseDayAdapter;
	private Button btn_save=null;
	private Button btn_delete=null;
	private EditText text_location=null;
	private EditText text_courseName=null;
	private EditText text_teacherName=null;
	//用于接收ScreenSlideActivity传递过来的数据
	private String tempWeekDay=null;
	private String tempCourseId=null;
	private String tempCourseName=null;
	private String tempTeacherName=null;
	private String tempLoacation=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_course);
		//接收数据
		acceptData();
		//初始化下拉菜单
		setSpinner();
		//绑定控件
		bindView();
	}
	private void acceptData(){
		tempWeekDay=getIntent().getStringExtra(ScreenSlidePageFragment.WEEKDAY);
		tempCourseId=getIntent().getStringExtra(ScreenSlidePageFragment.COURSEID);
		tempTeacherName=getIntent().getStringExtra(ScreenSlidePageFragment.TEACHERNAME);
		tempCourseName=getIntent().getStringExtra(ScreenSlidePageFragment.COURSENAME);
		tempLoacation=getIntent().getStringExtra(ScreenSlidePageFragment.LOCATION);
	}
	
	/**
	 * 初始化日期和节数选择器
	 */
	@SuppressWarnings("unchecked")
	public void setSpinner(){
		list.add(getResources().getString(R.string.Monday));
		list.add(getResources().getString(R.string.Tuesday));
		list.add(getResources().getString(R.string.Wednesday));
		list.add(getResources().getString(R.string.Thursday));
		list.add(getResources().getString(R.string.Friday));
		list.add(getResources().getString(R.string.Saturday));
		list.add(getResources().getString(R.string.Sunday));
		//weekDay spinner
		myWeekDaySpinner=(Spinner)findViewById(R.id.id_weekDay_select);
		myWeekDayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		((ArrayAdapter<String>) myWeekDayAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myWeekDaySpinner.setAdapter(myWeekDayAdapter);
		//如果是点击ListView item传过来值，则设置spinner的默认值
		if(tempWeekDay!=null){
			myWeekDaySpinner.setSelection(Integer.parseInt(tempWeekDay), true);
		}
		//courseId spinner
		String courseId[]={"1","2","3","4","5","6","7"};
		for(String sc:courseId){
			courseList.add(sc);
		}
		myCourseSpinner=(Spinner)findViewById(R.id.id_course_select);
		myCourseDayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,courseList);
		((ArrayAdapter<String>) myCourseDayAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myCourseSpinner.setAdapter(myCourseDayAdapter);
		if(tempCourseId!=null){
			myCourseSpinner.setSelection(Integer.parseInt(tempCourseId)-1, true);
		}
	}
	
	/*
	 * 绑定按钮和文本框等控件
	 * */
	public void bindView(){
		btn_save=(Button)findViewById(R.id.id_save_btn);
		btn_delete=(Button)findViewById(R.id.id_delete_btn);
		text_location=(EditText)findViewById(R.id.id_location_editText);
		text_teacherName=(EditText)findViewById(R.id.id_teacherName_editText);
		text_courseName=(EditText)findViewById(R.id.id_courseName_editText);
		
		if(tempLoacation!=null){text_location.setText(tempLoacation.trim());}
		if(tempTeacherName!=null){text_teacherName.setText(tempTeacherName.trim());}
		if(tempCourseName!=null){text_courseName.setText(tempCourseName.trim());}
		
		btn_save.setOnClickListener(new saveCourseListener());
		btn_delete.setOnClickListener(new deleteCourseListener());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_course, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//保存course（如果之前存在，则覆盖）
	class saveCourseListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			DBCourseDao dbCouseDao=new DBCourseDao(getApplicationContext());
			dbCouseDao.save(bindData2Course());
			//抛出toast提醒
			Toast.makeText(getApplicationContext(), getString(R.string.toast_save), Toast.LENGTH_SHORT).show();
			myNotify("课程表提示","保存课程成功","请按时上课哦~点击继续添加课程");
		}
		
	}
	
	private class deleteCourseListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			DBCourseDao dbCouseDao=new DBCourseDao(getApplicationContext());
			dbCouseDao.delete(bindData2Course());
			Toast.makeText(getApplicationContext(), getString(R.string.toast_delete), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	//获取控件上的数据，绑定到Course对象并返回
	private Course bindData2Course(){
		//.trim()移除文本框前后的空格 ps：因为Course.toString()方法使用空格来分别每个字段，所以要把用户输入的空格去掉
		String weekDay=myWeekDaySpinner.getSelectedItem().toString().trim();
		String courseId=myCourseSpinner.getSelectedItem().toString().trim();
		String location=text_location.getText().toString().trim();
		String courseName=text_courseName.getText().toString().trim();
		String teacherName=text_teacherName.getText().toString().trim();
		String []hanzi2num={"一","二","三","四","五","六","日"};
		int wd=1;
		for(int i=0;i<hanzi2num.length;i++){
			if(weekDay.contains(hanzi2num[i]))
				wd=i+1;
		}
		Course course=new Course(wd, Integer.parseInt(courseId), courseName, teacherName, location);
		return course;
	}
	private void myNotify(CharSequence tickerText, CharSequence contentTitle,CharSequence contentText){
		String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        //定义通知栏展现的内容信息
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
         
        //定义下拉通知栏时要展现的内容信息
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(this, SetCourseActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,
                contentIntent);
         
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(1, notification);
	}
}
