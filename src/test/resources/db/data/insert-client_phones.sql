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

INSERT INTO PUBLIC.CLIENT_PHONE
(ID, CLIENT_ID, CREATED_AT, DELETED, DELETED_AT, MODIFIED_AT, "NUMBER")
VALUES
(1, 1, CURRENT_TIMESTAMP(), 0, NULL, NULL, '3344445566'),
(2, 1, CURRENT_TIMESTAMP(), 0, NULL, NULL, '3344455588'),
(3, 2, CURRENT_TIMESTAMP(), 0, NULL, NULL, '3377505577'),
(4, 1, CURRENT_TIMESTAMP(), 0, CURRENT_TIMESTAMP(), NULL, '3377512255'),
(5, 2, CURRENT_TIMESTAMP(), 0, CURRENT_TIMESTAMP(), NULL, '3377452288');
