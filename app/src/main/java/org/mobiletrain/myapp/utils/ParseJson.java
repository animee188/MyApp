package org.mobiletrain.myapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.mobiletrain.myapp.bean.Movie_list_news;
import org.mobiletrain.myapp.bean.Movie_news;

/**
 * Created by liusihui on 2015/12/8.
 */
public class ParseJson {
    public static List<Movie_news> getInfo(String path){
        List<Movie_news> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(path);
            JSONArray arr = obj.getJSONArray("result");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj2 = arr.getJSONObject(i);
                Movie_news news = new Movie_news();
                news.setId(obj2.getInt("id"));
                news.setImage(obj2.getString("image"));
                list.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    public static List<Movie_list_news> getListItem(String path){
        List<Movie_list_news> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(path);
            Movie_list_news news = new Movie_list_news();
            news.setAuthor(obj.getString("author"));
            news.setText1(obj.getString("text1"));
            news.setText2(obj.getString("text2"));
            news.setText3(obj.getString("text3"));
            news.setText4(obj.getString("text4"));
            news.setText5(obj.getString("text5"));
            news.setTitle(obj.getString("title"));
            news.setAuthorbrief(obj.getString("authorbrief"));
            news.setRealtitle(obj.getString("realtitle"));
            news.setTitleforplay(obj.getString("titleforplay"));
            news.setTimes(obj.getInt("times"));
            news.setImage1("http://api.shigeten.net/" + obj.getString("image1"));
            news.setImage2("http://api.shigeten.net/" + obj.getString("image2"));
            news.setImage3("http://api.shigeten.net/" + obj.getString("image3"));
            news.setImage4("http://api.shigeten.net/" + obj.getString("image4"));
            news.setUrlforplay(obj.getString("urlforplay"));
            news.setImimageforplay("http://api.shigeten.net/"+obj.getString("imageforplay"));
            list.add(news);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
