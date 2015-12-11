package org.mobiletrain.myapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.mobiletrain.myapp.interfaces.GetBitmap;
import org.mobiletrain.myapp.interfaces.StringGetData;

/**
 * Created by liusihui on 2015/12/5.
 */
public class HttpUtils {

   //最外层界面的链接地址
    public static final String MOVIE_URL = "http://api.shigeten.net/api/Critic/GetCriticList";
    public static final String ARTICLE_URL = "http://api.shigeten.net/api/Novel/GetNovelList";
    public static final String PICTURE_URL = "http://api.shigeten.net/api/Diagram/GetDiagramList";

    //详情地址
    public static final String MOVIE_DETATLS_URL = "http://api.shigeten.net/api/Critic/GetCriticContent?id=";
    public static final String ARTICLE_DETATLS_URL = "http://api.shigeten.net/api/Novel/GetNovelContent?id=";
    public static final String PICTURE_DETATLS_URL = "http://api.shigeten.net/api/Diagram/GetDiagramContent?id=";

    //加载图片地址
    public static final String PICTURE_DISPLAY_URL = "http://api.shigeten.net/";

    /**
     * 进行post请求
     */
    private Context context;
    private static RequestQueue queue;

    public HttpUtils(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static void getPostResult(String url, final String arg1, final String arg2, final StringGetData data) {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                data.getStringData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> ret = new HashMap<>();
                ret.put(arg1, arg2);

                return ret;
            }
        };
        queue.add(request);

    }


    /**
     * 进行get请求
     */
    public static void getResult(String url, final StringGetData data){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                  data.getStringData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }
    /**
     * 进行JsonObjectRequest请求
     */
    public static void getJsonObjectRequest (String url){
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }
    /**
     * 进行ImageRequest请求
     */
    public static void getImageRequest(String url, final GetBitmap bm,int widthPiexls,int heightPiexls){
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                     bm.forGetBitmap(bitmap);
            }
        }, widthPiexls, heightPiexls, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);

    }

    //当前网络是否连接
    public static boolean isNetWorkConnection(Context context){
        boolean flag = false;
        //判断网络状态		网络连接管理器		getSystemService 得到系统服务
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null)
            flag = info.isConnected();		//返回网络连接状态
        return flag;
    }
}
