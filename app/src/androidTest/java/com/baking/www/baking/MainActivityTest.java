//package com.baking.www.baking;
//
//
//import android.support.test.espresso.contrib.RecyclerViewActions;
//import android.support.test.espresso.matcher.ViewMatchers;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//
//@RunWith(AndroidJUnit4.class)
//public class MainActivityTest {
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Before
//    public void registerIdlingResource() {
////TODO: excute idling resource test over here
//    }
//
//    @Test
//    public void mainActivityTest() {
//        onView(ViewMatchers.withId(R.id.rv_recipes))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//    }
//
//    @After
//    public void unRegisterIdlingResource() {
//
//    }
//}
