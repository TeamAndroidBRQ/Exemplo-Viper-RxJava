package com.rods.projeto.gitdesafio.robot;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.rods.projeto.gitdesafio.R;
import com.rods.projeto.gitdesafio.recyclerview.RecyclerViewItemCountAssertion;
import com.rods.projeto.gitdesafio.ui.activity.MainFeedActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class RepositoryListRobot {

    public RepositoryListRobot assertRepositoriesLoaded(String vacationDesc) {
        onView(withId(R.id.repositories_list))
                .check(matches(hasDescendant(withText(vacationDesc))));
        return this;
    }

    public RepositoryListRobot checkListSize(int count) {
        onView(withId(R.id.repositories_list))
                .check(new RecyclerViewItemCountAssertion(count));
        return this;
    }

    public RepositoryListRobot clickOnRepositoryListAtPosition(int position) throws Exception {
        onView(withId(R.id.repositories_list))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        return this;
    }

    public RepositoryListRobot assertPullRequestsLoaded() throws Exception {
        onView(withId(R.id.pr_list))
                .check(matches(isDisplayed()));
        return this;
    }

    public RepositoryListRobot waitServer() throws Exception {
        Thread.sleep(2500);
        return this;
    }

    public RepositoryListRobot rotateScreen(ActivityTestRule<MainFeedActivity> activityRule) {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return this;
    }
}
