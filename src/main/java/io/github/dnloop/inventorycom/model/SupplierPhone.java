package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "supplier_phone")
@SQLDelete(sql = "UPDATE supplier_phone SET deleted=1 WHERE id=?")
public class SupplierPhone {
    private Integer id;
    private String number;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer supplierId;
    private Supplier supplierBySupplierId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number", nullable = false, length = 12)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "modified_at")
    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Basic
    @Column(name = "deleted_at")
    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Basic
    @Column(name = "supplier_id", nullable = false)
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierPhone that = (SupplierPhone) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) &&
               Objects.equals(deleted, that.deleted) && Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) && Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, deleted, createdAt, modifiedAt, deletedAt, supplierId);
    }

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Supplier getSupplierBySupplierId() {
        return supplierBySupplierId;
    }

    public void setSupplierBySupplierId(Supplier supplierBySupplierId) {
        this.supplierBySupplierId = supplierBySupplierId;
    }

    @Override
    public String toString() {
        return "SupplierPhone{" +
               "id=" + id +
               ", number='" + number + '\'' +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", supplierId=" + supplierId +
               '}';
    }
}
