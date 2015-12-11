package org.mobiletrain.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMQQSsoHandler;

import java.util.Map;
import java.util.Set;

public class UserActivity extends BaseActivity {
    private UMSocialService mController;
    private String image, name;
    private UMQQSsoHandler qqSsoHandler;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        preferences = getSharedPreferences("my", MODE_PRIVATE);
        edit = preferences.edit();
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        qqSsoHandler = new UMQQSsoHandler(UserActivity.this, "1105014910",
                "WSncAGsxsiNNrrbT");
        qqSsoHandler.addToSocialSDK();
    }


    public void loginQQ(View view) {
        toLogin();


    }


    private void toLogin() {

        mController.doOauthVerify(UserActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(UserActivity.this, "登录中...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(UserActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                Toast.makeText(UserActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
                //获取相关授权信息
                mController.getPlatformInfo(UserActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(UserActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for (String key : keys) {
                                if ("screen_name".equals(key)) {
                                    name = info.get(key).toString();
                                }
                                if ("profile_image_url".equals(key)) {
                                    image = info.get(key).toString();
                                }
                                sb.append(key + "=" + info.get(key).toString() + "\r\n");
                            }

                            Log.i("hehe", name + image);
                            if (name != null && name.length() != 0) {
                                edit.putString("name", name);
                                edit.putString("image", image);
                                edit.commit();
                            } else {
                                Toast.makeText(UserActivity.this, "登录无数据返回，请重新登录", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("TestData", "发生错误：" + status);
                        }
                        finish();
                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(UserActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            edit.putString("num","0");
            edit.commit();
            finish();}
        return super.onKeyDown(keyCode,event);
    }

    public void userBack(View view) {
        SharedPreferences preferences = getSharedPreferences("my", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("num","0");
        edit.commit();
        finish();
    }

}
