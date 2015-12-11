package org.mobiletrain.myapp.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mobiletrain.myapp.R;

import org.mobiletrain.myapp.bean.PictureDetail;
import org.mobiletrain.myapp.interfaces.GetBitmap;
import org.mobiletrain.myapp.interfaces.StringGetData;
import org.mobiletrain.myapp.parse.ParsePictureDetail;
import org.mobiletrain.myapp.utils.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicturePagerFragment extends Fragment {


    private ImageView image;
    private TextView title;
    private TextView auther;
    private TextView summary;
    private TextView summary2;
    private String urlDetetal="http://api.shigeten.net/api/Diagram/GetDiagramContent?id=";
    private String imageUrl="http://api.shigeten.net/";

    private int width;
    private int height;

    public PicturePagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView=inflater.inflate(R.layout.fragment_picture_item, container, false);

        initView(itemView); // 初始化控件

        // 获取传来的 id
        int id = getArguments().getInt("id", 0);
        // 根据id 填充数据

        HttpUtils.getResult(urlDetetal + id, new StringGetData() {

            @Override
            public void getStringData(String s) {
                if (s != null) {
                    // 解析
                    PictureDetail detail = ParsePictureDetail.parsePictureDetail(s);

                    if (detail != null) {

                        title.setText(detail.getTitle());
                        auther.setText(detail.getAuthorbrief());
                        summary.setText(detail.getText1());
                        summary2.setText(detail.getText2());

                        detail.getPublishtime();

                        // 为 imgeView 赋值
                        final String image1 = detail.getImage1();

                        HttpUtils.getImageRequest(imageUrl + image1, new GetBitmap() {

                            @Override
                            public void forGetBitmap(Bitmap bm) {

                                height = bm.getHeight();
                                width = bm.getWidth();

                                Drawable drawable = image.getDrawable();
                                int intrinsicHeight = drawable.getIntrinsicHeight();
                                int intrinsicWidth = drawable.getIntrinsicWidth();

                                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(intrinsicWidth,intrinsicHeight);
                                image.setLayoutParams(layoutParams);

                                image.setImageBitmap(bm);
                            }
                        },width,height);

                    } else {
                        Log.i("info", "未获取到第二步解析的数据");
                    }

                } else {
                    Log.i("info", "未获取到第二步访问的数据");
                }
            }
        });

        return itemView;
    }

    private void initView(View itemView) {
        image = (ImageView) itemView.findViewById(R.id.fragment_picture_item_image);
        title = (TextView) itemView.findViewById(R.id.fragment_picture_item_title);
        auther = (TextView) itemView.findViewById(R.id.fragment_picture_item_authorbrief);
        summary = (TextView) itemView.findViewById(R.id.fragment_picture_item_summary);
        summary2 = (TextView) itemView.findViewById(R.id.fragment_picture_item_summary2);
    }

}
