package com.leo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class LayoutInflateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflate);
        FrameLayout root = findViewById(R.id.main);

        //1.OK 返回的是FrameLayout
//        View view = View.inflate(this, R.layout.layout_inflate, root);
//        System.out.println(view);
        //2.The specified child already has a parent. You must call removeView() on the child's parent first.
//        View view = View.inflate(this, R.layout.layout_inflate, root);
//        System.out.println(view);
//        root.addView(view);


        //3.OK,二者一样 返回值是父布局root
        View view = LayoutInflater.from(this).inflate(R.layout.layout_inflate, null,false);
//        LayoutInflater.from(this).inflate(R.layout.layout_inflate, root,true);

        //4.不报错 返回的是子View，需要手动添加
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_inflate, root, false);
        root.addView(view);
        System.out.println(view);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
