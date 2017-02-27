package com.hxx.faceview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * <ul>
 * <li>功能说明：</li>
 * <li>作者：tal on 2017/2/27 0027 15:22 </li>
 * <li>邮箱：houxiangxiang@cibfintech.com</li>
 * </ul>
 */

public class FaceView extends View {
    private Paint mLinePaint;
    private Paint mFieldPaint;
    private Paint mBgPaint;
    private Path mFacePath;
    private Path mBgPath;
    private int defaultColor = Color.GREEN;
    private int mHeight;
    private int mWidth;

    public FaceView(Context context) {
        super(context);
        onFinishInflate();
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        mHeight = height;
        mWidth = width;
        initPath();
        initPaint();
        Log.e("111", "mHeight-->" + mHeight + "    mWidth-->" + mWidth);

    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(20);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{20, 20}, 0));

        mFieldPaint = new Paint();
        mFieldPaint.setColor(Color.TRANSPARENT);
        mFieldPaint.setStyle(Paint.Style.FILL);
        mFieldPaint.setStrokeWidth(5);

        mBgPaint = new Paint();
        mBgPaint.setColor(Color.BLACK);
        mBgPaint.setStyle(Paint.Style.FILL);
    }

    private void initPath() {
        //面部区域
        mFacePath = new Path();
        mFacePath.moveTo(mWidth / 8, mHeight / 3);
        mFacePath.cubicTo(mWidth / 4, mHeight * 4 / 5, mWidth * 3 / 4, mHeight * 4 / 5, mWidth * 7 / 8, mHeight / 3);
        //画path
        mFacePath.cubicTo(mWidth * 4 / 5, mHeight / 8, mWidth / 5, mHeight / 8, mWidth / 8, mHeight / 3);
        mFacePath.close();
        //背景区域
        mBgPath = new Path();
        mBgPath.moveTo(0, 0);
        mBgPath.addRect(0, 0, mWidth, mHeight, Path.Direction.CCW);
        mBgPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画虚线
        canvas.drawPath(mFacePath, mLinePaint);
        //画透明区域
        canvas.drawPath(mFacePath, mFieldPaint);
        //画背景
        canvas.clipPath(mFacePath, Region.Op.DIFFERENCE);
        canvas.drawPath(mBgPath, mBgPaint);
    }
}
