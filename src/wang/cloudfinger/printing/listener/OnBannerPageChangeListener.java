package wang.cloudfinger.printing.listener;

import java.util.List;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;
import wang.cloudfinger.printing.R;
import wang.cloudfinger.printing.entity.Advertisement;

public class OnBannerPageChangeListener implements OnPageChangeListener {

	private List<Advertisement> adList; 
	private TextView textViewTitle;
	private TextView textViewDate;
	private TextView textViewAuthor;
	private List<View> dots;
	private int oldPositon = 0;
	private int currentPosition = 0;
	
	public OnBannerPageChangeListener(List<Advertisement> adList, View container, List<View> dots, int currentPositon) {
		this.adList = adList;
		this.textViewTitle = (TextView) container.findViewById(R.id.textView_title);
		this.textViewDate = (TextView) container.findViewById(R.id.textView_date);
		this.textViewAuthor = (TextView) container.findViewById(R.id.textView_author);
		this.dots = dots;
		this.currentPosition = currentPositon;
	}

	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		currentPosition = position;
		Advertisement advertisement = adList.get(position);
		textViewTitle.setText(advertisement.getTitle());
		textViewDate.setText(advertisement.getDate());
		textViewAuthor.setText(advertisement.getAuthor());
		dots.get(oldPositon).setBackgroundResource(R.drawable.dot_normal);
		dots.get(position).setBackgroundResource(R.drawable.dot_focused);
		oldPositon = position;
	}

}
