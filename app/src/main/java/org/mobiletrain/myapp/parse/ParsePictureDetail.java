package org.mobiletrain.myapp.parse;


import com.alibaba.fastjson.JSON;

import org.mobiletrain.myapp.bean.PictureDetail;


/**
 * Created by liusihui on 2015／12／8.
 */
public class ParsePictureDetail {
    public static PictureDetail parsePictureDetail(String json){
        if(json!=null){
            PictureDetail detail = JSON.parseObject(json, PictureDetail.class);
            return detail;
        }
        else {
            return null;
        }
    }
}
