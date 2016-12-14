package org.tanrabad.survey.utils.place;

public class CommonPlaceTypeRemover {
    public static final String[] COMMON_PLACE_TYPES =
            { "โรงเรียน", "วัด", "หมู่", "โรงพยาบาล", "รพ.สต.", "มหาวิทยาลัย", "สถานีตำรวจ", "สถานีรถไฟ" };

    public static String remove(String placeName) {
        String trimmedPlaceName = placeName.trim();
        for (String eachPlaceType : COMMON_PLACE_TYPES) {
            if (trimmedPlaceName.contains(eachPlaceType)) {
                if (eachPlaceType.equals("หมู่")) {
                    return trimmedPlaceName.replaceAll("หมู่( \\d*)|หมู่บ้าน", "").trim();
                }
                return trimmedPlaceName.replace(eachPlaceType, "").replaceAll("[\\p{Punct}\\s\\d]+", "");
            }
        }
        return placeName;
    }
}
