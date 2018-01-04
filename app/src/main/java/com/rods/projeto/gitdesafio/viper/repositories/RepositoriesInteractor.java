package com.rods.projeto.gitdesafio.viper.repositories;

import com.rods.projeto.gitdesafio.bean.GitCallResult;
import com.rods.projeto.gitdesafio.bean.Repository;
import com.rods.projeto.gitdesafio.network.DesafioApiBuilder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RepositoriesInteractor implements RepositoriesContracts.Interactor{

    private static final String PAGE_PARAM = "page";
    private static final String GET_REPOS_METHOD = "search/repositories?q=language:Java&sort=stars&page={" + PAGE_PARAM + "}";

    private final RepositoriesContracts.InteractorOutput output;

    RepositoriesInteractor(RepositoriesContracts.InteractorOutput output) {
        this.output = output;
    }

    @Override
    public Disposable loadRepositories(int page) {
        return DesafioApiBuilder.getRequestBuilder(GET_REPOS_METHOD)
                .addPathParameter(PAGE_PARAM, String.valueOf(page))
                .build()
                .getObjectObservable(GitCallResult.class)
                .map(new Function<GitCallResult, List<Repository>>() {
                    @Override
                    public List<Repository> apply(GitCallResult gitCallResult) throws Exception {
                        return gitCallResult.getItems();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        output.onLoadRepositoriesSuccess(),
                        //onError
                        output.onLoadRepositoriesError()
                );
    }
}
