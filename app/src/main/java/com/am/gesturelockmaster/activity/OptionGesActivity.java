package com.am.gesturelockmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.am.gesturelockmaster.BaseActivity;
import com.am.gesturelockmaster.R;

/**
 * Created by AM on 2016/4/29.
 */
public class OptionGesActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvSetGes;
    private TextView mTvCleanGes;
    private String appGesKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_ges);
        initView();
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
                startActivity(new Intent(OptionGesActivity.this,SetGesPwdActivity.class));

                break;

            case R.id.mTvCleanGes:
                startActivity(new Intent(OptionGesActivity.this,DelGesPwdActivity.class));
                break;
        }
    }
}
