package org.mobiletrain.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by liusihui on 2015/12/4.
 * 基础界面，包含activity中共有的特性
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();

    }
}
