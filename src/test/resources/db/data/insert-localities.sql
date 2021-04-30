insert into PUBLIC.PROVINCE
(id, name)
values(1, 'PROVINCE');


insert into PUBLIC.DEPARTMENTS
(id, name)
values(1, 'DEPARTMENT');


insert into PUBLIC.MUNICIPALITY
(id, name)
values(1, 'MUNICIPALITY');

insert into PUBLIC.LOCALITY
(id, category, centroid_lat, centroid_lon, departament_id, function, municipality_id, name, province_id)
values(1, 'CATEGORY', 1, 1, 1, 'FUNCTION', 1, 'LOCALITY', 1);