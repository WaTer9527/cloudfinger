package wang.cloudfinger.printing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {

	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private List<View> views = new ArrayList<View>();
	
	private LinearLayout tabHome;
	private LinearLayout tabMessage;
	private LinearLayout tabKnowledge;
	private LinearLayout tabMine;
	private LinearLayout tabSettings;
	
	private ImageButton tabImgHome;
	private ImageButton tabImgMessage;
	private ImageButton tabImgKnowledge;
	private ImageButton tabImgMine;
	private ImageButton tabImgSettings;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		
		initEvents();
	}


	private void initEvents() {
		tabHome.setOnClickListener(this);
		tabMessage.setOnClickListener(this);
		tabKnowledge.setOnClickListener(this);
		tabMine.setOnClickListener(this);
		tabSettings.setOnClickListener(this);
		
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				int currentItem = viewPager.getCurrentItem();
				resetImg();
				switch (currentItem) {
				case 0:
					tabImgHome.setImageResource(R.drawable.icon_home_press);
					break;
				case 1:
					tabImgMessage.setImageResource(R.drawable.icon_message_press);
					break;
				case 2:
					tabImgKnowledge.setImageResource(R.drawable.icon_books_press);
					break;
				case 3:
					tabImgMine.setImageResource(R.drawable.icon_user_press);
					break;
				case 4:
					tabImgSettings.setImageResource(R.drawable.icon_gear_press);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}


	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.id_viewPager);
		
		tabHome = (LinearLayout) findViewById(R.id.id_tab_home);
		tabMessage = (LinearLayout) findViewById(R.id.id_tab_message);
		tabKnowledge = (LinearLayout) findViewById(R.id.id_tab_knowledge);
		tabMine = (LinearLayout) findViewById(R.id.id_tab_mine);
		tabSettings = (LinearLayout) findViewById(R.id.id_tab_settings);
		
		tabImgHome = (ImageButton) findViewById(R.id.id_tab_home_img);
		tabImgMessage = (ImageButton) findViewById(R.id.id_tab_message_img);
		tabImgKnowledge = (ImageButton) findViewById(R.id.id_tab_knowledge_img);
		tabImgMine = (ImageButton) findViewById(R.id.id_tab_mine_img);
		tabImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);
		
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View viewHome = layoutInflater.inflate(R.layout.tab_home, null);
		View viewMessage = layoutInflater.inflate(R.layout.tab_message, null);
		View viewKnowledge = layoutInflater.inflate(R.layout.tab_knowledge, null);
		View viewMine = layoutInflater.inflate(R.layout.tab_mine, null);
		View viewSettings = layoutInflater.inflate(R.layout.tab_settings, null);
		
		views.add(viewHome);
		views.add(viewMessage);
		views.add(viewKnowledge);
		views.add(viewMine);
		views.add(viewSettings);
		
		pagerAdapter = new PagerAdapter(
				) {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				View view = views.get(position);
				container.removeView(view);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = views.get(position);
				container.addView(view);
				return view;
			}
			
		};
		
		viewPager.setAdapter(pagerAdapter);
	}


	@Override
	public void onClick(View v) {
		resetImg();
		switch (v.getId()) {
		case R.id.id_tab_home:
			viewPager.setCurrentItem(0);
			tabImgHome.setImageResource(R.drawable.icon_home_press);
			break;
		case R.id.id_tab_message:
			viewPager.setCurrentItem(1);
			tabImgMessage.setImageResource(R.drawable.icon_message_press);
			break;
		case R.id.id_tab_knowledge:
			viewPager.setCurrentItem(2);		
			tabImgKnowledge.setImageResource(R.drawable.icon_books_press);
			break;
		case R.id.id_tab_mine:
			viewPager.setCurrentItem(3);
			tabImgMine.setImageResource(R.drawable.icon_user_press);
			break;
		case R.id.id_tab_settings:
			viewPager.setCurrentItem(4);
			tabImgSettings.setImageResource(R.drawable.icon_gear_press);
			break;

		default:
			break;
		}
	}

	
	/**
	 * 将所有的图片切换为白色
	 */
	private void resetImg() {
		tabImgHome.setImageResource(R.drawable.icon_home);
		tabImgMessage.setImageResource(R.drawable.icon_message);
		tabImgKnowledge.setImageResource(R.drawable.icon_books);
		tabImgMine.setImageResource(R.drawable.icon_user);
		tabImgSettings.setImageResource(R.drawable.icon_gear);
	}

}
