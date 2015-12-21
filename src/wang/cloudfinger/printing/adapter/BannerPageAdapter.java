package wang.cloudfinger.printing.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import wang.cloudfinger.printing.entity.Advertisement;

public class BannerPageAdapter extends PagerAdapter {
	
	private List<Advertisement> adList;
	
	private List<ImageView> imageViews;
	
	public BannerPageAdapter(List<Advertisement> adList, List<ImageView> imageViews) {
		this.adList = adList;
		this.imageViews = imageViews;
	}

	@Override
	public int getCount() {
		return adList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	@Override
	public Object instantiateItem(View container, int position) {
		ImageView imageView = imageViews.get(position);
		((ViewPager)container).addView(imageView);
		final Advertisement advertisement = adList.get(position);
		//图片点击事件
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//处理跳转逻辑
			}
		});
		return imageView;
	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView((View)object);
	}
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}
	@Override
	public Parcelable saveState() {
		return null;
	}
	@Override
	public void startUpdate(View container) {
	}
	@Override
	public void finishUpdate(View container) {
	}

}
