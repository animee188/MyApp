package org.mobiletrain.myapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import org.mobiletrain.myapp.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.mobiletrain.myapp.adapter.MyAdapter_text;
import org.mobiletrain.myapp.bean.Text_id_info;
import org.mobiletrain.myapp.interfaces.StringGetData;
import org.mobiletrain.myapp.parse.Parse_Id_info;
import org.mobiletrain.myapp.utils.CurrentTimeUtil;
import org.mobiletrain.myapp.utils.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {
    private String id_path = "http://api.shigeten.net/api/Novel/GetNovelList";                  //id的路径
    private ViewPager text_viewPager;
    private List<Text_detail_fragment> data;
    private List<Text_id_info> parse_id;
    private ImageView top_ima,text_icon;
    private HashSet<Integer>set;
    private HashSet<Integer>listPositionOffsetPixels;
    private int position1,state;
    private int position;
    private int postionBefore;
    private int monthPostion;
    private int dayPostion;
    private int weekPostion;
    private int monthCurrent;
    private int yearCurrent;
    private int dayCurrent;
    private int weekCurrent;

    public TextFragment() {
        // Required empty public constructor
        set = new HashSet<>();
        listPositionOffsetPixels = new HashSet<>();
        set.add(0);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        initView(view);
        final ImageView monthIv= (ImageView) view.findViewById(R.id.months);
        final ImageView date2Iv= (ImageView) view.findViewById(R.id.data2);
        final ImageView weekIv= (ImageView) view.findViewById(R.id.week);


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
        initData();
        //给ViewPager设置监听
        text_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position1, float positionOffset, int positionOffsetPixels) {
                TextFragment.this.position1 = position1;
                TextFragment.this.listPositionOffsetPixels.add(positionOffsetPixels);
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
                TextFragment.this.state = state;
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
    //初始化view
    private void initView(View view) {
        text_viewPager = (ViewPager) view.findViewById(R.id.text_viewPager);
        text_icon = (ImageView) view.findViewById(R.id.text_icon);
        top_ima = (ImageView) view.findViewById(R.id.imageView);
        top_ima.setImageResource(R.mipmap.logo_novel);
    }

    //发送数据到Text_detail_info页面
    private void initViewPager() {
        data = new ArrayList<>();
        for (int i = 0;i<parse_id.size();i++){
            Text_detail_fragment fragment = new Text_detail_fragment();
            Text_id_info info = parse_id.get(i);
            String index = info.getId();
            Bundle bundle = new Bundle();
            bundle.putString("index",index);
            fragment.setArguments(bundle);
            data.add(fragment);
        }
    }
    //获得联网访问需要的id
    private void initData() {
        //判断网络是否连接，若连接则网络访问接口；否则显示一个图片，点击图片，吐丝，“暂无网络连接，请检查后重试”
        boolean flag = HttpUtils.isNetWorkConnection(getActivity());
        if (flag){
            //网络访问接口，获得数据
            HttpUtils.getResult(id_path, new StringGetData() {

                @Override
                public void getStringData(String s) {
                    //解析数据，获得想要的id
                    parse_id = Parse_Id_info.parse(s);
                    initViewPager();
                    //设置适配器
                    MyAdapter_text adapter = new MyAdapter_text(getFragmentManager(), data);
                    text_viewPager.setAdapter(adapter);
                }
            });
        }
        else {
            text_icon.setImageResource(R.mipmap.retry_icon);
            //给ImageView设置监听事件，点击图片
            text_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"暂无网络连接，请检查后重试",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




}
