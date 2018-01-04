package com.rods.projeto.gitdesafio.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.rods.projeto.gitdesafio.R;
import com.rods.projeto.gitdesafio.adapter.PullRequestsFeedAdapter;
import com.rods.projeto.gitdesafio.app.DesafioApplication;
import com.rods.projeto.gitdesafio.bean.PullRequest;
import com.rods.projeto.gitdesafio.decorator.RecyclerItemDecorator;
import com.rods.projeto.gitdesafio.network.ErrorCause;
import com.rods.projeto.gitdesafio.viper.pull_requests.PullRequestsContracts;
import com.rods.projeto.gitdesafio.viper.pull_requests.PullRequestsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class PullRequestActivity extends BaseActivity implements PullRequestsContracts.View{

    private static final String STATE_PULL_REQUESTS = "a0s7gdlas@";
    public static final String REPOSITORY_PATH_EXTRA = "A)S7gdiidlas";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pr_list)
    RecyclerView pullrequestsList;

    @BindView(R.id.progress)
    ProgressBar progress;

    PullRequestsContracts.Presenter presenter;
    private String repoName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_request_list);
        repoName = getIntent().getStringExtra(REPOSITORY_PATH_EXTRA);

        toolbar.setTitle(repoName);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);

        pullrequestsList.setHasFixedSize(true);
        pullrequestsList.addItemDecoration(new RecyclerItemDecorator(getBaseContext()));

        presenter = new PullRequestsPresenter(this);
        if (savedInstanceState == null) {
            prepareLoad();
        } else {
            reloadActivityState(savedInstanceState);
        }
    }

    @Override
    public Observable<PullRequest> getListClickAsObservable() {
        return ((DesafioApplication)getApplication()).bus()
                .toObservable()
                .map(new Function<Object, PullRequest>() {
                    @Override
                    public PullRequest apply(Object o) throws Exception {
                        return (PullRequest)o;
                    }
                });
    }

    private void prepareLoad() {
        progress.setVisibility(View.VISIBLE);
        pullrequestsList.setVisibility(View.GONE);
        presenter.onLoadPullRequests(repoName.toLowerCase());
    }

    @Override
    public void showPullRequestsList(List<PullRequest> pullrequestes) {
        progress.setVisibility(View.GONE);
        pullrequestsList.setVisibility(View.VISIBLE);
        pullrequestsList.setAdapter(new PullRequestsFeedAdapter(pullrequestes,
                ((DesafioApplication)getApplication()).bus()));
    }

    @Override
    public void handlePullRequestLoadError(ErrorCause errorCause) {
        int errorMsgId = errorCause == ErrorCause.NO_INTERNET_ERROR ?
                R.string.error_msg_no_internet : R.string.error_msg_unknown;

        Snackbar.make(pullrequestsList, errorMsgId, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prepareLoad();
                    }
                })
                .show();
    }

    private void reloadActivityState(Bundle savedInstanceState) {
        List<PullRequest> pullRequestList = savedInstanceState.getParcelableArrayList(STATE_PULL_REQUESTS);
        showPullRequestsList(pullRequestList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PullRequestsFeedAdapter adapter = (PullRequestsFeedAdapter) pullrequestsList.getAdapter();
        if (adapter != null) {
            ArrayList<PullRequest> pullRequests = (ArrayList<PullRequest>) adapter.getPullRequests();
            outState.putParcelableArrayList(STATE_PULL_REQUESTS, pullRequests);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
}
