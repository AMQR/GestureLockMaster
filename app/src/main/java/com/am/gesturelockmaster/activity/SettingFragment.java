package com.am.gesturelockmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.am.gesturelockmaster.R;

/**
 * User: LJM
 * Date&Time: 2016-08-07 & 20:43
 * Describe:
 */
public class SettingFragment extends Fragment{

    private TextView mTvOpenSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgrament_setting,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTvOpenSetting  = (TextView) getView().findViewById(R.id.mTvOpenSetting);
        mTvOpenSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),OptionGesActivity.class));
            }
        });

    }
}
