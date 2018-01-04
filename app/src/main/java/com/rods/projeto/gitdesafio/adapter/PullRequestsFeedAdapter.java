package com.rods.projeto.gitdesafio.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rods.projeto.gitdesafio.R;
import com.rods.projeto.gitdesafio.bean.PullRequest;
import com.rods.projeto.gitdesafio.rxbus.RxBus;
import com.rods.projeto.gitdesafio.ui.viewholder.PullRequestViewHolder;

import java.util.List;

public class PullRequestsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final RxBus rxBus;
    private List<PullRequest> pullRequests;

    public PullRequestsFeedAdapter(List<PullRequest> repositories, RxBus bus) {
        pullRequests = repositories;
        rxBus = bus;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View cellView = mInflater.inflate(R.layout.cell_pull_request, parent, false);
        return new PullRequestViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PullRequestViewHolder)holder).fillContent(pullRequests.get(position), rxBus);
    }

    @Override
    public int getItemCount() {
        return pullRequests.size();
    }

    public List<PullRequest> getPullRequests() {
        return pullRequests;
    }
}
