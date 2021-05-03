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

public class PurchaseDetailBuilder {
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer purchaseInvoiceId;
    private Integer productId;
    private Byte iva;

    public PurchaseDetailBuilder() {}

    public PurchaseDetailBuilder setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public PurchaseDetailBuilder setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public PurchaseDetailBuilder setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public PurchaseDetailBuilder setDeleted(Byte deleted) {
        this.deleted = deleted;
        return this;
    }

    public PurchaseDetailBuilder setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PurchaseDetailBuilder setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public PurchaseDetailBuilder setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public PurchaseDetailBuilder setPurchaseInvoiceId(Integer purchaseInvoiceId) {
        this.purchaseInvoiceId = purchaseInvoiceId;
        return this;
    }

    public PurchaseDetailBuilder setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public PurchaseDetailBuilder setIva(Byte iva) {
        this.iva = iva;
        return this;
    }

    public PurchaseDetail createPurchaseDetail() {
        return new PurchaseDetail(
                amount, unitPrice, subtotal, deleted, createdAt, modifiedAt, deletedAt, purchaseInvoiceId, productId,
                iva
        );
    }
}