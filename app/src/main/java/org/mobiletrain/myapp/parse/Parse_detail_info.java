package org.mobiletrain.myapp.parse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.mobiletrain.myapp.bean.Text_detail_info;

/**
 * Created by Administrator on 2015/11/14.
 */
public class Parse_detail_info {
    public static List<Text_detail_info> parse(String json){
        List<Text_detail_info>data = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            Text_detail_info info = new Text_detail_info();
            info.setTitle(obj.optString("title"));
            info.setAuthor(obj.optString("author"));
            info.setTimes(obj.optString("times"));
            info.setSummary(obj.optString("summary"));
            info.setText(obj.optString("text"));
            info.setAuthorbrief(obj.optString("authorbrief"));
            info.setImage(obj.optString("image"));
            data.add(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
