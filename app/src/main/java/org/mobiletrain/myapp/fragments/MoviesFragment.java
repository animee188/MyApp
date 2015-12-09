package org.mobiletrain.myapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.mobiletrain.myapp.adapter.MovieAdapter;
import org.mobiletrain.myapp.bean.Movie_news;
import org.mobiletrain.myapp.interfaces.StringGetData;
import org.mobiletrain.myapp.utils.ParseJson;
import org.mobiletrain.myapp.utils.CurrentTimeUtil;
import org.mobiletrain.myapp.utils.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private List<Fragment> fragments;
    private final String url = "http://api.shigeten.net/api/Critic/GetCriticList";
    private List<Movie_news> list;
    private ViewPager movievp;

    private int position,postionBefore;
    private int yearCurrent;
    private int monthCurrent;
    private int dayCurrent;
    private int weekCurrent;
    private int monthPostion;
    private int dayPostion;
    private int weekPostion;
    private TreeSet<Integer> set;
    private TreeSet<Integer> listPositionOffsetPixels;
    private ImageView monthIv;
    private ImageView date2Iv;
    private ImageView weekIv;

    public MoviesFragment() {
        // Required empty public constructor
        set = new TreeSet<>();
        listPositionOffsetPixels = new TreeSet<>();

        set.add(0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        movievp = (ViewPager) view.findViewById(R.id.movie_vp);
        monthIv = (ImageView) view.findViewById(R.id.months);
        date2Iv = (ImageView) view.findViewById(R.id.data2);
        weekIv = (ImageView) view.findViewById(R.id.week);



        // 获取系统当前时间
        int[] currentDate = CurrentTimeUtil.getCurrentDate();
        yearCurrent =currentDate[0];
        monthCurrent =currentDate[1];
        dayCurrent =currentDate[2];
        weekCurrent =currentDate[3];

        // 获得当前时间对应的图片的 postion
        int[] currentDatePic = CurrentTimeUtil.getCurrentDatePic(yearCurrent, monthCurrent, dayCurrent, weekCurrent);
        monthPostion = currentDatePic[0];
        dayPostion = currentDatePic[1];
        weekPostion = currentDatePic[2];

        // 第一个页面的图片
        monthIv.setImageResource(CurrentTimeUtil.monthPic[monthPostion]); // 月
        date2Iv.setImageResource(CurrentTimeUtil.listPicture.get(dayPostion)); // 日
        weekIv.setImageResource(CurrentTimeUtil.weekPic[weekPostion]); // 星期几

        HttpUtils.getResult(url, new StringGetData() {
            @Override
            public void getStringData(String s) {
                list = ParseJson.getInfo(s);
                initData();

            }
        });

        return view;
    }

    private void initData() {
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size() ; i++) {
            Movie_vp_fragment f = new Movie_vp_fragment();
            Movie_news news = list.get(i);
            int id = news.getId();
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            f.setArguments(bundle);
            fragments.add(f);
        }
        movievp.setAdapter(new MovieAdapter(getFragmentManager(), fragments));
        // 监听
        movievp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                MoviesFragment.this.position = position;
                MoviesFragment.this.listPositionOffsetPixels.add(positionOffsetPixels);

                Log.i("info", "-----onPageScrolled-------" + position + " --- " + positionOffset + " --- " + positionOffsetPixels);

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

                //  没有判断 一个月 有多少天 , 没有进行月份的变化
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

    }


}
