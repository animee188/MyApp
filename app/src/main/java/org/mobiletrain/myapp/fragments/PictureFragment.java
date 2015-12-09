package org.mobiletrain.myapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.mobiletrain.myapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.mobiletrain.myapp.interfaces.StringGetData;
import org.mobiletrain.myapp.parse.Parse_Id_info;
import org.mobiletrain.myapp.utils.CurrentTimeUtil;
import org.mobiletrain.myapp.utils.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment {

    private int monthCurrent;
    private int yearCurrent;
    private int dayCurrent;
    private int weekCurrent;

    private String url = "http://api.shigeten.net/api/Diagram/GetDiagramList";
    private TreeSet<Integer> set;
    private TreeSet<Integer> listPositionOffsetPixels;

    private List<Fragment> fragmentList;

    private ViewPager viewPager;
    private PictureFragmentAdapter adapter;

    private int position;
    private int postionBefore;
    private int monthPostion;
    private int dayPostion;
    private int weekPostion;

    public PictureFragment() {
        // Required empty public constructor
        set = new TreeSet<>();
        listPositionOffsetPixels = new TreeSet<>();

        set.add(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.fragment_picture_pager);
        final ImageView monthIv= (ImageView) view.findViewById(R.id.months);
        final ImageView date2Iv= (ImageView) view.findViewById(R.id.data2);
        final ImageView weekIv= (ImageView) view.findViewById(R.id.week);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.logo_diagram);

        // 获取系统当前时间
        int[] currentDate = CurrentTimeUtil.getCurrentDate();
        yearCurrent=currentDate[0];
        monthCurrent=currentDate[1];
        dayCurrent=currentDate[2];
        weekCurrent=currentDate[3];

        // 获得当前时间对应的图片的 postion
        int[] currentDatePic = CurrentTimeUtil.getCurrentDatePic(yearCurrent,monthCurrent, dayCurrent, weekCurrent);
        monthPostion = currentDatePic[0];
        dayPostion = currentDatePic[1];
        weekPostion = currentDatePic[2];

        // 第一个页面的图片
        monthIv.setImageResource(CurrentTimeUtil.monthPic[monthPostion]); // 月
        date2Iv.setImageResource(CurrentTimeUtil.listPicture.get(dayPostion)); // 日
        weekIv.setImageResource(CurrentTimeUtil.weekPic[weekPostion]); // 星期几

        fragmentList = new ArrayList<>();

        // 3. 初始化数据
        initData();

        // 监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                PictureFragment.this.position = position;
                      PictureFragment.this.listPositionOffsetPixels.add(positionOffsetPixels);

                Log.i("info","-----onPageScrolled-------"+position+" --- "+positionOffset+" --- "+positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {

                monthIv.setImageResource(CurrentTimeUtil.monthPic[monthPostion]); // 月

                int i = (position+dayPostion)%CurrentTimeUtil.listPicture.size(); // 日
                date2Iv.setImageResource(CurrentTimeUtil.listPicture.get(i));

                int j = (position+weekPostion)%CurrentTimeUtil.weekPic.length;
                weekIv.setImageResource(CurrentTimeUtil.weekPic[j]);

                // 月份的变化
                if(CurrentTimeUtil.listDays.get(i)==1){  // 1 号
                    if(postionBefore<position){ // 向左滑
                        monthCurrent=(++monthCurrent)%CurrentTimeUtil.months.length;
                    }
                }
                // 根据修改后的数据对 月份进行操作
                if(CurrentTimeUtil.listDays.get(i)==CurrentTimeUtil.listDays.get(0)){  // 一个月的最后一天
                    if(postionBefore>position){ // 向右滑
                        monthCurrent=(--monthCurrent)%CurrentTimeUtil.months.length;
                    }
                }

                postionBefore=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {

                    if (position == 0 && listPositionOffsetPixels.equals(set)) {
                        Toast.makeText(getActivity(), "已经更新到最新内容了", Toast.LENGTH_SHORT).show();
                    }
                    if (position == 9 && listPositionOffsetPixels.equals(set)) {
                        Toast.makeText(getActivity(), "只有十篇内容可查", Toast.LENGTH_SHORT).show();
                    }
                    listPositionOffsetPixels.clear();
                }
            }
        });

        return view;
    }

    //  为 list 填充数据
    private void initData() {
        // 获取网络访问的数据
        HttpUtils.getResult(url, new StringGetData() {

            private List<Integer> listPicure;

            @Override
            public void getStringData(String s) {
                // 解析
                if (s != null) {
                    listPicure = Parse_Id_info.ParsePicture.parsePictureAll(s);
                    // Fragment
                    for (int i = 0; i < listPicure.size(); i++) {

                        Fragment fragment = new PicturePagerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", listPicure.get(i));
                        fragment.setArguments(bundle);
                        fragmentList.add(fragment);
                    }

                    adapter = new PictureFragmentAdapter(getActivity().getSupportFragmentManager());
                    viewPager.setAdapter(adapter);


                } else {
                    Log.i("info", "未获取到网络的数据");
                }

            }
        });
    }


    // 适配器
    class PictureFragmentAdapter extends FragmentPagerAdapter {

        public PictureFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
