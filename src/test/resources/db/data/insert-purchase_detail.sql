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

INSERT
	INTO
	PUBLIC.PURCHASE_DETAIL (ID,
	AMOUNT,
	CREATED_AT,
	DELETED,
	DELETED_AT,
	IVA,
	MODIFIED_AT,
	PRODUCT_ID,
	PURCHASE_INVOICE_ID,
	SUBTOTAL,
	UNIT_PRICE)
VALUES
(1, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 1, 1, 100, 10),
(2, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 1, 1, 100, 10),
(3, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 1, 1, 100, 10),
(4, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 1, 1, 100, 10),
(5, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 1, 1, 100, 10),
(6, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 1, 1, 100, 10),
(7, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 4, 1, 100, 10),
(8, 10, CURRENT_TIMESTAMP(), 0, NULL, 21, NULL, 5, 1, 100, 10);
