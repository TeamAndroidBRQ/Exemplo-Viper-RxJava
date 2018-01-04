package com.rods.projeto.gitdesafio.robot;

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.rods.projeto.gitdesafio.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PullRequestsRobot {

    public PullRequestsRobot assertPullRequestLoaded() {
        onView(withId(R.id.pr_list))
                .check(matches(isDisplayed()));
        return this;
    }

    public PullRequestsRobot clickOnPullRequestListAtPosition(int position) throws Exception {
        onView(withId(R.id.pr_list))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        return this;
    }

    public PullRequestsRobot waitServer() throws Exception {
        Thread.sleep(2500);
        return this;
    }

}
