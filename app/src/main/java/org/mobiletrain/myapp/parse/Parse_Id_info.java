package org.mobiletrain.myapp.parse;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.mobiletrain.myapp.bean.Text_id_info;

/**
 * Created by liusihui on 2015/12/8.
 */
public class Parse_Id_info {
    public static List<Text_id_info>parse(String json){
        List<Text_id_info>data = new ArrayList<>();
        try {
            JSONArray arr = new JSONObject(json).getJSONArray("result");
            for (int i = 0;i<arr.length();i++){
                JSONObject obj = arr.getJSONObject(i);
                Text_id_info info = new Text_id_info();
                info.setId(obj.optString("id"));
                data.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
