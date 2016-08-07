package com.am.gesturelockmaster;

import android.app.Application;
import android.content.Intent;

import com.am.gesturelockmaster.activity.CheckGesPwdActivity;
import com.am.gesturelockmaster.utils.CacheUtils;
import com.am.gesturelockmaster.utils.L;
import com.am.gesturelockmaster.utils.MyConst;

/**
 * User: LJM
 * Date&Time: 2016-08-07 & 22:38
 * Describe: Describe Text
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ForegroundCallbacks.init(this);

        ForegroundCallbacks.get().addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                L.d("当前程序切换到前台");
                if(CacheUtils.getBoolean(getApplicationContext(), MyConst.GESTRUE_IS_LIVE)){
                    L.d("已经开启手势锁");
                    Intent intent = new Intent(getApplicationContext(), CheckGesPwdActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{

                }

            }

            @Override
            public void onBecameBackground() {
                L.d("当前程序切换到后台");
            }
        });

    }





}
