package com.example.mymovies;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import static android.support.v7.widget.helper.ItemTouchHelper.*;

//https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28?gi=b7f5c12ac38b

class SwipeController extends Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}