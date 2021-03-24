insert into PUBLIC.client
(id, address, created_at, cuit, deleted, deleted_at, dni, locality_id, mail, modified_at, name, surname)
values (1, 'ADDRESS-1', CURRENT_TIMESTAMP(), 123456789, 0, null, '12345678', 1, 'Joe@dora.biz', null, 'Berengaria', 'Hanigan'),
       (2, 'ADDRESS-2', CURRENT_TIMESTAMP(), 123456788, 0, null, '12345678', 1, 'Wayne.Crooks@destin.org', null, 'Gemariah', 'Benson'),
       (3, 'ADDRESS-3', CURRENT_TIMESTAMP(), 123456779, 0, null, '12345678', 1, 'Mackenzie@amely.io', null, 'Floyd', 'Martin'),
       (4, 'ADDRESS-4', CURRENT_TIMESTAMP(), 123455789, 1, CURRENT_TIMESTAMP(), '12345678', 1, 'Jammie@abagail.us', null, 'Myriam', 'Romilly'),
       (5, 'ADDRESS-5', CURRENT_TIMESTAMP(), 123446789, 1, CURRENT_TIMESTAMP(), '12345678', 1, 'Malcolm@haylie.info', null, 'Gaia', 'Ayers');
