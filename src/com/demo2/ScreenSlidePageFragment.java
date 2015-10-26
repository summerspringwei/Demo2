package com.demo2;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	ListView list_course=null;
	String []str_course=null;
	
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
		Collections.sort(courseList, new Comparator<Course>() {

			@Override
			public int compare(Course lhs, Course rhs) {
				// TODO Auto-generated method stub
				return (lhs.getOrder()<rhs.getOrder()?1:0);
			}
		});
		
		//获取ListView
		list_course=(ListView)rootView.findViewById(R.id.listview_course);
		//声明Adapter
		ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1);
		TextView text_weekDay=(TextView)rootView.findViewById(R.id.weekday);
		
		String []hanzi2num={"一","二","三","四","五","六","日"};
		text_weekDay.setText(getString(R.string.week)+hanzi2num[mPageNumber]);
		Log.v("PageNumber", ""+mPageNumber);
		for(int j=0;j<courseList.size();j++){
			Course c=courseList.get(j);
			Log.v("course", c.toString());
			if(c.weekDay==mPageNumber+1){
				//Adapter增加数据
				myAdapter.add(c.toString().substring(1));
			}
		}
		//设置Adapter
		list_course.setAdapter(myAdapter);
		//清空Adapter 。。不需要清空~每个fragment的数据是单独的
		//myAdapter.clear();
		
		return rootView;
	}
	
	public int getPageNumber(){
		return this.mPageNumber;
	}
}
