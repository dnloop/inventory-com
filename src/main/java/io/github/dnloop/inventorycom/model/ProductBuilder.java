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

import java.sql.Timestamp;
import java.time.Instant;

public class ProductBuilder {
    private String description;
    private Integer stock = 1;
    private String productCode;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Integer categoryId;
    private Timestamp deletedAt;
    private String image;
    private Integer detailId = 1;

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public ProductBuilder setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public ProductBuilder setDeleted(Byte deleted) {
        this.deleted = deleted;
        return this;
    }

    public ProductBuilder setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ProductBuilder setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public ProductBuilder setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ProductBuilder setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public ProductBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public ProductBuilder setDetailId(Integer detailId) {
        this.detailId = detailId;
        return this;
    }

    public Product createProduct() {
        return new Product(
                description, stock, productCode, deleted, createdAt, modifiedAt, categoryId, deletedAt, image,
                detailId
        );
    }
}