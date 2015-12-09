package org.mobiletrain.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import java.util.List;
import org.mobiletrain.myapp.fragments.Text_detail_fragment;

/**
 * 文本页面的fragment的适配器
 * Created by liusihui on 2015/12/8.
 */
public class MyAdapter_text extends FragmentPagerAdapter {
    private List<Text_detail_fragment> data;

    public MyAdapter_text(FragmentManager fm,List<Text_detail_fragment> data) {
        super(fm);
        this.data = data;
        Log.i("info","data.size-----"+data.size());
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (data != null)
            fragment = data.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (data != null)
            count = data.size();
        return count;
    }
}