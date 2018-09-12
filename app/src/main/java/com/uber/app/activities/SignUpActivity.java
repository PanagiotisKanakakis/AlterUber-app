package com.uber.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.uber.app.R;
import com.uber.app.fragments.SignUpCarFragment;
import com.uber.app.fragments.SignUpPersonFragment;

public class SignUpActivity extends FragmentActivity implements SignUpPersonFragment.FragmentChangeInterface {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new SignUpPersonFragment());
        ft.commitNow();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    @Override
    public void changeChildFragment() {
        setContentView(R.layout.activity_signup);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new SignUpCarFragment());
        ft.commitNow();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
}