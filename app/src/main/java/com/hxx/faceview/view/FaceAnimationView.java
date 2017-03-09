package com.hxx.faceview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hxx.faceview.R;

/**
 * <ul>
 * <li>功能说明：</li>
 * <li>作者：tal on 2017/3/1 0001 12:17 </li>
 * <li>邮箱：houxiangxiang@cibfintech.com</li>
 * </ul>
 */

public class FaceAnimationView extends RelativeLayout {
    private int mHeight;
    private int mWidth;
    private ImageView mImageView;
    private boolean mHasStart;

    public FaceAnimationView(Context context) {
        super(context);
        init();
        onFinishInflate();
    }

    public FaceAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FaceAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mWidth = wm.getDefaultDisplay().getWidth();
        mHeight = wm.getDefaultDisplay().getHeight();
        mImageView = new ImageView(getContext());
        LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        mImageView.setLayoutParams(params);
//        mImageView.scrollTo(mWidth / 8, mHeight / 3);
        mImageView.setBackgroundResource(R.drawable.icon_animation);
        addView(mImageView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && !mHasStart) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    start();
                    mHasStart = true;
                }
            }, 500);
        }
    }

    public void start() {
        ViewPath pathOne = new ViewPath();
        pathOne.moveTo(mWidth / 8, mHeight / 3);
        pathOne.curveTo(mWidth / 4, mHeight * 4 / 5, mWidth * 3 / 4, mHeight * 4 / 5, mWidth * 7 / 8, mHeight / 3);
        ViewPath pathTwo = new ViewPath();
        pathTwo.moveTo(mWidth * 7 / 8, mHeight / 3);
        pathTwo.curveTo(mWidth * 4 / 5, mHeight / 8, mWidth / 5, mHeight / 8, mWidth / 8, mHeight / 3);
        setAnimation(mImageView, pathOne, pathTwo);
    }

    private void setAnimation(final ImageView target, ViewPath path, ViewPath pathTwo) {
        ObjectAnimator animatorOne = ObjectAnimator.ofObject(new ViewObj(target), "fabLoc", new ViewPathEvaluator(), path.getPoints().toArray());
        animatorOne.setInterpolator(new LinearInterpolator());
//        animatorOne.setRepeatCount(-1);
        ObjectAnimator animatorTwo = ObjectAnimator.ofObject(new ViewObj(target), "fabLoc", new ViewPathEvaluator(), pathTwo.getPoints().toArray());
        animatorTwo.setInterpolator(new LinearInterpolator());
//        animatorTwo.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(animatorOne).with(animatorTwo);
        animatorSet.playSequentially(animatorOne, animatorTwo);
        animatorSet.setDuration(8000);
        animatorSet.start();
//        animator.setDuration(8000);
//        animator.setRepeatCount(-1);
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.start();
//        //贝塞尔曲线
//        ObjectAnimator redAnim2 = ObjectAnimator.ofObject(new ViewObj(target), "fabLoc", new ViewPathEvaluator(), path2.getPoints().toArray());
//        redAnim2.setInterpolator(new AccelerateDecelerateInterpolator());

        //组合动画
//        addAnimation(animator, target);
    }

    private void addAnimation(ObjectAnimator animator1, ImageView target) {
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 1f, 0.5f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 1, getScale(target), 1.0f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 1, getScale(target), 1.0f);

        AnimatorSet all2 = new AnimatorSet();
        all2.setDuration(8000);
        all2.play(animator1);
//        all2.playTogether(alpha, scaleX, scaleY, animator1);
//        all2.addListener(new AnimEndListener(target));
        all2.start();
//
//        AnimatorSet all = new AnimatorSet();
//        all.playSequentially(animator1, all2);
//        all.start();
    }

    private float getScale(ImageView target) {
        if (target == mImageView)
            return 3.0f;
//        if (target == purple)
//            return 2.0f;
//        if (target == yellow)
//            return 4.5f;
//        if (target == blue)
//            return 3.5f;
        return 2f;

    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView((target));
        }
    }

    public class ViewObj {
        private final ImageView red;

        public ViewObj(ImageView red) {
            this.red = red;
        }

        public void setFabLoc(ViewPoint newLoc) {
            red.setTranslationX(newLoc.x);
            red.setTranslationY(newLoc.y);
        }
    }
}
