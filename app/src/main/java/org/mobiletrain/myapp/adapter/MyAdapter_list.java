package org.mobiletrain.myapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.mobiletrain.myapp.R;
import java.util.List;
import org.mobiletrain.myapp.bean.Text_detail_info;
import org.mobiletrain.myapp.interfaces.GetBitmap;
import org.mobiletrain.myapp.unite.UniteApplication;
import org.mobiletrain.myapp.utils.HttpUtils;

/**
 * Created by liusihui on 2015/12/8.
 */
public class MyAdapter_list extends BaseAdapter{
    private List<Text_detail_info> data;
    private LayoutInflater inflater;

    private Context context;
    private int width;
    private int height;
    public MyAdapter_list(List<Text_detail_info> data,Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        int count = 0;
        if (data != null)
            count = data.size();
        return count;
    }
    @Override
    public Object getItem(int position) {
        Object obj = null;
        if (data != null)
            obj = data.get(position);
        return obj;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder vh;
        if (view == null){
            view = inflater.inflate(R.layout.text_list_item,null);
            vh = new ViewHolder();
            vh.text_title = (TextView) view.findViewById(R.id.text_title);
            vh.text_author = (TextView) view.findViewById(R.id.text_author);
            vh.text_times = (TextView) view.findViewById(R.id.text_times);
            vh.text_summary = (TextView) view.findViewById(R.id.text_summary);
            vh.text_text = (TextView) view.findViewById(R.id.text_text);
            vh.text_text.setTextSize(UniteApplication.FontSize);
            vh.text_author2 = (TextView) view.findViewById(R.id.text_author2);
            vh.text_authorbrief = (TextView) view.findViewById(R.id.text_authorbrief);
            vh.text_image = (ImageView) view.findViewById(R.id.text_image);
            view.setTag(vh);
        }
        else
            vh = (ViewHolder) view.getTag();
        Text_detail_info info = data.get(position);
        vh.text_title.setText(info.getTitle());
        vh.text_author.setText("作者："+info.getAuthor());
        vh.text_times.setText("阅读量："+info.getTimes());
        vh.text_summary.setText(info.getSummary());
        vh.text_author2.setText(info.getAuthor());
        vh.text_text.setText(info.getText());
        vh.text_text.setTextSize(UniteApplication.FontSize);
        vh.text_authorbrief.setText(info.getAuthorbrief());
        final String image = info.getImage();
        Log.i("info","----image-----"+image);
        //访问下载图片
        HttpUtils.getImageRequest("http://api.shigeten.net/" + image, new GetBitmap() {

            @Override
            public void forGetBitmap(Bitmap bm) {
                height = bm.getHeight();
                width = bm.getWidth();
                if (image != null)
                    vh.text_image.setImageBitmap(bm);
                else
                    vh.text_image.setVisibility(View.GONE);
            }
        },height,width);
        return view;
    }
    class ViewHolder{
        TextView text_title,text_author,text_times,text_summary,text_text,text_author2,text_authorbrief;
        ImageView text_image;
    }
 }
