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

INSERT INTO PUBLIC.SUPPLIER
(ID, ADDRESS, CREATED_AT, CUIT, DELETED, DELETED_AT, DESCRIPTION, LOCALITY_ID, MAIL, MODIFIED_AT, NAME)
VALUES
(1, 'ADDRESS-1', CURRENT_TIMESTAMP(), 2725452325, 0, NULL, 'SUP-DESC-1', 1, 'supplierone@mail.com', NULL, 'SUPPLIER-1'),
(2, 'ADDRESS-2', CURRENT_TIMESTAMP(), 2725452325, 0, NULL, 'SUP-DESC-2', 1, 'suppliertwo@mail.com', NULL, 'SUPPLIER-2'),
(3, 'ADDRESS-3', CURRENT_TIMESTAMP(), 2725452325, 0, NULL, 'SUP-DESC-3', 1, 'supplierthree@mail.com', NULL,  'SUPPLIER-3'),
(4, 'ADDRESS-4', CURRENT_TIMESTAMP(), 2725452325, 1, CURRENT_TIMESTAMP(), 'SUP-DESC-4', 1, 'supplierfour@mail.com', NULL, 'SUPPLIER-4'),
(5, 'ADDRESS-5', CURRENT_TIMESTAMP(), 2725452325, 1, CURRENT_TIMESTAMP(), 'SUP-DESC-5', 1, 'supplierfive@mail.com', NULL, 'SUPPLIER-5');
