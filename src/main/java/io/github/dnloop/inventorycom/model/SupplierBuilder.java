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

public class SupplierBuilder {
    private String name;
    private String description;
    private String mail;
    private Integer localityId;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private String address;
    private Long cuit;

    public SupplierBuilder() {}

    public SupplierBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SupplierBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public SupplierBuilder setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public SupplierBuilder setLocalityId(Integer localityId) {
        this.localityId = localityId;
        return this;
    }

    public SupplierBuilder setDeleted(Byte deleted) {
        this.deleted = deleted;
        return this;
    }

    public SupplierBuilder setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SupplierBuilder setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public SupplierBuilder setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public SupplierBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public SupplierBuilder setCuit(Long cuit) {
        this.cuit = cuit;
        return this;
    }

    public Supplier createSupplier() {
        return new Supplier(
                name, description, mail, localityId, deleted, createdAt, modifiedAt, deletedAt, address, cuit);
    }
}