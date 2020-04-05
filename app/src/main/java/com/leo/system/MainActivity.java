package com.leo.system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.leo.ui.ConstraintLayoutActivity;
import com.leo.ui.LayoutInflateActivity;
import com.leo.ui.RecyclerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void constraint(View view) {
        Intent intent = new Intent(this, ConstraintLayoutActivity.class);
        startActivity(intent);
    }

    public void recycler(View view) {
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
    }

    public void inflate(View view) {
        Intent intent = new Intent(this, LayoutInflateActivity.class);
        startActivity(intent);
    }
}
