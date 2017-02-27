package com.hxx.faceview.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * UI相关的工具类<b>
 */
public class UiUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context  上下文
     * @param dipValue dip的值
     * @return px的值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px的值
     * @return dip的值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp值换算成px
     *
     * @param context 上下文
     * @param spValue sp的值
     * @return px的值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context 上下文
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return sp的值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将dip转为pix，并且根据屏幕尺寸进行了加权修正
     *
     * @param activity 上下文
     * @param dpValue  dpValue
     * @param isWidth  true表示该尺寸是屏幕较长的一边的方向上。
     * @return 值
     */
    public static int dip2pxWithAdjust(Activity activity, float dpValue, boolean isWidth) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float scale = dm.density;
        float ret = (dpValue * scale + 0.5f);
        if (isWidth == (dm.widthPixels > dm.heightPixels)) {
            return (int) (ret / adapterFunWidth(dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels, 2.3f));
        } else {
            return (int) (ret / adapterHeight(dm.widthPixels < dm.heightPixels ? dm.widthPixels : dm.heightPixels, 2.3f));
        }
    }

    private static float adapterFunWidth(float x, float p) {
        return (float) ((1.0 - p) / 3097600 * (x - 800) * (x - 800) + p);
    }

    private static float adapterHeight(float y, float p) {
        return (float) ((1.0 - p) / 1254400 * (y - 480) * (y - 480) + p);
    }

    /**
     * 给view设置背景
     *
     * @param view     要设置背景的View视图
     * @param drawable 背景图案
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void setViewBackgroundDrawable(View view, Drawable drawable) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    /**
     * 判断键盘是否打开，若打开就关闭
     *
     * @param view 视图
     */
    public static void closedImsActive(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 打开键盘
     *
     * @param view 视图
     */
    public static void openImsActive(final View view) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(view, 0);
            }

        }, 100);
    }

    /**
     * 显示Toast信息提示
     *
     * @param context 上下文环境
     * @param msg     提示信息
     */
    public static void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 控件在顶部显示
     *
     * @param context 上下文
     * @param view    控件
     */
    public static void showSlidAnim(Context context, final View view, int anim) {
        view.post(new Runnable() {

            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        view.startAnimation(animation);
    }

    /**
     * 控件在底部隐藏
     *
     * @param context 上下文
     * @param view    控件
     */
    public static void hideSlidAnim(Context context, final View view, int anim) {
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        view.startAnimation(animation);
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        }, 500);
    }

    /**
     * 设置ListView的高度
     *
     * @param listView ListView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getStatusBarHeight(Context context, int offset) {
        int result = 80;
        return result + offset;
    }

    /**
     * 改变Window的透明度
     *
     * @param value   透明度比例
     * @param context 上下文
     */
    public static void changeWindowAlpha(float value, Activity context) {
        WindowManager.LayoutParams attributes = context.getWindow().getAttributes();
        attributes.alpha = value;
        context.getWindow().setAttributes(attributes);
    }

}
