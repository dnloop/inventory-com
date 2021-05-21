package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "supplier_catalog")
@SQLDelete(sql = "UPDATE supplier_catalog SET deleted=1 WHERE id=?")
public class SupplierCatalog {
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    @NotEmpty(message = "{supplierCatalog.code}")
    @Size(min = 1, max = 20, message = "{field.size}")
    private String catalogCode;
    private Integer id;
    private Supplier supplierBySupplierId;
    private Product productByProductId;

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
    @Column(name = "catalog_code", nullable = false, length = 20)
    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierCatalog that = (SupplierCatalog) o;
        return Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) && Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(catalogCode, that.catalogCode) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modifiedAt, deletedAt, deleted, createdAt, catalogCode, id);
    }

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false, insertable = false,
                updatable = false)
    public Supplier getSupplierBySupplierId() {
        return supplierBySupplierId;
    }

    public void setSupplierBySupplierId(Supplier supplierBySupplierId) {
        this.supplierBySupplierId = supplierBySupplierId;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable = false,
                updatable = false)
    public Product getProductByProductId() { return productByProductId; }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }

    @Override
    public String toString() {
        return "SupplierCatalog{" +
               "modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", catalogCode='" + catalogCode + '\'' +
               ", id=" + id +
               '}';
    }
}
