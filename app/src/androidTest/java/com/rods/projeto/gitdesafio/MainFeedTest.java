package com.rods.projeto.gitdesafio;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rods.projeto.gitdesafio.robot.RepositoryListRobot;
import com.rods.projeto.gitdesafio.ui.activity.MainFeedActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainFeedTest {

    @Rule
    public ActivityTestRule<MainFeedActivity> mActivityRule =
            new ActivityTestRule<>(MainFeedActivity.class);

    private RepositoryListRobot robot;

    @Before
    public void init() {
        robot = new RepositoryListRobot();
    }

    @Test
    public void onMainFeed_LoadRepositories_ChangeScreenOrientation_ShouldKeepActivityState() throws Exception {
        robot
                .waitServer()
                .assertRepositoriesLoaded("RxJava")
                .checkListSize(31)
                .rotateScreen(mActivityRule)
                .checkListSize(31);
    }

    @Test
    public void onMainFeed_LoadRepositories_ClickOnList_ShouldShowPullRequestList() throws Exception {
        robot
                .waitServer()
                .clickOnRepositoryListAtPosition(1)
                .waitServer()
                .assertPullRequestsLoaded();
    }
}
