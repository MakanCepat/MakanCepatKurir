package gonorus.makancepatkurir.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gonorus.makancepatkurir.view.FragmentLogin1;
import gonorus.makancepatkurir.view.FragmentLogin2;
import gonorus.makancepatkurir.view.FragmentLogin3;

/**
 * Created by USER on 1/30/2017.
 */

public class ImageLoginAdapter extends FragmentPagerAdapter {
    private Context _context;
    public static int totalPage = 3;

    public ImageLoginAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch (position) {
            case 0:
                f = new FragmentLogin1();
                break;
            case 1:
                f = new FragmentLogin2();
                break;
            case 2:
                f = new FragmentLogin3();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return totalPage;
    }
}