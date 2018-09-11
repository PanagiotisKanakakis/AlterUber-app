package com.uber.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.uber.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpCarFragment extends Fragment {

    @Nullable
    @BindView(R.id.input_license)
    TextView input_license;
    @BindView(R.id.btn_signup_car)
    Button _signup_car;
    @BindView(R.id.link_login)
    TextView _loginLink;
    @BindView(R.id.imgView)
    ImageView _img;
    @BindView(R.id.car)
    BootstrapDropDown car_list;
    @BindView(R.id.model)
    BootstrapDropDown model_list;
    @BindView(R.id.year)
    BootstrapDropDown year_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View retView = inflater.inflate(R.layout.activity_signup_car, container,false);
        TypefaceProvider.registerDefaultIconSets();

        ButterKnife.bind(this,retView);
        return retView;
    }


}
