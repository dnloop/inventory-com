insert into PUBLIC.PROVINCE
(id, name)
values(1, 'PROVINCE-1'),
      (2, 'PROVINCE-1'),
      (3, 'PROVINCE-1');


insert into PUBLIC.DEPARTMENTS
(id, name)
values(1, 'DEPARTMENT-1'),
      (2, 'DEPARTMENT-2');


insert into PUBLIC.MUNICIPALITY
(id, name)
values(1, 'MUNICIPALITY-1'),
      (2, 'MUNICIPALITY-2'),
      (3, 'MUNICIPALITY-3');

insert into PUBLIC.locality
(id, category, centroid_lat, centroid_lon, departament_id, function, municipality_id, name, province_id)
values(1, 'CATEGORY', 1, 1, 2, 'FUNCTION', 1, 'LOCALITY-1', 1),
      (2, 'CATEGORY', 1, 1, 2, 'FUNCTION', 1, 'LOCALITY-2', 1),
      (3, 'CATEGORY', 1, 1, 1, 'FUNCTION', 2, 'LOCALITY-3', 2),
      (4, 'CATEGORY', 1, 1, 1, 'FUNCTION', 2, 'LOCALITY-4', 2),
      (5, 'CATEGORY', 1, 1, 1, 'FUNCTION', 3, 'LOCALITY-5', 3);