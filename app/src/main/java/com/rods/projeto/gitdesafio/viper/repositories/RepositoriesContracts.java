package com.rods.projeto.gitdesafio.viper.repositories;

import com.rods.projeto.gitdesafio.bean.Repository;
import com.rods.projeto.gitdesafio.network.ErrorCause;
import com.rods.projeto.gitdesafio.viper.BaseContracts;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RepositoriesContracts {

    public interface View {
        void showFeedList(List<Repository> repopsitories);
        void handleRepositoryLoadError(ErrorCause errorCause);
        Observable<Repository> getListClickAsObservable();
    }

    public interface Presenter extends BaseContracts.Presenter{
        void onLoadRepositories(int page);
    }

    public interface Interactor {
        Disposable loadRepositories(int page);
    }

    public interface InteractorOutput {
        Consumer<Throwable> onLoadRepositoriesError();
        Consumer<List<Repository>> onLoadRepositoriesSuccess();
    }

    public interface Router {
        //Repositoty path as parameter
        Consumer<String> showPullRequestOfRepository();
    }

}
