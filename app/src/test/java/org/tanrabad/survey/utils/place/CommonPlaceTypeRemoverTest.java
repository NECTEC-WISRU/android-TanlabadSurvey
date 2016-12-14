package org.tanrabad.survey.utils.place;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by CHNCS23 on 13/12/2559.
 */
public class CommonPlaceTypeRemoverTest {
    @Test public void testRemoveVillageName() throws Exception {
        assertEquals("บ้านxxxx", CommonPlaceTypeRemover.remove("หมู่ 1 บ้านxxxx"));
    }

    @Test public void testRemoveSchoolName() throws Exception {
        assertEquals("วัดบางไผ่", CommonPlaceTypeRemover.remove("โรงเรียนวัดบางไผ่"));
    }
}