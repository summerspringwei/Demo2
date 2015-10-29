package com.demo2;

import java.util.Calendar;
import java.util.Date;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	protected static final int NUM_PAGES=7;
	/**
	 * pager 控件，用来处理动画和允许水平滑动来获得之前和之后的页面
	 */
	private ViewPager mPager=null;
	
	/**
	 * 为上面的pager 控件提供页面
	 */
	private PagerAdapter mPageAdapter=null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//实例化pager和pager adapter
		mPager=(ViewPager)findViewById(R.id.pager);
		mPageAdapter=new ScreenSlidePagerAdapter(getFragmentManager());
		mPager.setAdapter(mPageAdapter);
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });
		//设置启动的页面为今天
		mPager.setCurrentItem(getWeekday());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem()>0);
		
		//基于当前是哪一页来动态的在action bar加上"next" or "finish"
		MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPageAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId()){
			case R.id.action_previous:
				mPager.setCurrentItem(mPager.getCurrentItem()-1);
				return true;
			case R.id.action_next:
				mPager.setCurrentItem(mPager.getCurrentItem()+1);
				return true;
			case R.id.action_settings:
				Intent intent=new Intent(this,SetCourseActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_about:
				Intent intent1=new Intent(this,AboutActivity.class);
				startActivity(intent1);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
	//设置完课程之后，返回MainActivity更新当前页面的数据（学习android生命周期的好处）
	@Override
	public void onResume(){
		super.onResume();
		mPager.setCurrentItem(getWeekday());
	}
	/**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    public static int getWeekday(){
    	Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==1){
			dayOfWeek=6;
		}
		else{
			dayOfWeek=dayOfWeek-2;
		}
		return dayOfWeek;
    }
}
