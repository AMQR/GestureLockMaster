package com.am.gesturelockmaster.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.am.gesturelocklib.widget.GestureLockView;
import com.am.gesturelockmaster.R;
import com.am.gesturelockmaster.utils.CacheUtils;
import com.am.gesturelockmaster.utils.MyConst;

/**
 * Created by AM on 2016/4/29.
 */
public class SetGesPwdActivity extends Activity {


    private TextView mTvTip;
    private GestureLockView mGlvSet;

    private String tempKey = null;
    private boolean hasInput = false;

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
                if (!hasInput) { // 如果当前还没有设置过第一次密码
                    tempKey = gestureCode;
                    hasInput = true;
                    mGlvSet.setUpDiyColor(true);
                    //Toast.makeText(SetGesPwdActivity.this,"已设置一次，请重新设置第二次密码",Toast.LENGTH_SHORT).show();
                    mTvTip.setText("已设置一次，请重新设置第二次密码");
                } else {// 如果输入过第一次密码，那么做比较
                    if (tempKey.equals(gestureCode)) {
                        mGlvSet.setUpDiyColor(true);
                        Toast.makeText(SetGesPwdActivity.this, "设置密码成功", Toast.LENGTH_SHORT).show();
                        // 把手势密码缓存起来
                        CacheUtils.setString(SetGesPwdActivity.this, MyConst.GESTRUE_PWD_KEY,gestureCode);
                        startActivity(new Intent(SetGesPwdActivity.this,CheckGesPwdActivity.class));
                        finish();
                    } else {
                        mGlvSet.setUpDiyColor(false);
                        //Toast.makeText(SetGesPwdActivity.this, "设置密码失败，请重新设置两次相同的密码", Toast.LENGTH_SHORT).show();
                        mTvTip.setText("两次密码不一致");
                        tempKey = "";
                    }
                    hasInput = false;
                }
            }
        });
    }



}
