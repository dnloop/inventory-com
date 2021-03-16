insert into inventario_comercial.province
(id, name)
values(1, 'PROVINCE');


insert into inventario_comercial.departments
(id, name)
values(1, 'DEPARTMENT');


insert into inventario_comercial.municipality
(id, name)
values(1, 'MUNICIPALITY');

insert into inventario_comercial.locality
(id, category, centroid_lat, centroid_lon, departament_id, "function", municipality_id, name, province_id)
values(1, 'CATEGORY', 1, 1, 1, 'FUNCTION', 1, 'LOCALITY', 1);