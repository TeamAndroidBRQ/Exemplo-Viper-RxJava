package com.rods.projeto.gitdesafio.viper.pull_requests;

import com.rods.projeto.gitdesafio.bean.PullRequest;
import com.rods.projeto.gitdesafio.network.DesafioApiBuilder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class PullRequestsInteractor implements PullRequestsContracts.Interactor{

    private static final String REPO_PATH_PARAM = "repoPath";
    private static final String GET_REPOS_METHOD = "repos/{" + REPO_PATH_PARAM + "}/pulls";

    private final PullRequestsContracts.InteractorOutput output;

    PullRequestsInteractor(PullRequestsContracts.InteractorOutput output) {
        this.output = output;
    }

    @Override
    public Disposable loadPullRequests(String repoPath) {
        return DesafioApiBuilder.getRequestBuilder(GET_REPOS_METHOD)
                .addPathParameter(REPO_PATH_PARAM, repoPath)
                .build()
                .getObjectListObservable(PullRequest.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        output.onLoadPullRequestsSuccess(),
                        //onError
                        output.onLoadPullRequestsError()
                );
    }
}
