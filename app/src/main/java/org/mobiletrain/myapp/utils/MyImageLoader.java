package org.mobiletrain.myapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * 单例模式的三个步骤：
 *  1. 创建一个 private static final 类名 对象名；
 *  2. 私有化构造方法
 *  3. 创建一个对象的同一入口 pubilc static 类名 getInstance(){}
 */
public class MyImageLoader {

    private Context context;
    private static RequestQueue queue;
    private static ImageLoader loader;

    // 1. 对象
    private static MyImageLoader myLoader;

    // 2. 私有化构造方法
    private MyImageLoader(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        loader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            int size = (int) (Runtime.getRuntime().totalMemory()/8);
            private LruCache<String,Bitmap> lruCache = new LruCache<String,Bitmap>(size){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    int ret = value.getRowBytes()* value.getHeight();
                    return ret;
                }
            };
            @Override
            public Bitmap getBitmap(String s) {

                return lruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lruCache.put(s,bitmap);
            }
        });

    }
    // 统一的访问入口: 目的是loader
    public static void createMyImageLoader(Context context){
        if(myLoader==null){
            myLoader=new MyImageLoader(context);
        }
    }
    public static RequestQueue getResquestQueue(){
        return  queue;
    }
    public static ImageLoader getLoader() {
        return loader;
    }

}
