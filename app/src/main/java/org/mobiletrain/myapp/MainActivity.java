package org.mobiletrain.myapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.mobiletrain.myapp.fragments.MoviesFragment;
import org.mobiletrain.myapp.fragments.PictureFragment;
import org.mobiletrain.myapp.fragments.TextFragment;
import org.mobiletrain.myapp.fragments.UserFragment;


public class MainActivity extends BaseActivity {

    private RadioGroup main_tabBar;
    private Fragment fragment ,moviesFragment,pictureFragment,textFragment,userFragment;
    private FragmentManager manager;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.umeng.socialize.utils.Log.LOG = true;
        moviesFragment = new MoviesFragment();
        pictureFragment = new PictureFragment();
        textFragment = new TextFragment();
        userFragment = new UserFragment();


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container,moviesFragment,"moviesFragment");
        transaction.add(R.id.container,pictureFragment,"pictureFragment");
        transaction.add(R.id.container,textFragment,"textFragment");
        transaction.add(R.id.container,userFragment,"userFragment");
        transaction.hide(pictureFragment);
        transaction.hide(textFragment);
        transaction.hide(userFragment);
        transaction.show(moviesFragment);
        transaction.commit();


        initView();


    }
        private void initView() {
        main_tabBar = (RadioGroup) findViewById(R.id.main_rg);
        main_tabBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();


                switch (checkedId) {
                    case R.id.get_movices:
                        if (moviesFragment.isAdded()){
                            fragment = manager.findFragmentByTag("moviesFragment");
                        }
                        else {
                            transaction.add(R.id.container, new MoviesFragment(), "moviesFragment");
                        }
                        transaction.hide(userFragment);
                        transaction.hide(pictureFragment);
                        transaction.hide(textFragment);
                        break;
                    case R.id.get_pictures :
                        if(pictureFragment.isAdded()){
                            fragment = manager.findFragmentByTag("pictureFragment");}

                        else {
                            transaction.add(R.id.container, new PictureFragment(), "pictureFragment");
                        }
                            transaction.hide(moviesFragment);
                            transaction.hide(textFragment);
                            transaction.hide(userFragment);






                        break;
                    case R.id.get_texts:
                        if (textFragment.isAdded()){
                            fragment = manager.findFragmentByTag("textFragment");


                        }
                        else {
                            transaction.add(R.id.container, new TextFragment(), "textFragment");
                        }
                        transaction.hide(pictureFragment);
                        transaction.hide(moviesFragment);
                        transaction.hide(userFragment);



                        break;
                    case R.id.get_users:
                        if (userFragment.isAdded()){
                            fragment = manager.findFragmentByTag("userFragment");
                        }
                        else {
                            transaction.add(R.id.container,new UserFragment(),"userFragment");
                        }
                        transaction.hide(pictureFragment);
                        transaction.hide(textFragment);
                        transaction.hide(moviesFragment);


                        break;
                }

               transaction.show(fragment);
                transaction.commit();

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            Toast.makeText(MainActivity.this, "再按一次退出十个", Toast.LENGTH_SHORT).show();
            exit();
            return false;}
        return super.onKeyDown(keyCode,event);
    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出十个",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


}



