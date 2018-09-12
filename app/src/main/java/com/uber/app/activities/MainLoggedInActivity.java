package com.uber.app.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.uber.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainLoggedInActivity extends AppCompatActivity {


    @BindView(R.id.from)
    EditText from;
    @BindView(R.id.to)
    EditText to;
   // @BindView(R.id.add_stop)
   // ImageButton add_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged_in);
        ButterKnife.bind(this);

//        add_stop.setOnClickListener((View view) -> {
//
//            TextInputLayout textInputLayout = findViewById(R.id.text_to);
//
//            TextInputLayout newInput = new TextInputLayout(getApplicationContext(), (AttributeSet) textInputLayout);
//            ArrayList views = new ArrayList();
//            views.add(newInput);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                findViewById(R.id.linearLayout).addChildrenForAccessibility(views);
//            }
//
//        });

    }

}
