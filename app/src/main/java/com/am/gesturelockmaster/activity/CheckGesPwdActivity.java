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
public class CheckGesPwdActivity extends Activity{

    private TextView mTvTip;
    private GestureLockView mGlvSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ges_pwd);
        initView();
        initGesLock();

    }



    private void initView() {
        mTvTip = (TextView) findViewById(R.id.mTvTip);
        mGlvSet = (GestureLockView) findViewById(R.id.mGlvSet);
    }

    private void initGesLock() {

        // 把之前设置好存起来的手势密码设置为当前页面的密码的key
        // 九个点分别对应着数字的  012345678  第一个点即为0，最后一个点为8，横向数起


        // 这里去出来的密码肯定是正常的，不会是 no
        mGlvSet.setKey(CacheUtils.getString(CheckGesPwdActivity.this, MyConst.GESTRUE_PWD_KEY,"no"));
        mGlvSet.setOnGestureFinishListener(new GestureLockView.OnGestureFinishListener() {
            @Override
            public void OnGestureFinish(boolean success, String gestureCode) {
                if(success){
                    startActivity(new Intent(CheckGesPwdActivity.this,LoginActivity.class));
                    Toast.makeText(CheckGesPwdActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                    CacheUtils.setBoolean(CheckGesPwdActivity.this,MyConst.GESTRUE_HAS_INPUT_PWD,true);
                    finish();
                }else{
                    Toast.makeText(CheckGesPwdActivity.this,"密码错误，错误10次地球将来10分钟后毁灭！！！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
