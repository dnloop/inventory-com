/*
 *     Inventory-Com: Inventory and Commerce Management Application.
 *     Copyright (C) 2021. dnloop.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

insert into PUBLIC.client
(id, address, created_at, cuit, deleted, deleted_at, dni, locality_id, mail, modified_at, name, surname)
values (1, 'ADDRESS-1', CURRENT_TIMESTAMP(), 123456789, 0, null, '12345678', 1, 'Joe@dora.biz', null, 'Berengaria', 'Hanigan'),
       (2, 'ADDRESS-2', CURRENT_TIMESTAMP(), 123456788, 0, null, '12345678', 1, 'Wayne.Crooks@destin.org', null, 'Gemariah', 'Benson'),
       (3, 'ADDRESS-3', CURRENT_TIMESTAMP(), 123456779, 0, null, '12345678', 1, 'Mackenzie@amely.io', null, 'Floyd', 'Martin'),
       (4, 'ADDRESS-4', CURRENT_TIMESTAMP(), 123455789, 1, CURRENT_TIMESTAMP(), '12345678', 1, 'Jammie@abagail.us', null, 'Myriam', 'Romilly'),
       (5, 'ADDRESS-5', CURRENT_TIMESTAMP(), 123446789, 1, CURRENT_TIMESTAMP(), '12345678', 1, 'Malcolm@haylie.info', null, 'Gaia', 'Ayers');
