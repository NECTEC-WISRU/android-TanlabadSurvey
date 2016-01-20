INSERT INTO
  province (province_code, name)
VALUES
  ("12", "นนทบุรี");

INSERT INTO
  district (district_code, name, province_code)
VALUES
  ("1202", "บางกรวย", "12"),
  ("1206", "ปากเกร็ด", "12"),
  ("1204", "บางบัวทอง", "12"),
  ("1205", "ไทรน้อย", "12"),
  ("1203", "บางใหญ่", "12"),
  ("1201", "เมืองนนทบุรี", "12");

INSERT INTO
  subdistrict (subdistrict_code, name, district_code)
VALUES
  ("120404", "บางคูรัด", "1204"),
  ("120405", "ละหาร", "1204"),
  ("120602", "บางตลาด", "1206"),
  ("120206", "บางคูเวียง", "1202"),
  ("120104", "บางกระสอ", "1201"),
  ("120601", "ปากเกร็ด", "1206"),
  ("120209", "ศาลากลาง", "1202"),
  ("120202", "บางกรวย", "1202"),
  ("120401", "โสนลอย", "1204"),
  ("120303", "บางเลน", "1203"),
  ("120507", "ทวีวัฒนา", "1205"),
  ("120106", "บางไผ่", "1201"),
  ("120305", "บางใหญ่", "1203"),
  ("120109", "ไทรม้า", "1201"),
  ("120611", "บางพลับ", "1206"),
  ("120201", "วัดชลอ", "1202"),
  ("120501", "ไทรน้อย", "1205"),
  ("120304", "เสาธงหิน", "1203"),
  ("120107", "บางศรีเมือง", "1201"),
  ("120503", "หนองเพรางาย", "1205"),
  ("120402", "บางบัวทอง", "1204"),
  ("120105", "ท่าทราย", "1201"),
  ("120604", "บางพูด", "1206"),
  ("120203", "บางสีทอง", "1202"),
  ("120103", "บางเขน", "1201"),
  ("120608", "เกาะเกร็ด", "1206"),
  ("120603", "บ้านใหม่", "1206"),
  ("120306", "บ้านใหม่", "1203"),
  ("120606", "คลองพระอุดม", "1206"),
  ("120504", "ไทรใหญ่", "1205"),
  ("120610", "คลองข่อย", "1206"),
  ("120101", "สวนใหญ่", "1201"),
  ("120102", "ตลาดขวัญ", "1201"),
  ("120612", "คลองเกลือ", "1206"),
  ("120301", "บางม่วง", "1203"),
  ("120207", "มหาสวัสดิ์", "1202"),
  ("120407", "พิมลราช", "1204"),
  ("120208", "ปลายบาง", "1202"),
  ("120403", "บางรักใหญ่", "1204"),
  ("120505", "ขุนศรี", "1205"),
  ("120607", "ท่าอิฐ", "1206"),
  ("120108", "บางกร่าง", "1201"),
  ("120406", "ลำโพ", "1204"),
  ("120502", "ราษฎร์นิยม", "1205"),
  ("120110", "บางรักน้อย", "1201"),
  ("120609", "อ้อมเกร็ด", "1206"),
  ("120506", "คลองขวาง", "1205"),
  ("120302", "บางแม่นาง", "1203"),
  ("120605", "บางตะไนย์", "1206"),
  ("120408", "บางรักพัฒนา", "1204"),
  ("120204", "บางขนุน", "1202"),
  ("120205", "บางขุนกอง", "1202");

INSERT INTO
  organization (org_id, name, subdistrict_code, health_region_code)
VALUES
  ("100", "กรมควบคุมโรค", "120105", "1");

INSERT INTO
  user_profile (username, org_id)
VALUES
  ("dpc-user", "100");

INSERT INTO
  place_type (type_id, name)
VALUES
  ("1", "หมู่บ้าน/ชุมชน"),
  ("2", "ศาสนสถาน"),
  ("3", "สถานศึกษา"),
  ("4", "โรงพยาบาล"),
  ("5", "โรงงาน");

INSERT INTO
  place_subtype (subtype_id, name, type_id)
VALUES
  ("1", "สำนักงานสาธารณสุขจังหวัด", "4"),
  ("2", "สำนักงานสาธารณสุขอำเภอ", "4"),
  ("3", "รพ.สต./สาธารณสุขชุมชน", "4"),
  ("4", "โรงพยาบาลศูนย์/โรงพยาบาลทั่วไป", "4"),
  ("5", "โรงพยาบาลชุมชน", "4"),
  ("6", "โรงพยาบาลอื่นสังกัด สธ./โรงพยาบาลสังกัดกระทรวงอื่น", "4"),
  ("7", "โรงพยาบาลเอกชน", "4"),
  ("8", "ศูนย์สุขภาพชุมชน/บริการสาธารณสุข", "4"),
  ("9", "ศูนย์วิชาการ", "4"),
  ("10", "ชุมชนแออัด", "1"),
  ("11", "ชุมชนพักอาศัย", "1"),
  ("12", "ชุมชนพาณิชย์", "1"),
  ("13", "วัด", "2"),
  ("14", "โบสถ์", "2"),
  ("15", "มัสยิด/สุเหร่า", "2"),
  ("16", "โรงเรียน", "3"),
  ("17", "โรงงาน", "5");

INSERT INTO
  place (place_id, subtype_id, name, subdistrict_code, update_by, update_time)
VALUES
  ("abc01db8-7207-8a65-152f-ad208cb99b5e", "10", "หมู่บ้านทดสอบ", "120202", "dpc-user", "2015-12-24T05:05:19.626Z");

INSERT INTO
  building (building_id, place_id, name, update_by, update_time)
VALUES
  ("00001db8-7207-8a65-152f-ad208cb99b01", "abc01db8-7207-8a65-152f-ad208cb99b5e", "23/2", "dpc-user", "2015-12-24T05:05:19.626Z");