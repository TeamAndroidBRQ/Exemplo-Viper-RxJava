package com.rods.projeto.gitdesafio.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rods.projeto.gitdesafio.R;
import com.rods.projeto.gitdesafio.bean.Repository;
import com.rods.projeto.gitdesafio.rxbus.RxBus;
import com.rods.projeto.gitdesafio.ui.viewholder.FooterViewHolder;
import com.rods.projeto.gitdesafio.ui.viewholder.RepositoryViewHolder;

import java.util.List;

public class RepositoriesFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int FOOTER_VIEW_TYPE = 0;
    private static final int CELL_VIEW_TYPE = 2;
    private final RxBus rxBus;

    private List<Repository> mRepositories;

    public RepositoriesFeedAdapter(List<Repository> repositories, RxBus bus) {
        mRepositories = repositories;
        this.rxBus = bus;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1) {
            return FOOTER_VIEW_TYPE;
        } else {
            return CELL_VIEW_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        if (viewType == FOOTER_VIEW_TYPE) {
            View cellView = mInflater.inflate(R.layout.cell_loading_footer, parent, false);
            return new FooterViewHolder(cellView);
        } else {
            View cellView = mInflater.inflate(R.layout.cell_repository, parent, false);
            return new RepositoryViewHolder(cellView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == CELL_VIEW_TYPE) {
            ((RepositoryViewHolder)holder).fillContent(mRepositories.get(position), rxBus);
        }
    }

    @Override
    public int getItemCount() {
        return mRepositories.size() + 1;
    }

    public void insertNewItems(List<Repository> repositories) {
        mRepositories.addAll(repositories);
        notifyDataSetChanged();
    }

    public List<Repository> getAll() {
        return mRepositories;
    }
}
