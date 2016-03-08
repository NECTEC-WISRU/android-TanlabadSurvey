/*
 * Copyright (c) 2016 NECTEC
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

package th.or.nectec.tanrabad.survey.service;

import com.bluelinelabs.logansquare.LoganSquare;
import org.joda.time.DateTime;
import th.or.nectec.tanrabad.entity.Place;
import th.or.nectec.tanrabad.survey.TanrabadApp;
import th.or.nectec.tanrabad.survey.presenter.AccountUtils;
import th.or.nectec.tanrabad.survey.service.json.JsonEntomology;

import java.io.IOException;
import java.util.List;

public class EntomologyRestService extends AbsRestService<JsonEntomology> {

    public static final String PATH = "/entomology";
    private final Place place;

    public EntomologyRestService(Place place) {
        this(BASE_API, new ServiceLastUpdatePreference(TanrabadApp.getInstance(), PATH), place);
    }

    public EntomologyRestService(String apiBaseUrl, ServiceLastUpdate serviceLastUpdate, Place place) {
        super(apiBaseUrl, serviceLastUpdate);
        this.place = place;
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected List<JsonEntomology> jsonToEntityList(String responseBody) throws IOException {
        return LoganSquare.parseList(responseBody, JsonEntomology.class);
    }

    @Override
    public String getDefaultParams() {
        return "geostd=4326"
                + "&" + placeIdParam()
                + "&" + OrgIdParam()
                + "&" + oneWeekIntervalParam();
    }

    private String placeIdParam() {
        return "place_id=" + place.getId();
    }

    private String OrgIdParam() {
        return "org_id=" + AccountUtils.getUser().getOrganizationId();
    }

    private String oneWeekIntervalParam() {
        DateTime dateTime = new DateTime();
        return "startdate=" + unixTime(dateTime.minusDays(7)) + "&enddate=" + unixTime(dateTime);
    }

    private long unixTime(DateTime dateTime) {
        return dateTime.getMillis() / 1000;
    }
}
