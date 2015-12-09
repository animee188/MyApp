package org.mobiletrain.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import org.mobiletrain.myapp.R;
import java.util.List;
import org.mobiletrain.myapp.bean.Movie_list_news;
import org.mobiletrain.myapp.unite.UniteApplication;
import org.mobiletrain.myapp.utils.MyImageLoader;

/**
 * Created by lk on 2015/11/15.
 */
public class Movie_List_Adapter extends BaseAdapter{
    private Context context;
    private List<Movie_list_news> list;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    public Movie_List_Adapter(Context context, List<Movie_list_news> list) {
        this.context = context;
        this.list = list;
        imageLoader = MyImageLoader.getLoader();
        requestQueue = MyImageLoader.getResquestQueue();
    }

    @Override
    public int getCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VoidHolder vh;
        if (convertView == null){
            vh = new VoidHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_list_item,null);
            vh.author = (TextView) convertView.findViewById(R.id.author);
            vh.author_bottom = (TextView) convertView.findViewById(R.id.author_bottom);
            vh.authorbrief = (TextView) convertView.findViewById(R.id.authorbrief);
            vh.realtitle = (TextView) convertView.findViewById(R.id.realtitle);
            vh.times = (TextView) convertView.findViewById(R.id.read);
            vh.title = (TextView) convertView.findViewById(R.id.title);
//            vh.titleforplay = (TextView) convertView.findViewById(R.id.title);
            vh.text1 = (TextView) convertView.findViewById(R.id.text1);
            vh.text1.setTextSize(UniteApplication.FontSize);
            vh.text2 = (TextView) convertView.findViewById(R.id.text2);
            vh.text3 = (TextView) convertView.findViewById(R.id.text3);
            vh.text4 = (TextView) convertView.findViewById(R.id.text4);
            vh.text5 = (TextView) convertView.findViewById(R.id.text5);
            vh.image1 = (ImageView) convertView.findViewById(R.id.image1);
            vh.image2 = (ImageView) convertView.findViewById(R.id.image2);
            vh.image3 = (ImageView) convertView.findViewById(R.id.image3);
            vh.image4 = (ImageView) convertView.findViewById(R.id.image4);
            convertView.setTag(vh);
        }else{
            vh = (VoidHolder) convertView.getTag();
        }
        Movie_list_news news = list.get(position);
        vh.text1.setText(news.getText1());
        vh.text2.setText(news.getText2().substring(news.getText2().indexOf("\n") + 1));
        vh.text3.setText(news.getText3());
        vh.text4.setText(news.getText4());
        vh.text5.setText(news.getText5());
        vh.realtitle.setText(news.getRealtitle());
        vh.title.setText(news.getTitle());
        vh.times.setText("阅读量： "+news.getTimes());
        vh.author.setText("作者： " + news.getAuthor());
        vh.author_bottom.setText(news.getAuthor());
        vh.authorbrief.setText(news.getAuthorbrief());

        imageLoader.get(news.getImage1(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                vh.image1.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        imageLoader.get(news.getImage2(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                vh.image2.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        imageLoader.get(news.getImage3(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                vh.image3.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        imageLoader.get(news.getImage4(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                vh.image4.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        return convertView;
    }
    class VoidHolder{
        TextView text1,text2,text3,text4,text5;
        ImageView image1,image2,image3,image4;
        TextView author,author_bottom,title,authorbrief,realtitle,times;
    }
}
