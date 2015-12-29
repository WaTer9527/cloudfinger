package wang.cloudfinger.printing.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import wang.cloudfinger.printing.R;

public class AdvertisingActivity extends FragmentActivity implements OnClickListener {
	
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private Fragment contentAdvertising;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_advertising);
		
		initVariables();
		initViews();
		loadData();
	}
	
	private void initVariables() {
		fragmentManager = getSupportFragmentManager();
	}
	
	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if(contentAdvertising != null){
			fragmentTransaction.hide(contentAdvertising);
		}
	}

	private void initViews() {
		setSelect(0);
	}
	
	private void setSelect(int i) {
		fragmentTransaction = fragmentManager.beginTransaction();
		hideFragment(fragmentTransaction);
		switch (i) {
		case 0:
			if(contentAdvertising == null){
				contentAdvertising = new AdvertisingFragment();
				fragmentTransaction.add(R.id.id_advertising, contentAdvertising);
			}else{
				fragmentTransaction.show(contentAdvertising);
			}
			break;

		default:
			break;
		}
		fragmentTransaction.commit();
	}

	private void loadData() {
		
	}

	@Override
	public void onClick(View v) {
		
	}
}
