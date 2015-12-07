package org.mobiletrain.myapp.utils;

/**
 * Created by liusihui on 2015/12/4.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 这个类用来获取这款app的版本号
 */
public class PackageUtil {

    public static String getPackageVersion(Context context){

        String version = "1.0";

        //通过上下文获取Packagemanager
        PackageManager manager = context.getPackageManager();

        try {
            //通过manager获取package信息
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            //获取了app版本号
            version = packageInfo.versionName;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;

    }
}
