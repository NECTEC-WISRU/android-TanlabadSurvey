package org.tanrabad.survey.presenter.nearbyplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.tanrabad.survey.entity.Place;

import static org.junit.Assert.assertEquals;

public class PlaceWeightScoreComparatorTest {
    @Test public void compare() throws Exception {
        Place tuKindergarten = new Place(UUID.nameUUIDFromBytes("3".getBytes()), "โรงเรียนประถมศึกษาธรรมศาสตร์");
        tuKindergarten.setWeight(0.4);
        Place bangkokUniversity =
                new Place(UUID.nameUUIDFromBytes("4".getBytes()), "มหาวิทยาลัยกรุุงเทพ (วิทยาเขตรังสิต)");
        bangkokUniversity.setWeight(0.5);
        Place racha1School = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "โรงเรียนบางปะอิน \"ราชานุเคราะห์ ๑\"");
        racha1School.setWeight(0.8);
        Place watPhonim = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "วัดโพธิ์นิ่ม");
        watPhonim.setWeight(0.2);
        Place watNivetdhammaprawat = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "วัดนิเวศธรรมประวัติ");
        watNivetdhammaprawat.setWeight(0.1);
        Place salapanSchool = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "โรงเรียนศาลาพัน");
        salapanSchool.setWeight(0.9);

        List<Place> places = new ArrayList<>();
        places.add(tuKindergarten);
        places.add(bangkokUniversity);
        places.add(racha1School);
        places.add(watPhonim);
        places.add(watNivetdhammaprawat);
        places.add(salapanSchool);

        List<Place> sortedPlaces = new ArrayList<>();
        sortedPlaces.add(salapanSchool);
        sortedPlaces.add(racha1School);
        sortedPlaces.add(bangkokUniversity);
        sortedPlaces.add(tuKindergarten);
        sortedPlaces.add(watPhonim);
        sortedPlaces.add(watNivetdhammaprawat);

        Collections.sort(places, new PlaceWeightScoreComparator());
        assertEquals(sortedPlaces, places);
    }
}