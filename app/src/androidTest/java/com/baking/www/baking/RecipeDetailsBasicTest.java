package com.baking.www.baking;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import com.baking.www.baking.fragments.RecipeDetailsFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsBasicTest {

    public static String RECIPE_INTRO = "Recipe Introduction";

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityTestRule = new ActivityTestRule<>(RecipeDetailsActivity.class);



    @Test
    public void useAppContext() {
        // Context of the app under test.

        mActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecipeDetailsFragment recipeDetailsFragment = startRecipeDetailsFragment();
            }
        });
        // Then use Espresso to test the Fragment
//        onView(withId(R.id.iv_record_image)).check(matches(isDisplayed()));
//        onData(anything()).inAdapterView(withId(R.id.rv_steps)).atPosition(1).perform(click());
//        onView(withId(R.id.tv_step_description)).check(matches(withText(RECIPE_INTRO)));

    }


    private RecipeDetailsFragment startRecipeDetailsFragment() {
        RecipeDetailsActivity activity =  mActivityTestRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        transaction.add(recipeDetailsFragment, "recipeDetailsFragment");
        transaction.commit();
        return recipeDetailsFragment;
    }
}
