package org.mobiletrain.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
/**
 * create by liusihui on 2015/12/4.
 * 引导界面，如果是第一次进入就跳转到欢迎界面，如果已经进入过就跳转到主页面
 * */
public class GuideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Intent in = new Intent();
        //保存状态
        SharedPreferences joke = getSharedPreferences("joke", MODE_PRIVATE);
        SharedPreferences.Editor edit = joke.edit();
        String jokeString = joke.getString("once", "no");
        if (!jokeString .equals("no")){
            in.setClass(this, MainActivity.class);
            startActivity(in);


        }
        else{
            in.setClass(this,WelcomeActivity.class);
            edit.putString("once", "yes");
            edit.commit();
            startActivity(in);
        }

        finish();


    }
}
