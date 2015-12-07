package org.mobiletrain.myapp.users;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import org.mobiletrain.myapp.BaseActivity;
import org.mobiletrain.myapp.R;
/**
 * 意见反馈界面
 *  Created by liusihui on 2015/12/7.
 */
public class AdvicesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advices);
    }

    public void advicesBack(View view) {
        finish();
    }

    public void shigenten(View view) {
        Toast.makeText(this,"已为您复制"+"\""+"shigeten"+"\""+"到剪切板",Toast.LENGTH_SHORT).show();
    }

    public void qq(View view) {
        Toast.makeText(this,"已为您复制"+"\""+"551608331"+"\""+"到剪切板",Toast.LENGTH_SHORT).show();
    }
}
