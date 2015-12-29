package wang.cloudfinger.printing.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import wang.cloudfinger.printing.R;
import wang.cloudfinger.printing.adapter.BannerPageAdapter;
import wang.cloudfinger.printing.entity.Advertisement;
import wang.cloudfinger.printing.utils.Constants;

public class AdvertisingFragment extends Fragment {

	private View advertisingView;
	
	private ViewPager viewPager;
	private List<ImageView> imageViews;
	private TextView textViewTitle;
	private TextView textViewDate;
	private TextView textViewAuthor;
	private List<View> dots;
	private List<View> dotList;
	private View dot0;
	private View dot1;
	private View dot2;
	private View dot3;
	private View dot4;
	private int currentItem = 0;
	
	private ScheduledExecutorService scheduledExecutorService;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	private List<Advertisement> adList;
	private PagerAdapter bannerPageAdapter;
	private Handler bannerHandler;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		advertisingView = inflater.inflate(R.layout.tab_advertising, container, false);
		initVariables();
		initViews();
		loadData();
		return advertisingView;
	}
	
	@Override
	public void onStop() {
		super.onStop();
		scheduledExecutorService.shutdown();
	}
	
	private void initVariables() {
		initImageLoader();
		
		imageViews = new ArrayList<ImageView>();
		dots = new ArrayList<View>();
		dotList = new ArrayList<View>();
		
		viewPager = (ViewPager) advertisingView.findViewById(R.id.viewPager);
		textViewTitle = (TextView) advertisingView.findViewById(R.id.textView_title);
		textViewDate = (TextView) advertisingView.findViewById(R.id.textView_date);
		textViewAuthor = (TextView) advertisingView.findViewById(R.id.textView_author);
		
		dot0 = advertisingView.findViewById(R.id.view_dot0);
		dot1 = advertisingView.findViewById(R.id.view_dot1);
		dot2 = advertisingView.findViewById(R.id.view_dot2);
		dot3 = advertisingView.findViewById(R.id.view_dot3);
		dot4 = advertisingView.findViewById(R.id.view_dot4);
		
		dots.add(dot0);
		dots.add(dot1);
		dots.add(dot2);
		dots.add(dot3);
		dots.add(dot4);
		
		adList = getBannerAd();
		
		bannerPageAdapter = new BannerPageAdapter(adList, imageViews);
		
		bannerHandler = new Handler(){
			public void handleMessage(Message msg){
				viewPager.setCurrentItem(currentItem);
			}
		};
	}

	private void initViews() {
		viewPager.setAdapter(bannerPageAdapter);
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			private int oldPosition = 0;
			@Override
			public void onPageSelected(int position) {
				currentItem = position;
				Advertisement advertisement = adList.get(position);
				textViewTitle.setText(advertisement.getTitle());
				textViewDate.setText(advertisement.getDate());
				textViewAuthor.setText(advertisement.getAuthor());
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldPosition = position;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		addDynamicView();
		startAd();
	}

	private void loadData() {
		
	}
	
	private List<Advertisement> getBannerAd() {
		List<Advertisement> adList = new ArrayList<Advertisement>();
		Advertisement advertisement1 = new Advertisement();
		advertisement1.setDate("12月21日");
		advertisement1.setTitle("云指印务上线了");
		advertisement1.setAuthor("By WaTer");
		advertisement1.setImgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=bb99d6add2c8a786be2a4c0f5708c9c7/d50735fae6cd7b8900d74cd40c2442a7d9330e29.jpg");
		adList.add(advertisement1);

		Advertisement advertisement2 = new Advertisement();
		advertisement2.setDate("12月22日");
		advertisement2.setTitle("云指印务上线了");
		advertisement2.setAuthor("By WaTer");
		advertisement2.setImgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=7cbcd7da78f40ad115e4c1e2672e1151/eaf81a4c510fd9f9a1edb58b262dd42a2934a45e.jpg");
		adList.add(advertisement2);

		Advertisement advertisement3 = new Advertisement();
		advertisement3.setDate("12月23日");
		advertisement3.setTitle("云指印务上线了");
		advertisement3.setAuthor("By WaTer");
		advertisement3.setImgUrl("http://e.hiphotos.baidu.com/image/w%3D310/sign=392ce7f779899e51788e3c1572a6d990/8718367adab44aed22a58aeeb11c8701a08bfbd4.jpg");
		adList.add(advertisement3);

		Advertisement advertisement4 = new Advertisement();
		advertisement4.setDate("12月24日");
		advertisement4.setTitle("云指印务上线了");
		advertisement4.setAuthor("By WaTer");
		advertisement4.setImgUrl("http://d.hiphotos.baidu.com/image/w%3D310/sign=54884c82b78f8c54e3d3c32e0a282dee/a686c9177f3e670932e4cf9338c79f3df9dc55f2.jpg");
		adList.add(advertisement4);

		Advertisement advertisement5 = new Advertisement();
		advertisement5.setDate("12月25日");
		advertisement5.setTitle("云指印务上线了");
		advertisement5.setAuthor("By WaTer");
		advertisement5.setImgUrl("http://e.hiphotos.baidu.com/image/w%3D310/sign=66270b4fe8c4b7453494b117fffd1e78/0bd162d9f2d3572c7dad11ba8913632762d0c30d.jpg");
		adList.add(advertisement5);
		return adList;
	}

	private void initImageLoader() {
		File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils
				.getOwnCacheDirectory(this.getActivity(), Constants.IMAGE_CACHE_PATH);
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getActivity())
				.defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new LruMemoryCache(12*1024*1024))
				.memoryCacheSize(12*1024*1024)
				.discCacheSize(32*1024*1024).discCacheFileCount(100)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.threadPriority(Thread.NORM_PRIORITY -2)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.top_banner_android)
				.showImageForEmptyUri(R.drawable.top_banner_android)
				.showImageOnFail(R.drawable.top_banner_android)
				.cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY).build();
	}
	
	private void addDynamicView() {
		for (int i = 0; i < adList.size(); i++) {
			ImageView imageView = new ImageView(this.getActivity());
			//异步加载图片
			imageLoader.displayImage(adList.get(i).getImgUrl(), imageView, options);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
			dots.get(i).setVisibility(View.VISIBLE);
			dotList.add(dots.get(i));
		}
	}

	private class ScrollTask implements Runnable {
		@Override
		public void run() {
			synchronized(viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				bannerHandler.obtainMessage().sendToTarget();
			}
		}
	}

	private void startAd() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		//当fragment显示出来后，每隔两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
	}
	
}
