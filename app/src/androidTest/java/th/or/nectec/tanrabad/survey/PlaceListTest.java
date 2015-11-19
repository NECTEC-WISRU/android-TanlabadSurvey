/*
 * Copyright (c) 2015 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package th.or.nectec.tanrabad.survey;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PlaceListTest extends TANRABADInstrumentationBaseTest {
    public ActivityTestRule<PlaceListActivity> mActivityTestRule = new ActivityTestRule<>(PlaceListActivity.class);
    PlaceListActivity mAtivity;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.addCategory(".PlaceListActivity");
        mAtivity = mActivityTestRule.launchActivity(intent);
    }

    @Test
    public void testDefaultPageDefineSurveyPlace() throws Exception {
        onView(withText(R.string.survey_place))
                .check(matches(isDisplayed()));
        onView(withId(R.id.text_show_title_place_list))
                .check(matches(withText("รายชื่อสถานที่")));
        onView(withId(R.id.place_count))
                .check(matches(withText("7")));
        onView(withText(R.string.not_define_place_type))
                .check(matches(isDisplayed()));
        waitingFor(2000);
    }

    @Test
    public void testChooseTypeVillageCommunity() throws Exception {
        onView(withText(R.string.not_define_place_type))
                .perform(click());
        onView(withText(R.string.village_community))
                .perform(click());
        onView(withText(R.string.village_community))
                .check(matches(isDisplayed()));
        onView(withText("2"))
                .check(matches(isDisplayed()));
        onView(withText("หมู่บ้านพาลาซเซตโต้"))
                .check(matches(isDisplayed()));
        onView(withText("ชุมชนกอล์ฟวิว"))
                .check(matches(isDisplayed()));
        waitingFor(2000);
    }
}
