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

public class SaleDetailBuilder {
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private Byte iva;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer saleInvoiceId;
    private Integer productId;

    public SaleDetailBuilder() {}

    public SaleDetailBuilder setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public SaleDetailBuilder setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public SaleDetailBuilder setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public SaleDetailBuilder setIva(Byte iva) {
        this.iva = iva;
        return this;
    }

    public SaleDetailBuilder setDeleted(Byte deleted) {
        this.deleted = deleted;
        return this;
    }

    public SaleDetailBuilder setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SaleDetailBuilder setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public SaleDetailBuilder setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public SaleDetailBuilder setSaleInvoiceId(Integer saleInvoiceId) {
        this.saleInvoiceId = saleInvoiceId;
        return this;
    }

    public SaleDetailBuilder setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public SaleDetail createSaleDetail() {
        return new SaleDetail(
                amount, unitPrice, subtotal, iva, deleted, createdAt, modifiedAt, deletedAt, saleInvoiceId, productId);
    }
}