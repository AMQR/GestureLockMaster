package com.am.gesturelockmaster.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.am.gesturelocklib.widget.GestureLockView;
import com.am.gesturelockmaster.BaseActivity;
import com.am.gesturelockmaster.R;
import com.am.gesturelockmaster.utils.CacheUtils;
import com.am.gesturelockmaster.utils.MyConst;

/**
 * Created by AM on 2016/4/29.
 */
public class DelGesPwdActivity extends BaseActivity {


    private TextView mTvTip;
    private GestureLockView mGlvSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ges_pwd);
        initView();
        initGesLock();
    }


    private void initView() {
        mTvTip = (TextView) findViewById(R.id.mTvTip);
        mGlvSet = (GestureLockView) findViewById(R.id.mGlvSet);
    }

    private void initGesLock() {
        mGlvSet.setOnGestureFinishListener(new GestureLockView.OnGestureFinishListener() {
            @Override
            public void OnGestureFinish(boolean success, String gestureCode) {
                String liveGesPwd = CacheUtils.getString(DelGesPwdActivity.this,MyConst.GESTRUE_PWD_KEY,"no");
                if(gestureCode.equals(liveGesPwd)){
                    Toast.makeText(DelGesPwdActivity.this, "验证通过，密码已经删除", Toast.LENGTH_SHORT).show();
                    CacheUtils.setBoolean(DelGesPwdActivity.this,MyConst.GESTRUE_IS_LIVE,false);
                    finish();
                }else{
                    Toast.makeText(DelGesPwdActivity.this, "密码错误，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
