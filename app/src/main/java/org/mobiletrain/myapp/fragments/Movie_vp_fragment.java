package org.mobiletrain.myapp.fragments;


import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import org.mobiletrain.myapp.R;

import java.util.ArrayList;
import java.util.List;

import org.mobiletrain.myapp.adapter.Movie_List_Adapter;
import org.mobiletrain.myapp.bean.Movie_list_news;
import org.mobiletrain.myapp.interfaces.StringGetData;
import org.mobiletrain.myapp.utils.ParseJson;
import org.mobiletrain.myapp.utils.HttpUtils;
import org.mobiletrain.myapp.utils.MyImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class Movie_vp_fragment extends Fragment {
    private ImageLoader imageLoader;
    private RequestQueue queue;
    private final  String url = HttpUtils.MOVIE_DETATLS_URL;
    private List<Movie_list_news> list;
    private ListView lv;
    private int tag = 0;

    public Movie_vp_fragment() {
        // Required empty public constructor
        imageLoader = MyImageLoader.getLoader();
        queue = MyImageLoader.getResquestQueue();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.movie_fragment_vp, container, false);
        Bundle bundle = getArguments();
        int i = bundle.getInt("id");
        lv = (ListView) view.findViewById(R.id.movie_list);

       ;final View head = inflater.inflate(R.layout.movie_listhead, null);
        lv.addHeaderView(head);
        HttpUtils.getResult(url + i, new StringGetData() {
            @Override
            public void getStringData(String s) {
                list = ParseJson.getListItem(s);
                Movie_List_Adapter adapter = new Movie_List_Adapter(getActivity(), list);
                lv.setAdapter(adapter);
                initHead(head);
            }
        });

        return view;
    }

    private void initHead(View view) {
        TextView titleforplay = (TextView) view.findViewById(R.id.video_name);
        titleforplay.setText("播放" + list.get(tag).getTitleforplay());

        final ImageView video = (ImageView) view.findViewById(R.id.video);
        imageLoader.get(list.get(tag).getImimageforplay(), new ImageLoader.ImageListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap bitmap = imageContainer.getBitmap();
//                Drawable drawable = new BitmapDrawable(bitmap);
                video.setImageBitmap(bitmap);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        ImageView start_video = (ImageView) view.findViewById(R.id.start_video);
        start_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = list.get(tag).getUrlforplay();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(path));
                startActivity(intent);
            }
        });
        RelativeLayout empty = (RelativeLayout) view.findViewById(R.id.empty_layout);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = list.get(tag).getUrlforplay();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(path));
                startActivity(intent);
            }
        });
    }


}
