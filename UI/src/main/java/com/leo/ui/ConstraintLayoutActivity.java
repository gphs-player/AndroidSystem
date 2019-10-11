package com.leo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ConstraintLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
        findViewById(R.id.marginLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View star = findViewById(R.id.star);
                findViewById(R.id.star).setVisibility(star.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
    }
}
