package com.skill_branch.graduate.ui.screens.search.find;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FindHistoryAdapter extends RecyclerView.Adapter<FindHistoryAdapter.HistoryViewHolder>{
    private List<String> historyList = new ArrayList<>();


    public void clearHistory(){
        historyList.clear();
        notifyDataSetChanged();
    }

    public void addHistory(String item){
        historyList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
