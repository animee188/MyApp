package org.mobiletrain.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by liusihui on 2015/12/8.
 */
public class MovieAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;

    public MovieAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }
}
