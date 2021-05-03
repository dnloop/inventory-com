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

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PurchaseInvoiceBuilder {
    private Integer number;
    private Timestamp generationDate;
    private String paymentType;
    private String invoiceType;
    private BigDecimal surcharge;
    private BigDecimal discount;
    private BigDecimal total;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer supplierId;

    public PurchaseInvoiceBuilder() {}

    public PurchaseInvoiceBuilder setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public PurchaseInvoiceBuilder setGenerationDate(Timestamp generationDate) {
        this.generationDate = generationDate;
        return this;
    }

    public PurchaseInvoiceBuilder setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public PurchaseInvoiceBuilder setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public PurchaseInvoiceBuilder setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
        return this;
    }

    public PurchaseInvoiceBuilder setDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public PurchaseInvoiceBuilder setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public PurchaseInvoiceBuilder setDeleted(Byte deleted) {
        this.deleted = deleted;
        return this;
    }

    public PurchaseInvoiceBuilder setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PurchaseInvoiceBuilder setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public PurchaseInvoiceBuilder setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public PurchaseInvoiceBuilder setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public PurchaseInvoice createPurchaseInvoice() {
        return new PurchaseInvoice(
                number, generationDate, paymentType, invoiceType, surcharge, discount, total, deleted, createdAt,
                modifiedAt, deletedAt, supplierId
        );
    }
}