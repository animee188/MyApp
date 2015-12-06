package org.mobiletrain.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * create by liusihui on 2015/12/4.
 * 欢迎界面，有图片移动效果的
 * */

public class WelcomeActivity extends BaseActivity {

    private ImageView image,icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        image = (ImageView) findViewById(R.id.image_guide);
        icon = (ImageView) findViewById(R.id.icon);
        setPosition();      //设置图片放置的位置和图片的宽高
        //设置动画效果
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome);
        image.startAnimation(animation);
        //动画的监听事件
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    //设置图片放置的位置和图片的宽高
    private void setPosition() {
        //获得手机屏幕的高度
        int heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        int widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        Log.i("info", "-------height---------" + heightPixels + "------width------" + widthPixels);
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(icon.getLayoutParams());
        margin.setMargins(widthPixels/3, heightPixels/3, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        layoutParams.height = heightPixels/9;                   //设置图片的高度
        layoutParams.width = widthPixels/3;                     //设置图片的宽度
        icon.setLayoutParams(layoutParams);
    }
}
