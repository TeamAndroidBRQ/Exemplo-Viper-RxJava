package com.rods.projeto.gitdesafio.viper.pull_requests;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.rods.projeto.gitdesafio.bean.PullRequest;
import com.rods.projeto.gitdesafio.network.ErrorCause;
import com.rods.projeto.gitdesafio.utils.NetworkUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class PullRequestsPresenter implements PullRequestsContracts.Presenter, PullRequestsContracts.InteractorOutput {

    private final PullRequestsContracts.View view;
    private final PullRequestsContracts.Router router;
    private final PullRequestsContracts.Interactor interactor;

    private CompositeDisposable disposablesUI;
    private CompositeDisposable disposablesAPI;
    private Context context;

    public PullRequestsPresenter(PullRequestsContracts.View view) {
        this.view = view;
        this.context = ((AppCompatActivity)view).getBaseContext();
        this.interactor = new PullRequestsInteractor(this);
        this.router = new PullRequestsRouter((AppCompatActivity)view);
        this.disposablesAPI = new CompositeDisposable();
        this.disposablesUI = new CompositeDisposable();
    }

    @Override
    public void detach() {
        disposablesAPI.clear();
    }

    @Override
    public void detachUI() {
        disposablesUI.clear();
    }

    @Override
    public void attachUI() {
        disposablesUI.add(
                getSubscriptionForPullRequestListClick()
        );
    }

    @Override
    public void onLoadPullRequests(String repoPath) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            disposablesAPI.add(
                    interactor.loadPullRequests(repoPath)
            );
        } else {
            view.handlePullRequestLoadError(ErrorCause.NO_INTERNET_ERROR);
        }
    }

    @Override
    public Consumer<Throwable> onLoadPullRequestsError() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.handlePullRequestLoadError(ErrorCause.UNKNOWN_ERROR);
            }
        };
    }

    @Override
    public Consumer<List<PullRequest>> onLoadPullRequestsSuccess() {
        return new Consumer<List<PullRequest>>() {
            @Override
            public void accept(List<PullRequest> pullRequests) throws Exception {
                view.showPullRequestsList(pullRequests);
            }
        };
    }

    @NonNull
    private Disposable getSubscriptionForPullRequestListClick() {
        return view.getListClickAsObservable()
                .map(new Function<PullRequest, Uri>() {
                    @Override
                    public Uri apply(PullRequest pullRequest) throws Exception {
                        return Uri.parse(pullRequest.getContentUrl());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(router.openPullRequestinBrowser());
    }
}
