package wang.cloudfinger.printing.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import wang.cloudfinger.printing.R;

public class MainActivity extends FragmentActivity implements OnClickListener {

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
	
	private Fragment contentHome;
	private Fragment contentMessage;
	private Fragment contentKnowledge;
	private Fragment contentMine;
	private Fragment contentSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		initEvents();
		setSelect(0);
	}


	private void initEvents() {
		tabHome.setOnClickListener(this);
		tabMessage.setOnClickListener(this);
		tabKnowledge.setOnClickListener(this);
		tabMine.setOnClickListener(this);
		tabSettings.setOnClickListener(this);
	}


	private void initView() {
		
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
	}


	@Override
	public void onClick(View v) {
		resetImg();
		switch (v.getId()) {
		case R.id.id_tab_home:
			setSelect(0);
			break;
		case R.id.id_tab_message:
			setSelect(1);
			break;
		case R.id.id_tab_knowledge:
			setSelect(2);
			break;
		case R.id.id_tab_mine:
			setSelect(3);
			break;
		case R.id.id_tab_settings:
			setSelect(4);
			break;
		case R.id.img_advertising:
			setSelect(5);
			break;
		default:
			break;
		}
	}

	private void setSelect(int i){
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		hideFragment(fragmentTransaction);
		switch (i) {
		case 0:
			tabImgHome.setImageResource(R.drawable.icon_home_press);
			if(contentHome == null){
				contentHome = new HomeFragment();
				fragmentTransaction.add(R.id.id_content, contentHome);
			}else{
				fragmentTransaction.show(contentHome);
			}
			break;
		case 1:
			tabImgMessage.setImageResource(R.drawable.icon_message_press);
			if(contentMessage == null){
				contentMessage = new MessageFragment();
				fragmentTransaction.add(R.id.id_content, contentMessage);
			}else{
				fragmentTransaction.show(contentMessage);
			}	
			break;
		case 2:
			tabImgKnowledge.setImageResource(R.drawable.icon_books_press);
			if(contentKnowledge == null){
				contentKnowledge = new KnowledgeFragment();
				fragmentTransaction.add(R.id.id_content, contentKnowledge);
			}else{
				fragmentTransaction.show(contentKnowledge);
			}
			break;
		case 3:
			tabImgMine.setImageResource(R.drawable.icon_user_press);
			if(contentMine == null){
				contentMine = new MineFragment();
				fragmentTransaction.add(R.id.id_content, contentMine);
			}else{
				fragmentTransaction.show(contentMine);
			}
			break;
		case 4:
			tabImgSettings.setImageResource(R.drawable.icon_gear_press);
			if(contentSettings == null){
				contentSettings = new SettingsFragment();
				fragmentTransaction.add(R.id.id_content, contentSettings);
			}else{
				fragmentTransaction.show(contentSettings);
			}
			break;

		default:
			break;
		}
		fragmentTransaction.commit();
	}
	
	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if(contentHome != null){
			fragmentTransaction.hide(contentHome);
		}
		if(contentMessage != null){
			fragmentTransaction.hide(contentMessage);
		}
		if(contentKnowledge != null){
			fragmentTransaction.hide(contentKnowledge);
		}
		if(contentMine != null){
			fragmentTransaction.hide(contentMine);
		}
		if(contentSettings != null){
			fragmentTransaction.hide(contentSettings);
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
