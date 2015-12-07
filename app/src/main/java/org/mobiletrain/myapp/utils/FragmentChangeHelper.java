package org.mobiletrain.myapp.utils;

/**
 * Created by liusihui on 2015/12/5.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 帮助Fragment跳转类，可以携带跳转信息，需不需要加入回退栈，需不需要清空回退栈
 */
public class FragmentChangeHelper {

    //目标Fragment
    private Fragment targetFragment;
    //添加标记
    private String tag;
    //是否加入回退栈
    private String fragmentTag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    //是否清空回退栈
    private boolean isClearBackStack;

    //Bundle,跳转携带的信息
    private Bundle bundle;

    public Fragment getTargetFragment() {
        return targetFragment;
    }

    public void setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    public boolean isClearBackStack() {
        return isClearBackStack;
    }

    public void setIsClearBackStack(boolean isClearBackStack) {
        this.isClearBackStack = isClearBackStack;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
