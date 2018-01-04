package com.rods.projeto.gitdesafio.ui.viewholder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rods.projeto.gitdesafio.R;
import com.rods.projeto.gitdesafio.bean.Repository;
import com.rods.projeto.gitdesafio.rxbus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rp_name_tv)
    TextView nameTv;
    @BindView(R.id.rp_description_tv)
    TextView descriptionTv;
    @BindView(R.id.rp_fork_count_tv)
    TextView forkCountTv;
    @BindView(R.id.rp_star_count_tv)
    TextView starCountTv;
    @BindView(R.id.rp_owner_profile)
    ImageView ownerImg;
    @BindView(R.id.rp_owner_name)
    TextView ownerNameTv;

    public RepositoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        forkCountTv.setCompoundDrawablesWithIntrinsicBounds(
                tintDrawable(itemView.getResources(), R.drawable.ic_call_split_black_24dp, R.color.colorCellGolden)
                , null, null, null);

        starCountTv.setCompoundDrawablesWithIntrinsicBounds(
                tintDrawable(itemView.getResources(), R.drawable.ic_grade_black_24dp, R.color.colorCellGolden)
                , null, null, null);
    }

    public void fillContent(final Repository repository, final RxBus rxBus) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxBus.send(repository);
            }
        });

        nameTv.setText(repository.getName());
        descriptionTv.setText(repository.getDescription());
        forkCountTv.setText(Integer.toString(repository.getForksCount()));
        starCountTv.setText(Integer.toString(repository.getStarsCount()));
        ownerNameTv.setText(repository.getOwner().getLogin());

        Glide.with(itemView.getContext())
                .load(repository.getOwner().getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(ownerImg);
    }

    private Drawable tintDrawable (Resources resources, int drawableId, int colorId) {
        Drawable drawable = resources.getDrawable(drawableId);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable.mutate(), resources.getColor(colorId));
        return drawable;
    }

}
