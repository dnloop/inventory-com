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

package io.github.dnloop.inventorycom.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class PurchaseShareBuilder {
    private Integer number;
    private Date paymentDate;
    private Date dueDate;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer purchaseInvoiceId;

    public PurchaseShareBuilder setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public PurchaseShareBuilder setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public PurchaseShareBuilder setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public PurchaseShareBuilder setDeleted(Byte deleted) {
        this.deleted = deleted;
        return this;
    }

    public PurchaseShareBuilder setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PurchaseShareBuilder setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public PurchaseShareBuilder setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public PurchaseShareBuilder setPurchaseInvoiceId(Integer purchaseInvoiceId) {
        this.purchaseInvoiceId = purchaseInvoiceId;
        return this;
    }

    public PurchaseShare createPurchaseShare() {
        return new PurchaseShare(
                number, paymentDate, dueDate, deleted, createdAt, modifiedAt, deletedAt, purchaseInvoiceId);
    }
}