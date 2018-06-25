package com.anandsinha.myapplication;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.id), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.policeBar), isDisplayed()));
        appCompatImageView.perform(click());

        pressBack();

        ViewInteraction resideMenuItem = onView(
                allOf(withClassName(is("com.special.ResideMenu$ResideMenuItem")),
                        withParent(allOf(withId(R.id.layout_left_menu),
                                withParent(withId(R.id.sv_left_menu))))));
        resideMenuItem.perform(scrollTo(), click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.policeBar), isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction resideMenuItem2 = onView(
                allOf(withClassName(is("com.special.ResideMenu$ResideMenuItem")),
                        withParent(allOf(withId(R.id.layout_left_menu),
                                withParent(withId(R.id.sv_left_menu))))));
        resideMenuItem2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.pubvehicleno1), isDisplayed()));
        appCompatEditText2.perform(replaceText("BR11AS3302"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.pubvehicleno1), withText("BR11AS3302"), isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.pubvehicleno1), withText("BR11AS3302"), isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.pubsearchbutton), isDisplayed()));
        appCompatImageView3.perform(click());

        pressBack();

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.policeBar), isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction resideMenuItem3 = onView(
                allOf(withClassName(is("com.special.ResideMenu$ResideMenuItem")),
                        withParent(allOf(withId(R.id.layout_left_menu),
                                withParent(withId(R.id.sv_left_menu))))));
        resideMenuItem3.perform(scrollTo(), click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.policeBar), isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction resideMenuItem4 = onView(
                allOf(withClassName(is("com.special.ResideMenu$ResideMenuItem")),
                        withParent(allOf(withId(R.id.layout_left_menu),
                                withParent(withId(R.id.sv_left_menu))))));
        resideMenuItem4.perform(scrollTo(), click());

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(R.id.publicBar), isDisplayed()));
        appCompatImageView6.perform(click());

        ViewInteraction resideMenuItem5 = onView(
                allOf(withClassName(is("com.special.ResideMenu$ResideMenuItem")),
                        withParent(allOf(withId(R.id.layout_right_menu),
                                withParent(withId(R.id.sv_right_menu))))));
        resideMenuItem5.perform(scrollTo(), click());

    }

}
