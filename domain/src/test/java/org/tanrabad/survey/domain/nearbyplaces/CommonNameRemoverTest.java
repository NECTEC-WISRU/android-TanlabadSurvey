package org.tanrabad.survey.domain.nearbyplaces;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by CHNCS23 on 7/12/2559.
 */

public class CommonNameRemoverTest {

    String[] commonPlaceTypes = {"โรงเรียน" ,"วัด", "หมู่"};

    @Test public void testRemoveCommonPlaceName() throws Exception {

        assertEquals("บ้านxxxx", replaceCommonPlaceType("หมู่ 1 บ้านxxxx"));
    }

    String replaceCommonPlaceType(String placeName) {
        String trimmedPlaceName = placeName.trim();
        for(String eachPlaceType : commonPlaceTypes) {
            if(trimmedPlaceName.contains(eachPlaceType)) {
                if(eachPlaceType.equals("หมู่")){
                    return trimmedPlaceName.replaceAll("หมู่( \\d*)|หมู่บ้าน","").trim();
                }
                return trimmedPlaceName.replace(eachPlaceType, "");
            }

        }
        return placeName;
    }
}
