package com.rods.projeto.gitdesafio.viper.pull_requests;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import io.reactivex.functions.Consumer;

public class PullRequestsRouter implements PullRequestsContracts.Router{

    Activity activity;

    public PullRequestsRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Consumer<Uri> openPullRequestinBrowser() {
        return new Consumer<Uri>() {
            @Override
            public void accept(Uri uri) throws Exception {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(browserIntent);
            }
        };
    }
}
