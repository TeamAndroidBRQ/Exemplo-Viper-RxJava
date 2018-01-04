package com.rods.projeto.gitdesafio.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rods.projeto.gitdesafio.R;
import com.rods.projeto.gitdesafio.bean.PullRequest;
import com.rods.projeto.gitdesafio.rxbus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PullRequestViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.pr_title_tv)
    TextView nameTv;
    @BindView(R.id.pr_description_tv)
    TextView descriptionTv;
    @BindView(R.id.pr_owner_profile)
    ImageView ownerImg;
    @BindView(R.id.pr_owner_name)
    TextView ownerNameTv;

    public PullRequestViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void fillContent(final PullRequest pr, final RxBus rxBus) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxBus.send(pr);
            }
        });

        nameTv.setText(pr.getTitle());
        descriptionTv.setText(pr.getDescription());
        ownerNameTv.setText(pr.getOwner().getLogin());

        Glide.with(itemView.getContext())
                .load(pr.getOwner().getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(ownerImg);
    }

}
