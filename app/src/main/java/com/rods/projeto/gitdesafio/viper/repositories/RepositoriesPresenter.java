package com.rods.projeto.gitdesafio.viper.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.rods.projeto.gitdesafio.bean.Repository;
import com.rods.projeto.gitdesafio.network.ErrorCause;
import com.rods.projeto.gitdesafio.utils.NetworkUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RepositoriesPresenter implements RepositoriesContracts.Presenter, RepositoriesContracts.InteractorOutput {

    private RepositoriesContracts.Interactor interactor;
    private RepositoriesContracts.Router router;
    private RepositoriesContracts.View view;

    private CompositeDisposable disposablesUI;
    private CompositeDisposable disposablesAPI;
    private Context context;

    public RepositoriesPresenter(RepositoriesContracts.View view){
        this.view = view;
        this.context = ((AppCompatActivity)view).getBaseContext();
        this.disposablesAPI = new CompositeDisposable();
        this.disposablesUI = new CompositeDisposable();

        interactor = new RepositoriesInteractor(this);
        router = new RepositoriesRouter((AppCompatActivity)view);
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
                getSubscriptionForRepositoryListClick()
        );
    }

    @Override
    public void onLoadRepositories(int page) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            disposablesAPI.add(
                    interactor.loadRepositories(page)
            );
        } else {
            view.handleRepositoryLoadError(ErrorCause.NO_INTERNET_ERROR);
        }
    }

    @Override
    public Consumer<Throwable> onLoadRepositoriesError() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.handleRepositoryLoadError(ErrorCause.UNKNOWN_ERROR);
            }
        };
    }

    @Override
    public Consumer<List<Repository>> onLoadRepositoriesSuccess() {
        return new Consumer<List<Repository>>() {
            @Override
            public void accept(List<Repository> repositories) throws Exception {
                view.showFeedList(repositories);
            }
        };
    }

    @NonNull
    private Disposable getSubscriptionForRepositoryListClick() {
        return view.getListClickAsObservable()
                .map(new Function<Repository, String>() {
                    @Override
                    public String apply(Repository repository) throws Exception {
                        return repository.getFullName();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(router.showPullRequestOfRepository());
    }
}
