package com.unicornheight.bakingapp;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.hamcrest.CoreMatchers.allOf;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.unicornheight.bakingapp.modules.details.DetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by deboajagbe on 7/3/17.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class StepsIntent {


    private static final String HAS_EXTRA = "CAKE";
    private static final int CAKE_VALUE = 1;

    private static final String EXTRA_STEPS = "STEPS";
    private static final int EXTRA_STEP_VALUE = 1;

    @Rule
    public IntentsTestRule<DetailActivity> intentsTestRule
            = new IntentsTestRule<>(DetailActivity.class);

    @Test
    public void clickOnRecyclerViewItem_runsRecipeStepActivityIntent() {

        onView(ViewMatchers.withId(R.id.step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(allOf(
                hasExtra(HAS_EXTRA, CAKE_VALUE),
                hasExtra(EXTRA_STEPS, EXTRA_STEP_VALUE)
        ));
    }
}
