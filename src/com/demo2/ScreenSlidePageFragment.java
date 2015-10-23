package com.demo2;

import java.util.List;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import sqlDao.Course;
import sqlDao.DBCourseDao;

public class ScreenSlidePageFragment extends Fragment{

	/**
	 * The argument key for the page number this fragment represents.
	 */
	public static final String ARG_PAGE="page";
	/**
	 * 这个框架的页面的数量，
	 */
	private int mPageNumber=0;
	
	public ScreenSlidePageFragment(){};
	
	/**
	 * 这个框架页面的工厂方法，为给定的页面号构造一个新的框架
	 * 
	 * @param pageNumber
	 * @return fragment
	 */
	public static ScreenSlidePageFragment create(int pageNumber){
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args=new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}
	/**
	 * 初始创造一个fragment的时候被调用
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mPageNumber=getArguments().getInt(ARG_PAGE);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.fragment_screen_slide, container, false);
		DBCourseDao dbCouse=new DBCourseDao(getActivity());
		List<Course> courseList=dbCouse.queryAll();
		String s_course="";
		String []hanzi2num={"一","二","三","四","五","六","日"};
		((TextView)(rootView.findViewById(R.id.weekday))).setText("星期"+hanzi2num[mPageNumber]);
		Log.v("PageNumber", ""+mPageNumber);
		for(int j=0;j<courseList.size();j++){
			Course c=courseList.get(j);
			Log.v("course", c.toString());
			if(c.weekDay==mPageNumber+1){
				s_course+=c.toString()+"\n";
			}
		}
		((TextView)(rootView.findViewById(R.id.courseDetail))).setText(s_course);
		s_course="";
		
		return rootView;
	}
	
	public int getPageNumber(){
		return this.mPageNumber;
	}
}
