package org.mobiletrain.myapp.unite;

import android.app.Application;

import org.mobiletrain.myapp.utils.HttpUtils;
import org.mobiletrain.myapp.utils.MyImageLoader;

/**
 * Application
 * Created by liusihui on 2015/12/5.
 */
public class UniteApplication extends Application{
    public static float FontSize = 15.0F;

    @Override
    public void onCreate() {
        super.onCreate();
        MyImageLoader.createMyImageLoader(getApplicationContext());
        HttpUtils httpUtils=new HttpUtils(getApplicationContext());

    }

}
