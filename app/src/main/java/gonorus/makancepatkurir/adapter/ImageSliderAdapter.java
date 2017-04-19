package gonorus.makancepatkurir.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import gonorus.makancepatkurir.R;

/**
 * Created by ADMIN on 8/3/2016.
 */

public class ImageSliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private int totalPage = 0;
    private int[] sliderImagesId = new int[]{
            R.drawable.drawer_background_material,R.drawable.drawer_background_material,R.drawable.drawer_background_material,
            R.drawable.drawer_background_material,R.drawable.drawer_background_material,R.drawable.drawer_background_material
    };

    public ImageSliderAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fragment_image_slider, container, false);
        ImageView mImageView = new ImageView(this.context);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageResource(sliderImagesId[position]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
