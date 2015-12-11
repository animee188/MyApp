package org.mobiletrain.myapp.fragments;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import org.mobiletrain.myapp.R;
import java.util.List;

import org.mobiletrain.myapp.adapter.MyAdapter_list;
import org.mobiletrain.myapp.bean.Text_detail_info;
import org.mobiletrain.myapp.interfaces.StringGetData;
import org.mobiletrain.myapp.parse.Parse_detail_info;
import org.mobiletrain.myapp.utils.HttpUtils;

/**
 * Created by liusihui on 2015/12/8.
 */
public class Text_detail_fragment extends Fragment{
    private String detail_path = HttpUtils.ARTICLE_DETATLS_URL;
    private String index;
    private ListView text_list;
    private List<Text_detail_info> parse_info;
    private ImageView text_list_animation;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_viewpager, null);
        initView(view);
        index = getArguments().getString("index");
        setData();
        //listView的监听事件
        text_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        return view;
    }
    //获得数据，解析并设置给TextView
    private void setData() {
        //访问接口，获取数据
        HttpUtils.getResult(detail_path + index, new StringGetData() {
            @Override
            public void getStringData(String s) {
                //解析数据
                parse_info = Parse_detail_info.parse(s);
                MyAdapter_list adapter = new MyAdapter_list(parse_info,getActivity());
                text_list.setAdapter(adapter);
            }
        });
    }
    //初始化View
    private void initView(View view) {
        text_list = (ListView) view.findViewById(R.id.text_list);
        text_list_animation = (ImageView) view.findViewById(R.id.text_list_animation);
        //当listView为空的时候，填充动画
        AnimationDrawable animation = (AnimationDrawable) text_list_animation.getBackground();
        animation.start();
        text_list.setEmptyView(text_list_animation);
    }
}
