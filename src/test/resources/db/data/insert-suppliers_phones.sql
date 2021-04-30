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

INSERT INTO PUBLIC.SUPPLIER_PHONE
(ID, CREATED_AT, DELETED, DELETED_AT, MODIFIED_AT, "NUMBER", SUPPLIER_ID)
VALUES
(1, CURRENT_TIMESTAMP(), 0, NULL, NULL, '3344586966', 1),
(2, CURRENT_TIMESTAMP(), 0, NULL, NULL, '3344415577', 1),
(3, CURRENT_TIMESTAMP(), 0, NULL, NULL, '3377475588', 2),
(4, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP(), NULL, '3377512255', 1),
(5, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP(), NULL, '3377452288', 2);