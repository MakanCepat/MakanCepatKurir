package gonorus.makancepatkurir.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.adapter.ImageSliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentImageSlider extends Fragment {

    private ViewPager mViewPager;
    private ImageSliderAdapter adapterView;
    ImageView btnCircle1, btnCircle2, btnCircle3;

    public FragmentImageSlider() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image_slider, container, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.imageSlidePager);
        adapterView = new ImageSliderAdapter(getActivity());
        mViewPager.setAdapter(adapterView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setTab();
    }

    private void initView() {
        btnCircle1 = (ImageView) getView().findViewById(R.id.btnIndicator1);
        btnCircle1.setImageResource(R.drawable.fill_circle);
        btnCircle2 = (ImageView) getView().findViewById(R.id.btnIndicator2);
        btnCircle3 = (ImageView) getView().findViewById(R.id.btnIndicator3);
    }

    private void setTab() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                btnCircle1.setImageResource(R.drawable.holo_circle);
                btnCircle2.setImageResource(R.drawable.holo_circle);
                btnCircle3.setImageResource(R.drawable.holo_circle);
                btnAction(position);
            }

        });
    }
    private void btnAction(int action) {

        switch (action) {
            case 0:
                btnCircle1.setImageResource(R.drawable.fill_circle);
                break;
            case 1:
                btnCircle2.setImageResource(R.drawable.fill_circle);
                break;
            case 2:
                btnCircle3.setImageResource(R.drawable.fill_circle);
                break;
        }
    }
}
