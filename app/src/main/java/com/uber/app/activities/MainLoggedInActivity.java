package com.uber.app.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.uber.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainLoggedInActivity extends AppCompatActivity {


    @BindView(R.id.from)
    EditText from;
    @BindView(R.id.to)
    EditText to;
    @BindView(R.id.add_stop)
    ImageButton add_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged_in);
        ButterKnife.bind(this);

        add_stop.setOnClickListener((View view) -> {

            LinearLayout parentLinearLayout = findViewById(R.id.parent_layout);

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.stop, null);
            // Add the new row before the add field button.

            if(parentLinearLayout.getChildCount() < 3){
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

                ViewGroup layout = (ViewGroup) parentLinearLayout.getChildAt(parentLinearLayout.getChildCount() - 2);
                layout.removeView(findViewById(R.id.add_stop));
            }



        });

    }

}
