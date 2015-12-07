package org.mobiletrain.myapp.utils;

import android.util.Log;

import org.mobiletrain.myapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by liusihui on 15-12-4.
 */
public class CurrentTimeUtil {

    public static int[] monthPic= {R.mipmap.month_12,R.mipmap.month_11,R.mipmap.month_10,R.mipmap.month_9,
            R.mipmap.month_8,R.mipmap.month_7,R.mipmap.month_6,R.mipmap.month_5,R.mipmap.month_4,
            R.mipmap.month_3,R.mipmap.month_2,R.mipmap.month_1};

    // 临时list
    public static List<Integer> dayPic=Arrays.asList(R.mipmap.day_31,
            R.mipmap.day_30,R.mipmap.day_29,R.mipmap.day_28,R.mipmap.day_27,R.mipmap.day_26,
            R.mipmap.day_25,R.mipmap.day_24,R.mipmap.day_23,R.mipmap.day_22,R.mipmap.day_21,
            R.mipmap.day_20, R.mipmap.day_19,R.mipmap.day_18,R.mipmap.day_17,R.mipmap.day_16,
            R.mipmap.day_15,R.mipmap.day_14,R.mipmap.day_13,R.mipmap.day_12,R.mipmap.day_11,
            R.mipmap.day_10, R.mipmap.day_9,R.mipmap.day_8,R.mipmap.day_7,R.mipmap.day_6,
            R.mipmap.day_5,R.mipmap.day_4,R.mipmap.dau_3,R.mipmap.day_2,R.mipmap.day_1);

    public static List<Integer> listPicture= new ArrayList<>();

    // 临时list
    public static int[] weekPic={R.mipmap.week_6,R.mipmap.week_5,R.mipmap.week_4,R.mipmap.week_3,
            R.mipmap.week_2,R.mipmap.week_1,R.mipmap.week_7};

    public static int[] months={12,11,10,9,8,7,6,5,4,3,2,1};
    public static List<Integer> days=Arrays.asList(31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,
    14,13,12,11,10,9,8,7,6,5,4,3,2,1);

    public static List<Integer> listDays= new ArrayList<>();

    public static int[] weeks={7,6,5,4,3,2,1};

    // 获取系统当前时间 并与图片的位置相对应
    public static int[] getCurrentDate() {

        int[] currentDate=new int[4];

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mYear=c.get(Calendar.YEAR);   // 年
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        int mWay =c.get(Calendar.DAY_OF_WEEK); // 星期几

        Log.i("info","currentDate--------"+mYear+" --- "+mMonth+" --- "+mDay+" --- "+ mWay);

        currentDate[0]=mYear;
        currentDate[1]=mMonth;
        currentDate[2]=mDay;
        currentDate[3]=mWay;



        return currentDate;

     }

    // 当前时间与图片位置相对应
    public static int[] getCurrentDatePic(int mYear,int mMonth,int mDay,int mWay ) {

        listPicture.clear();
        listDays.clear();
        listPicture.addAll(dayPic);
        listDays.addAll(days);

        computeDateChange(mYear,mMonth);

        int [] currentIdPosition=new int[3];

        // 月
        for (int i = 0; i <months.length ; i++) {
            if(months[i]==mMonth){
                currentIdPosition[0]=i;

            }
        }
        // 日
        for (int i = 0; i <listDays.size() ; i++) {
            if(listDays.get(i)==mDay){
                currentIdPosition[1]=i;
            }
        }
        // 星期
        for (int i = 0; i <weeks.length ; i++) {
            if(weeks[i]==mWay){
                currentIdPosition[2]=i;
            }
        }
        return currentIdPosition;
    }

    // 计算月份的天数，与对应的图片id
    private static void computeDateChange(int year,int month){


        // 计算2月有多少天
        int month2=0;
        if(year%400==0){
            month2=29;
        }else if (year%4==0){
            month2=29;
        }else {
            month2=28;
        }
        // 每个月的天数
        if(month==4||month==6||month==9||month==11){


            listPicture.remove(0);
            listDays.remove(0);
        }
        else {
            listPicture.add(0,31);
            listDays.add(0,R.mipmap.day_31);
        }
        if(month==2){

            if(month2==29){
                listPicture.remove(0);
                listPicture.remove(1);
                listDays.remove(0);
                listDays.remove(1);
            }
            else {
                listPicture.add(0,31);
                listPicture.add(1,30);
                listDays.add(0,R.mipmap.day_31);
                listDays.add(1,R.mipmap.day_30);

            }
            if(month2==28){
                listPicture.remove(0);
                listPicture.remove(1);
                listPicture.remove(2);
                listDays.remove(0);
                listDays.remove(1);
                listDays.remove(2);
            }
            else {
                listPicture.add(0,31);
                listPicture.add(1,30);
                listPicture.add(2,29);
                listDays.add(0,R.mipmap.day_31);
                listDays.add(1,R.mipmap.day_30);
                listDays.add(2,R.mipmap.day_29);
            }

        }


    }

}
