package org.mobiletrain.myapp.users;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import org.mobiletrain.myapp.BaseActivity;
import org.mobiletrain.myapp.R;

import org.mobiletrain.myapp.unite.UniteApplication;
/**
 * 字体设置界面
 *  Created by liusihui on 2015/12/7.
 */
public class FontsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts);
        initView();
    }

    private void initView() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.fonts_to_changes);
        final TextView small = (TextView) findViewById(R.id.font_small);
        final TextView medium = (TextView) findViewById(R.id.font_medium);
        final TextView large = (TextView) findViewById(R.id.font_large);
        final TextView font_cahnges = (TextView) findViewById(R.id.fonts_changes);
        final TextView qingren = (TextView) findViewById(R.id.qingren);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.eye_bad:

                        UniteApplication.FontSize = 15.0f;
                        font_cahnges.setTextSize(15.0f);
                        qingren.setTextSize(15.0f);
                        small.setTextColor(Color.rgb(236, 80, 77));
                        medium.setTextColor(Color.rgb(0, 0, 0));
                        large.setTextColor(Color.rgb(0, 0, 0));
                        break;
                    case R.id.eye_medium:
                        UniteApplication.FontSize = 17.0f;
                        font_cahnges.setTextSize(17.0f);
                        qingren.setTextSize(17.0f);
                        small.setTextColor(Color.rgb(0, 0, 0));
                        medium.setTextColor(Color.rgb(236, 80, 77));
                        large.setTextColor(Color.rgb(0, 0, 0));
                        break;
                    case R.id.eye_large:
                        UniteApplication.FontSize = 19.0f;
                        font_cahnges.setTextSize(19.0f);
                        qingren.setTextSize(19.0f);
                        small.setTextColor(Color.rgb(0, 0, 0));
                        medium.setTextColor(Color.rgb(0, 0, 0));
                        large.setTextColor(Color.rgb(236, 80, 77));
                        break;
                }
            }
        });
    }

    public void fontsBack(View view) {

        finish();
    }
}
