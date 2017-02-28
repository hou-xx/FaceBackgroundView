package com.hxx.faceview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * <ul>
 * <li>功能说明：自定义View 绘制人脸登录扫描背景</li>
 * <li>作者：tal on 2017/2/27 0027 15:22 </li>
 * <li>邮箱：hou_xiangxiang@126.com</li>
 * </ul>
 */

public class FaceBackgroundView extends View {
    private final static String TAG = "FaceBackgroundView";
    private Paint mLinePaint;
    private Paint mFieldPaint;
    private Paint mBitmapPaint;
    private Paint mBgPaint;
    private Path mFacePath;
    private Path mBgPath;
    private int defaultDashedColor = Color.WHITE;
    private int defaultDashedLength = 20;
    private int mHeight;
    private int mWidth;
    private ResultStatus mStatus = ResultStatus.SCANNING;
    private Bitmap mBitmap;
    private Rect mFieldDst;

    public FaceBackgroundView(Context context) {
        super(context);
        onFinishInflate();
    }

    public FaceBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mWidth = wm.getDefaultDisplay().getWidth();
        mHeight = wm.getDefaultDisplay().getHeight();
        initPath();
        initPaint();
        initImageField();
        Log.e(TAG, "mHeight-->" + mHeight + "    mWidth-->" + mWidth);
    }

    private void initImageField() {
        mFieldDst = new Rect(mWidth / 3, mWidth / 2, mWidth * 2 / 3, mWidth * 5 / 6);
    }

    public void changeResultState(ResultStatus status) {
        this.mStatus = status;
        invalidate();
    }

    public void changeResultState(ResultStatus status, int dashedColor) {
        mLinePaint.setColor(dashedColor);
        this.mStatus = status;
        invalidate();
    }

    public void changeResultState(ResultStatus status, int dashedColor, int imgResId) {
        mLinePaint.setColor(dashedColor);
        this.mStatus = status;
        mBitmap = BitmapFactory.decodeResource(getResources(), imgResId);
        invalidate();
    }

    public void changeDashedColor(int color) {
        mLinePaint.setColor(color);
        invalidate();
    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setColor(defaultDashedColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(defaultDashedLength);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{defaultDashedLength, defaultDashedLength}, 0));

        mFieldPaint = new Paint();
        mFieldPaint.setColor(Color.TRANSPARENT);
        mFieldPaint.setStyle(Paint.Style.FILL);
        mFieldPaint.setStrokeWidth(5);

        mBgPaint = new Paint();
        mBgPaint.setColor(Color.BLACK);
        mBgPaint.setStyle(Paint.Style.FILL);

        mBitmapPaint = new Paint();
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
        if (mStatus == ResultStatus.SUCCESS || mStatus == ResultStatus.FAIL) {
            if (mBitmap == null) {
                canvas.drawPath(mFacePath, mFieldPaint);
            } else {
                canvas.drawBitmap(mBitmap, null, mFieldDst, mBitmapPaint);
            }
        } else if (mStatus == ResultStatus.SCANNING) {
            canvas.drawPath(mFacePath, mFieldPaint);
        }
        //画背景
        canvas.clipPath(mFacePath, Region.Op.DIFFERENCE);
        canvas.drawPath(mBgPath, mBgPaint);
    }

    public enum ResultStatus {
        SUCCESS,
        FAIL,
        SCANNING
    }
}
