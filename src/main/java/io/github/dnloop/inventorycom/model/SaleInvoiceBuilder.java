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

public class SaleInvoiceBuilder {
    private Integer number;
    private Timestamp generationDate;
    private BigDecimal surcharge;
    private BigDecimal total;
    private String invoiceType;
    private String paymentType;
    private Byte deleted;
    private Timestamp createdAt;
    private BigDecimal discount;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer clientId;

    public SaleInvoiceBuilder() {}

    public SaleInvoiceBuilder setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public SaleInvoiceBuilder setGenerationDate(Timestamp generationDate) {
        this.generationDate = generationDate;
        return this;
    }

    public SaleInvoiceBuilder setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
        return this;
    }

    public SaleInvoiceBuilder setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public SaleInvoiceBuilder setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public SaleInvoiceBuilder setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public SaleInvoiceBuilder setDeleted(Byte deleted) {
        this.deleted = deleted;
        return this;
    }

    public SaleInvoiceBuilder setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SaleInvoiceBuilder setDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public SaleInvoiceBuilder setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public SaleInvoiceBuilder setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public SaleInvoiceBuilder setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaleInvoice createSaleInvoice() {
        return new SaleInvoice(
                number, generationDate, surcharge, total, invoiceType, paymentType, deleted, createdAt, discount,
                modifiedAt, deletedAt, clientId
        );
    }
}