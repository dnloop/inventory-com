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
    PUBLIC.SALE_DETAIL (ID,
                        AMOUNT,
                        CREATED_AT,
                        DELETED,
                        DELETED_AT,
                        IVA,
                        MODIFIED_AT,
                        PRODUCT_ID,
                        SALE_INVOICE_ID,
                        UNIT_PRICE,
                        SUBTOTAL)
VALUES
(1, 10, CURRENT_TIMESTAMP(), 0, NULL, 0, NULL, 1, 1, 5, 50),
(2, 10, CURRENT_TIMESTAMP(), 0, NULL, 0, NULL, 1, 1, 5, 50),
(3, 10, CURRENT_TIMESTAMP(), 0, NULL, 0, NULL, 2, 1, 5, 50),
(4, 10, CURRENT_TIMESTAMP(), 0, NULL, 0, NULL, 3, 2, 10, 100),
(5, 1, CURRENT_TIMESTAMP(), 0, NULL, 0, NULL, 4, 3, 10, 10);

