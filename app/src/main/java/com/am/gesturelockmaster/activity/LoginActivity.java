package com.am.gesturelockmaster.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.am.gesturelockmaster.R;
import com.am.gesturelockmaster.utils.CacheUtils;
import com.am.gesturelockmaster.utils.MyConst;

/**
 * Created by AM on 2016/4/29.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private TextView mTvSetGes;
    private TextView mTvCleanGes;
    private String appGesKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();

        boolean hasInput = CacheUtils.getBoolean(LoginActivity.this, MyConst.GESTRUE_HAS_INPUT_PWD);
        if(!hasInput){
            appGesKey = CacheUtils.getString(LoginActivity.this, MyConst.GESTRUE_PWD_KEY, "no");

            if(!appGesKey.equals("no")){ // 如果不等于no，就代表设置过手势密码
                startActivity(new Intent(LoginActivity.this,CheckGesPwdActivity.class));
            }
        }


    }

    private void initView() {
        mTvSetGes = (TextView) findViewById(R.id.mTvSetGes);
        mTvCleanGes = (TextView) findViewById(R.id.mTvCleanGes);

        mTvSetGes.setOnClickListener(this);
        mTvCleanGes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mTvSetGes:
                startActivity(new Intent(LoginActivity.this,SetGesPwdActivity.class));

                break;

            case R.id.mTvCleanGes:
                startActivity(new Intent(LoginActivity.this,CheckGesPwdActivity.class));
                break;
        }
    }
}
