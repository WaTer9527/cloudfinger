package wang.cloudfinger.printing.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import wang.cloudfinger.printing.R;

public class BidActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bid);
	}
}