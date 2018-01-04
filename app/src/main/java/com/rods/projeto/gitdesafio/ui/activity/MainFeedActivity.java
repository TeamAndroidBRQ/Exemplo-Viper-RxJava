package com.rods.projeto.gitdesafio.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rods.projeto.gitdesafio.R;
import com.rods.projeto.gitdesafio.adapter.RepositoriesFeedAdapter;
import com.rods.projeto.gitdesafio.app.DesafioApplication;
import com.rods.projeto.gitdesafio.bean.Repository;
import com.rods.projeto.gitdesafio.decorator.RecyclerItemDecorator;
import com.rods.projeto.gitdesafio.network.ErrorCause;
import com.rods.projeto.gitdesafio.viper.repositories.RepositoriesContracts;
import com.rods.projeto.gitdesafio.viper.repositories.RepositoriesPresenter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MainFeedActivity extends BaseActivity implements RepositoriesContracts.View{

    private static final String STATE_REPOSITORIES = "012*ASkxa,hvs";
    private static final String STATE_CURRENT_PAGE = "0978GCAL@Udt7";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.repositories_list)
    RecyclerView repositoriesList;

    @BindView(R.id.error_holder)
    View errorHolder;
    @BindView(R.id.error_tv)
    TextView errorTv;
    @BindView(R.id.loading_pb)
    ProgressBar loading;

    private RepositoriesContracts.Presenter presenter;
    private LinearLayoutManager mLayoutManager;

    private boolean isLoadingNextPage = false;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        toolbar.setTitle(R.string.title_repositories);
        setSupportActionBar(toolbar);

        repositoriesList.setHasFixedSize(true);
        repositoriesList.addItemDecoration(new RecyclerItemDecorator(getBaseContext()));
        repositoriesList.addOnScrollListener(recycleScrollListener);

        mLayoutManager = (LinearLayoutManager) repositoriesList.getLayoutManager();

        presenter = new RepositoriesPresenter(this);
        if (savedInstanceState == null) {
            prepareLoadRepositories();
        } else {
            reloadActivityState(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    public void prepareLoadRepositories() {
        repositoriesList.setVisibility(View.GONE);
        errorHolder.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        errorTv.setVisibility(View.GONE);

        presenter.onLoadRepositories(currentPage);
    }

    @Override
    public void showFeedList(List<Repository> repositories) {
        isLoadingNextPage = false;
        repositoriesList.setVisibility(View.VISIBLE);
        errorHolder.setVisibility(View.GONE);

        RepositoriesFeedAdapter adapter = (RepositoriesFeedAdapter) repositoriesList.getAdapter();
        if (adapter == null) {
            adapter = new RepositoriesFeedAdapter(repositories,
                    ((DesafioApplication)getApplication()).bus());
            repositoriesList.setAdapter(adapter);
        } else {
            adapter.insertNewItems(repositories);
        }
    }

    private void reloadActivityState(Bundle savedInstanceState) {
        currentPage = savedInstanceState.getInt(STATE_CURRENT_PAGE);
        List<Repository> repositories = savedInstanceState.getParcelableArrayList(STATE_REPOSITORIES);
        showFeedList(repositories);
    }

    @Override
    public void handleRepositoryLoadError(ErrorCause errorCause) {
        isLoadingNextPage = false;
        int errorMsgId = errorCause == ErrorCause.NO_INTERNET_ERROR ?
                R.string.error_msg_no_internet : R.string.error_msg_unknown;

        boolean isFirstLoad = repositoriesList.getAdapter() == null;
        if (isFirstLoad) {
            repositoriesList.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            errorHolder.setVisibility(View.VISIBLE);
            errorTv.setVisibility(View.VISIBLE);

            errorTv.setText(errorMsgId);
        } else {
            showSnackbakMsg(repositoriesList, errorMsgId);
        }
    }

    @Override
    public Observable<Repository> getListClickAsObservable() {
        return ((DesafioApplication)getApplication()).bus()
                .toObservable()
                .map(new Function<Object, Repository>() {
                    @Override
                    public Repository apply(Object o) throws Exception {
                        return (Repository)o;
                    }
                });
    }

    /**
     * Handling when user get to the end of RecycleView
     * */
    RecyclerView.OnScrollListener recycleScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(dy > 0)
            {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (!isLoadingNextPage)
                {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {
                        isLoadingNextPage = true;
                        currentPage++;
                        presenter.onLoadRepositories(currentPage);
                    }
                }
            }
        }
    };
}
