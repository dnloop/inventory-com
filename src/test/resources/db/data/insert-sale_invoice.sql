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
    PUBLIC.SALE_INVOICE (ID,
                         CLIENT_ID,
                         CREATED_AT,
                         DELETED,
                         DELETED_AT,
                         DISCOUNT,
                         GENERATION_DATE,
                         INVOICE_TYPE,
                         MODIFIED_AT,
                         "NUMBER",
                         PAYMENT_TYPE,
                         SURCHARGE,
                         TOTAL)
VALUES
(1, 1, CURRENT_TIMESTAMP(), 0, NULL, 0, CURRENT_TIMESTAMP(), 'A', NULL, 1, 'CASH', 0, 150),
(2, 2, CURRENT_TIMESTAMP(), 0, NULL, 0, CURRENT_TIMESTAMP(), 'A', NULL, 2, 'DEBIT', 0, 100),
(3, 3, CURRENT_TIMESTAMP(), 0, NULL, 0, CURRENT_TIMESTAMP(), 'A', NULL, 3, 'CASH', 0, 10);
