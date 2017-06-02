package com.baking.www.baking;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import com.baking.www.baking.IdlingResource.SimpleIdlingResource;
import com.baking.www.baking.fragments.MainFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Dell on 01/06/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MyFragmentTest {


    private SimpleIdlingResource mIdlingResource;



    @Rule
    public FragmentTestRule<MainFragment> mFragmentTestRule = new FragmentTestRule<>(MainFragment.class);

    @Before
    public void registerIdlingResource() {
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);

//        onView(withId(R.id.an_id_in_the_fragment)).check(matches(isDisplayed()));

        mIdlingResource = mFragmentTestRule.getFragment().getIdlingResource();
//        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void fragment_is_instantiated() {

        onView(withId(R.id.main_fragment)).check(matches(isDisplayed()));

    }

    @Test
    public void recycler_is_instantiated() {

        onView(withId(R.id.rv_recipes)).check(matches(isDisplayed()));

    }

    @Test
    public void fragment_can_be_instantiated() {


        // Then use Espresso to test the Fragment
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}