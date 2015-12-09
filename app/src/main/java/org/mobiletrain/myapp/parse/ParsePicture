package org.mobiletrain.myapp.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.mobiletrain.myapp.bean.PictureAll;

/**
 * Created by liusihui on 2015/12／9.
 */
public class ParsePicture {
    public static List<Integer> parsePictureAll(String json){
        List<Integer> list=new ArrayList<>();

        try {
            JSONArray array = new JSONObject(json).getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                int id = jsonObject.optInt("id");
                list.add(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
