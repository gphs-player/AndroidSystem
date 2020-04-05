package com.leo.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        List<String> mList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            mList.add(String.valueOf(i));
        }
        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

        mRecyclerView.addItemDecoration(decoration);
        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager = new GridLayoutManager(this,3);
//        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new RAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
    }


    static class RAdapter extends RecyclerView.Adapter<RHolder>{

        private List<String> mDataSet;

        private Context mCx;
        public RAdapter(Context context,List<String> mDataSet) {
            this.mDataSet = mDataSet;
            mCx =context;
        }

        @NonNull
        @Override
        public RHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(mCx).inflate(R.layout.item_text, viewGroup, false);
            RHolder rHolder = new RHolder(view);
            return rHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RHolder viewHolder, int i) {
            String s = mDataSet.get(i);
            viewHolder.mItemText.setText(s);
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }

    static class RHolder extends RecyclerView.ViewHolder{


        TextView mItemText;
        public RHolder(@NonNull View itemView) {
            super(itemView);
            mItemText = itemView.findViewById(R.id.item_text);
        }
    }
}
