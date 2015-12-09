package org.mobiletrain.myapp.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.mobiletrain.myapp.R;
import org.mobiletrain.myapp.UserActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.sso.UMQQSsoHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mobiletrain.myapp.interfaces.GetBitmap;
import org.mobiletrain.myapp.users.AboutsActivity;
import org.mobiletrain.myapp.users.AdvicesActivity;
import org.mobiletrain.myapp.users.FavouriteActivity;
import org.mobiletrain.myapp.users.FontsActivity;
import org.mobiletrain.myapp.utils.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    private ListView listView;
    private TextView user;

    private ImageView imageView;
    private ImageView login;
    private boolean b = true;
    private UMSocialService mController;

    private UMQQSsoHandler qqSsoHandler;
    private SharedPreferences preferences;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!("".equals(preferences.getString("name", ""))&&"".equals(preferences.getString("image", "")))){
            String name = preferences.getString("name", "");
            String image = preferences.getString("image", "");
            user.setText(name);
            Log.i("image","image"+image);
            HttpUtils.getImageRequest(image, new GetBitmap() {
                @Override
                public void forGetBitmap(Bitmap bm) {
                    login.setImageBitmap(getRoundedCornerBitmap(bm));
                }
            }, 70, 70);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_user, null);
        user = (TextView) view.findViewById(R.id.user_font);
        login = (ImageView) view.findViewById(R.id.login);
        listView = (ListView) view.findViewById(R.id.lv_user);
        imageView = (ImageView) view.findViewById(R.id.whiteLine);

        preferences = getActivity().getSharedPreferences("my", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("num", "0");
        edit.commit();


        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        qqSsoHandler = new UMQQSsoHandler(getActivity(), "1104971546",
                "ttA1QZdVfpeylKDe");
        qqSsoHandler.addToSocialSDK();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("退出登录").setMessage("退出登录后就不能多端同步了诶").setPositiveButton("退出",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mController.deleteOauth(getActivity(), SHARE_MEDIA.SINA,
                        new SocializeListeners.SocializeClientListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onComplete(int status, SocializeEntity entity) {
                                Log.i("status","status"+status);
                                if (status == 200) {


                                    SharedPreferences.Editor edit = preferences.edit();
                                    edit.remove("name");
                                    edit.remove("image");
                                    edit.putString("num", "0");
                                    edit.commit();
                                    user.setText("点击使用QQ登录");
                                    login.setImageResource(R.mipmap.avator_login);
                                    Toast.makeText(getActivity(), "退出成功.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), "退出失败,请重试！",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = preferences.getString("num", "");
                if ("0".equals(num)){
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("num","1");
                    edit.commit();
                    startActivity(new Intent(getActivity(), UserActivity.class));

                }
                else if ("1".equals(num)){
                    Toast.makeText(getActivity(),"您已经登录！",Toast.LENGTH_SHORT).show();
                    builder.create().show();
                }
            }
        });
        initView();
        return view;
    }



    private void initView() {

        int[] favourites = {R.mipmap.setting_favorite, R.mipmap.setting_font, R.mipmap.setting_aboutus, R.mipmap.setting_feedback};
        String[] fonts = {"我的收藏", "字体设置", "关于十个", "意见反馈"};
        int[] arrows = {R.mipmap.setting_arrow, R.mipmap.setting_arrow, R.mipmap.setting_arrow, R.mipmap.setting_arrow};
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", favourites[i]);
            map.put("font", fonts[i]);
            map.put("arrow", arrows[i]);
            list.add(map);
        }


        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.user_style,
                new String[]{"image", "font", "arrow"}, new int[]{R.id.favorite_image, R.id.favorite_wenzi, R.id.setting_arrow});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent();
                switch (position) {
                    case 0:
                        in.setClass(getActivity(), FavouriteActivity.class);


                        break;
                    case 1:
                        in.setClass(getActivity(), FontsActivity.class);

                        break;
                    case 2:
                        in.setClass(getActivity(), AboutsActivity.class);

                        break;
                    case 3:
                        in.setClass(getActivity(), AdvicesActivity.class);
                        break;
                }
                startActivity(in);
            }
        });


    }



    /* 圆形头像
         *
         * @param bitmap
         * @param ratio
         *            按照截取比例来获取圆形图片
         * @return
         */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);//Bitmap.Config.ARGB_4444比Bitmap.Config.ARGB_8888更省内存
        Canvas canvas = new Canvas(outBitmap);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPX = bitmap.getWidth() / 2 < bitmap.getHeight() / 2 ? bitmap
                .getWidth() : bitmap.getHeight();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBitmap;
    }



}


