package com.demo2;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SetCourseActivity extends Activity {

	private List<String> list = new ArrayList<String>();
	private List<String> courseList=new ArrayList<String>();
	private Spinner myWeekDaySpinner=null;
	private Spinner myCourseSpinner=null;
	private SpinnerAdapter myWeekDayAdapter;
	private SpinnerAdapter myCourseDayAdapter;
	private Button btn_save=null;
	private EditText text_location=null;
	private EditText text_courseName=null;
	private EditText text_teacherName=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_course);
		
		
		//初始化下拉菜单
		setSpinner();
		//绑定控件
		bindView();
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
		myWeekDaySpinner=(Spinner)findViewById(R.id.id_weekDay_select);
		myWeekDayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		((ArrayAdapter<String>) myWeekDayAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myWeekDaySpinner.setAdapter(myWeekDayAdapter);
		
		String courseId[]={"1","2","3","4","5","6","7"};
		for(String sc:courseId){
			courseList.add(sc);
		}
		myCourseSpinner=(Spinner)findViewById(R.id.id_course_select);
		myCourseDayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,courseList);
		((ArrayAdapter<String>) myCourseDayAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myCourseSpinner.setAdapter(myCourseDayAdapter);
	}
	
	/*
	 * 绑定按钮和文本框等控件
	 * */
	public void bindView(){
		btn_save=(Button)findViewById(R.id.id_save_btn);
		text_location=(EditText)findViewById(R.id.id_location_editText);
		text_teacherName=(EditText)findViewById(R.id.id_teacherName_editText);
		text_courseName=(EditText)findViewById(R.id.id_courseName_editText);
		
		btn_save.setOnClickListener(new saveCourseListener());
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
	
	class saveCourseListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String weekDay=myWeekDaySpinner.getSelectedItem().toString();
			String courseId=myCourseSpinner.getSelectedItem().toString();
			String location=text_location.getText().toString();
			String courseName=text_courseName.getText().toString();
			String teacherName=text_teacherName.getText().toString();
			
			String []hanzi2num={"一","二","三","四","五","六","日"};
			int wd=1;
			for(int i=0;i<hanzi2num.length;i++){
				if(weekDay.contains(hanzi2num[i]))
					wd=i+1;
			}
			
			
			Course course=new Course(wd, Integer.parseInt(courseId), courseName, teacherName, location);
			
			
			Toast.makeText(getApplicationContext(), getString(R.string.toast_save), Toast.LENGTH_SHORT).show();
			
		}
		
	}
}
