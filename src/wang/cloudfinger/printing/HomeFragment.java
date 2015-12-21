package wang.cloudfinger.printing;

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
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import wang.cloudfinger.printing.model.Advertisement;

public class HomeFragment extends Fragment {
	
	public static String IMAGE_CACHE_PATH = "imageLoader/cache";
	
	private View homeView;
	private ViewPager viewPager;
	private List<ImageView> imageViews;
	
	private List<View> dots;
	private List<View> dotList;
	
	private TextView textViewDate;
	private TextView textViewTitle;
	private TextView textViewAuthor;
	private int currentItem = 0;
	
	private View dot0;
	private View dot1;
	private View dot2;
	private View dot3;
	private View dot4;
	
	private ScheduledExecutorService scheduledExecutorService;
	
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	private List<Advertisement> adList;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			viewPager.setCurrentItem(currentItem);
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		homeView = inflater.inflate(R.layout.tab_home, container, false);
		
		initImageLoader();
		
		//获取图片加载实例
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.top_banner_android)
				.showImageForEmptyUri(R.drawable.top_banner_android)
				.showImageOnFail(R.drawable.top_banner_android)
				.cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY).build();
		
		initAdData();
		
		startAd();
		return homeView;
	}

	private void initAdData() {
		adList = getBannerAd();
		
		imageViews = new ArrayList<ImageView>();
		
		dots = new ArrayList<View>();
		dotList = new ArrayList<View>();
		dot0 = homeView.findViewById(R.id.view_dot0);
		dot1 = homeView.findViewById(R.id.view_dot1);
		dot2 = homeView.findViewById(R.id.view_dot2);
		dot3 = homeView.findViewById(R.id.view_dot3);
		dot4 = homeView.findViewById(R.id.view_dot4);
		dots.add(dot0);
		dots.add(dot1);
		dots.add(dot2);
		dots.add(dot3);
		dots.add(dot4);
		
		textViewDate = (TextView) homeView.findViewById(R.id.textView_date);
		textViewTitle = (TextView) homeView.findViewById(R.id.textView_title);
		textViewAuthor = (TextView) homeView.findViewById(R.id.textView_author);
		
		viewPager = (ViewPager) homeView.findViewById(R.id.viewPager);
		viewPager.setAdapter(new MyAdapter());
		viewPager.addOnPageChangeListener(new MyPageChangeListener());
		addDynamicView();
	}
	
	private void addDynamicView() {
		//动态添加图片和下面指示的圆点
		//初始化图片资源
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

	private void startAd() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		//当fragment显示出来后，每隔两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
	}

	private class ScrollTask implements Runnable {
		@Override
		public void run() {
			synchronized(viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		//当Activity不可见时停止切换
		scheduledExecutorService.shutdown();
	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPositon = 0;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			currentItem = position;
			Advertisement advertisement = adList.get(position);
			textViewTitle.setText(advertisement.getTitle());
			textViewDate.setText(advertisement.getDate());
			textViewAuthor.setText(advertisement.getAuthor());
			dots.get(oldPositon).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPositon = position;
		}
		
	}
	
	private class MyAdapter extends PagerAdapter {

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
	
	private void initImageLoader() {
		File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils
				.getOwnCacheDirectory(this.getActivity(), IMAGE_CACHE_PATH);
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
	}
	
	/**
	 * 模拟数据
	 * @return
	 */
	public static List<Advertisement> getBannerAd(){
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
}
