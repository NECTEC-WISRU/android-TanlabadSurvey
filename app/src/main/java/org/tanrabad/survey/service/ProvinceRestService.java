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

package org.tanrabad.survey.service;

import com.bluelinelabs.logansquare.LoganSquare;
import org.tanrabad.survey.TanrabadApp;
import org.tanrabad.survey.entity.lookup.Province;
import org.tanrabad.survey.service.json.JsonProvince;
import org.tanrabad.survey.utils.http.QueryStringBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceRestService extends AbsRestService<Province> {

    private static final String PATH = "/province";

    public ProvinceRestService() {
        this(BASE_API, new ServiceLastUpdatePreference(TanrabadApp.getInstance(), PATH));
    }

    private ProvinceRestService(String apiBaseUrl, ServiceLastUpdate serviceLastUpdate) {
        super(apiBaseUrl, serviceLastUpdate);
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    public String getQueryString() {
        return new QueryStringBuilder("geostd=4326", getApiFilterParam()).build();
    }

    @Override
    protected List<Province> jsonToEntityList(String responseBody) throws IOException {
        ArrayList<Province> provinceList = new ArrayList<>();
        List<JsonProvince> jsonProvinceList = LoganSquare.parseList(responseBody, JsonProvince.class);
        for (JsonProvince eachJsonProvince : jsonProvinceList)
            provinceList.add(eachJsonProvince.getEntity());
        return provinceList;
    }
}
