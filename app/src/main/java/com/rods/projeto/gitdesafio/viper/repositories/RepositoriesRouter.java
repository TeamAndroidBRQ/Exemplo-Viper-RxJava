package com.rods.projeto.gitdesafio.viper.repositories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.rods.projeto.gitdesafio.ui.activity.PullRequestActivity;

import io.reactivex.functions.Consumer;

public class RepositoriesRouter implements RepositoriesContracts.Router {

    Context context;

    public RepositoriesRouter(Activity activity) {
        this.context = activity;
    }

    @Override
    public Consumer<String> showPullRequestOfRepository() {
        return new Consumer<String>() {
            @Override
            public void accept(String repoPath) throws Exception {
                Intent it = new Intent(context, PullRequestActivity.class);
                it.putExtra(PullRequestActivity.REPOSITORY_PATH_EXTRA, repoPath);

                context.startActivity(it);
            }
        };
    }
}
