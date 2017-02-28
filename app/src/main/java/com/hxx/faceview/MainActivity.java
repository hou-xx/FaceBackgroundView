package com.hxx.faceview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.hxx.faceview.view.FaceBackgroundView;

public class MainActivity extends Activity implements View.OnClickListener {
    private FaceBackgroundView mFaceBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mFaceBg = (FaceBackgroundView) findViewById(R.id.face_bg_view);
        findViewById(R.id.btn_scanning).setOnClickListener(this);
        findViewById(R.id.btn_scan_success).setOnClickListener(this);
        findViewById(R.id.btn_scan_fail).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scanning:
                mFaceBg.changeResultState(FaceBackgroundView.ResultStatus.SCANNING,Color.WHITE);
                break;
            case R.id.btn_scan_success:
                mFaceBg.changeResultState(FaceBackgroundView.ResultStatus.SUCCESS,Color.GREEN);
                break;
            case R.id.btn_scan_fail:
                mFaceBg.changeResultState(FaceBackgroundView.ResultStatus.FAIL,Color.RED);
                break;
        }

    }
}
