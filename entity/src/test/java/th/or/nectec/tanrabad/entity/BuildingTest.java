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

package th.or.nectec.tanrabad.entity;

import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class BuildingTest {

    public static final UUID BUILDING_UUID = UUID.randomUUID();
    public static final String BUILDING_NAME = "โบสถ์ใหญ่";

    private Place saintMarry;
    private Building mainChurch;

    @Before
    public void setUp() throws Exception {
        saintMarry = Place.withName("วิหารเซนต์เมรี่");
        mainChurch = new Building(BUILDING_UUID, BUILDING_NAME);
        mainChurch.setPlace(saintMarry);
    }

    @Test
    public void testWithName() {
        Building building = Building.withName(BUILDING_NAME);
        assertEquals(BUILDING_NAME, building.getName());
    }

    @Test
    public void testSetThenGetPlace() {
        mainChurch.setPlace(saintMarry);
        assertEquals(saintMarry, mainChurch.getPlace());
    }

    @Test
    public void testGetName() {
        assertEquals(BUILDING_NAME, mainChurch.getName());
    }

    @Test
    public void testSetThenGetName() {
        String newChurchName = "โบสถ์เล็ก";
        mainChurch.setName(newChurchName);

        assertEquals(newChurchName, mainChurch.getName());
    }

    @Test
    public void testGetId() {
        assertEquals(BUILDING_UUID, mainChurch.getId());
    }

    @Test
    public void testSetThenGetBuildingLocation() {
        Location mainChurchLocation = new Location(14.078606, 100.603120);
        mainChurch.setLocation(mainChurchLocation);

        assertEquals(mainChurchLocation, mainChurch.getLocation());
    }

    @Test
    public void buildingWithDifferentIdMustNotEqual() {
        Building another = new Building(UUID.randomUUID(), BUILDING_NAME);
        another.setPlace(saintMarry);

        assertNotEquals(mainChurch, another);
    }

    @Test
    public void buildingWithDifferentNameMustNotEqual() {
        Building minorChurch = new Building(BUILDING_UUID, "โบสถ์เล็ก");
        minorChurch.setPlace(saintMarry);

        assertNotEquals(mainChurch, minorChurch);
    }

    @Test
    public void buildingWithDifferentPlaceMustNotEqual() {
        Building anotherChurch = new Building(BUILDING_UUID, BUILDING_NAME);
        anotherChurch.setPlace(Place.withName("โรงเรียนเซนต์เมรี่"));

        assertNotEquals(mainChurch, anotherChurch);
    }

    @Test
    public void buildingWithTheSameNameAndIdAndPlaceMustEqual() {
        Building sameChurch = new Building(BUILDING_UUID, BUILDING_NAME);
        sameChurch.setPlace(saintMarry);

        assertEquals(mainChurch, sameChurch);
    }

    @Test
    public void testGson() throws Exception {
        Building building = new Gson().fromJson(Resource.readFile("building.json"), Building.class);
        DateTime updateTime = DateTime.parse("2015-12-24T05:05:19.626Z");

        assertEquals(uuid("b7a9d934-04fc-a22e-0539-6c17504f732e"), building.getId());
        assertEquals(uuid("b7a9d934-000c-b33a-0539-6c17504b345e"), building.getPlaceId());
        assertEquals("อาคาร 1", building.getName());
        assertEquals(updateTime, building.getUpdateTimestamp());
    }

    private UUID uuid(String uuid) {
        return UUID.fromString(uuid);
    }
}