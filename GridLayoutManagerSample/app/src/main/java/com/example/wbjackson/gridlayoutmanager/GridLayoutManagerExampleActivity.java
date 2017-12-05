package com.example.wbjackson.gridlayoutmanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wbjackson on 12/5/17.
 */

public class GridLayoutManagerExampleActivity extends AppCompatActivity {

    private RecyclerView mGrid;
    private GridAdapter mAdapter;
    private FloatingActionButton mAddItemBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout_manager);
        mGrid = findViewById(R.id.grid);
        mAddItemBtn = findViewById(R.id.add_item_btn);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mAdapter = new GridAdapter(this, 0);
        layoutManager.setSpanSizeLookup(new CustomSpanSizeLookup(mAdapter));
        mGrid.setLayoutManager(layoutManager);
        mGrid.setAdapter(mAdapter);
        mAddItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addItem();
            }
        });
    }

    private class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private RecyclerView.Adapter mAdapter;

        public CustomSpanSizeLookup(RecyclerView.Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getSpanSize(int position) {
            if (mAdapter.getItemCount() < 3) {
                return 2;
            }
            if ((position + 1) % 3 == 0) {
                return 2;
            }
            return 1;
        }
    }

    private static class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridItemVH> {

        private LayoutInflater mInflater;
        private int mSize;

        public GridAdapter(Context context, int size) {
            mInflater = LayoutInflater.from(context);
            mSize = size;
        }

        @Override
        public GridItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.grid_item, parent, false);
            GridItemVH itemVH = new GridItemVH(itemView);
            return itemVH;
        }

        @Override
        public void onBindViewHolder(GridItemVH holder, int position) {
            int randomColor = getRandomColor();
            holder.itemView.setBackgroundColor(randomColor);
        }

        @Override
        public int getItemCount() {
            return mSize;
        }

        public void addItem() {
            mSize++;
            notifyItemInserted(mSize - 1);
        }

        private int getRandomColor() {
            return Color.rgb((int)(Math.random() * 255), (int)(Math.random() * 255),
                    (int)(Math.random() * 255));
        }

        public class GridItemVH extends RecyclerView.ViewHolder {

            public GridItemVH(View itemView) {
                super(itemView);
            }
        }
    }
}
