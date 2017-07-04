package com.unicornheight.bakingapp;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.unicornheight.bakingapp.modules.home.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by deboajagbe on 6/27/17.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CakeListIntent {


    private static final String EXTRA_RECIPE_ID_KEY = "RECIPE_ID";
    private static final int EXTRA_RECIPE_ID_VALUE = 1;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule
            = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickEvent() {

        onView(ViewMatchers.withId(R.id.cake_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(
                hasExtra(EXTRA_RECIPE_ID_KEY, EXTRA_RECIPE_ID_VALUE)
        );
    }
}
