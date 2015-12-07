package org.mobiletrain.myapp.users;

import android.os.Bundle;
import android.view.View;

import org.mobiletrain.myapp.BaseActivity;
import org.mobiletrain.myapp.R;
/**
 *  我的收藏界面
 *  Created by liusihui on 2015/12/7.
 */
public class FavouriteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
    }

    public void favouriteBack(View view) {

        finish();
    }
}
