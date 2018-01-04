package com.rods.projeto.gitdesafio;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rods.projeto.gitdesafio.robot.PullRequestsRobot;
import com.rods.projeto.gitdesafio.ui.activity.PullRequestActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PullRequestsTest {

    @Rule
    public ActivityTestRule<PullRequestActivity> mActivityRule =
            new ActivityTestRule<PullRequestActivity>(PullRequestActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, PullRequestActivity.class);
                    result.putExtra(PullRequestActivity.REPOSITORY_PATH_EXTRA, "ReactiveX/RxJava");
                    return result;
                }
            };

    private PullRequestsRobot robot;

    @Before
    public void init() {
        robot = new PullRequestsRobot();
    }

    @Test
    public void onPullRequestList_ShouldLoadList() throws Exception {
        robot
                .waitServer()
                .assertPullRequestLoaded();
    }

}
