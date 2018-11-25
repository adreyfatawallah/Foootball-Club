package com.example.adrey.footballclub.main;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class ErrorTextMatchers {

    public ErrorTextMatchers() { }

    public static Matcher<View> withErrorText(String text) {
        return withErrorText(Matchers.is(text));
    }

    public static Matcher<View> withErrorText(final Matcher<String> stringMatcher) {
        return new BoundedMatcher<View, TextView>(TextView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("with error text: ");
                stringMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(TextView item) {
                return stringMatcher.matches(item.getError().toString());
            }
        };
    }

    public static Matcher<View> withErrorText(@StringRes final int resourceId) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            private String resourceName = null;
            private String expectedText = null;

            @Override
            public void describeTo(Description description) {
                description.appendText("with error text from resource id: ");
                description.appendValue(resourceId);
                if (null != resourceName) {
                    description.appendText("[");
                    description.appendText(resourceName);
                    description.appendText("]");
                }
                if (null != expectedText) {
                    description.appendText(" value: ");
                    description.appendText(expectedText);
                }
            }

            @Override
            protected boolean matchesSafely(TextView item) {
                if (null == expectedText) {
                    try {
                        expectedText = item.getResources().getString(resourceId);
                        resourceName = item.getResources().getResourceEntryName(resourceId);
                    } catch (Resources.NotFoundException e) { }
                }
                return null != expectedText && expectedText.equals(item.getError());
            }
        };
    }
}
