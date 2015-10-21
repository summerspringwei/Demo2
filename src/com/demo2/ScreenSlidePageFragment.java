package com.demo2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment{

	/**
	 * The argument key for the page number this fragment represents.
	 */
	public static final String ARG_PAGE="page";
	/**
	 * 这个框架的页面的数量，
	 */
	private int mPageNumber;
	
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
		((TextView)(rootView.findViewById(android.R.id.text1))).setText(getString(R.string.title_template_step,mPageNumber+1));
		return rootView;
	}
	
	public int getPageNumber(){
		return this.mPageNumber;
	}
}
