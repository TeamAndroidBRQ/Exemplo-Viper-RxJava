package com.rods.projeto.gitdesafio.viper.pull_requests;

import android.net.Uri;

import com.rods.projeto.gitdesafio.bean.PullRequest;
import com.rods.projeto.gitdesafio.network.ErrorCause;
import com.rods.projeto.gitdesafio.viper.BaseContracts;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PullRequestsContracts {

    public interface View {
        void showPullRequestsList(List<PullRequest> repopsitories);
        void handlePullRequestLoadError(ErrorCause errorCause);
        Observable<PullRequest> getListClickAsObservable();
    }

    public interface Presenter extends BaseContracts.Presenter{
        void onLoadPullRequests(String repoPath);
    }

    public interface Interactor {
        Disposable loadPullRequests(String repoPath);
    }

    public interface InteractorOutput {
        Consumer<Throwable> onLoadPullRequestsError();
        Consumer<List<PullRequest>> onLoadPullRequestsSuccess();
    }

    public interface Router {
        Consumer<Uri> openPullRequestinBrowser();
    }
}
