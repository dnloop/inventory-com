package io.github.dnloop.inventorycom.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "supplier", schema = "inventario_comercial")
public class Supplier {
    private Integer id;
    private Object name;
    private Object description;
    private String mail;
    private Integer localityId;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private String address;
    private String cuit;
    private Collection<PurchaseInvoice
            > purchaseInvoicesById;
    private Locality localityByLocalityId;
    private Collection<SupplierCatalog> supplierCatalogsById;
    private Collection<SupplierPhone> supplierPhonesById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    @Basic
    @Column(name = "mail", length = 320)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "locality_id", nullable = false)
    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
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
    @Column(name = "created_at", nullable = false)
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
    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "cuit", nullable = false, length = 11)
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier that = (Supplier) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(name, that.name) &&
               Objects.equals(description, that.description) &&
               Objects.equals(mail, that.mail) &&
               Objects.equals(localityId, that.localityId) &&
               Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) &&
               Objects.equals(address, that.address) &&
               Objects.equals(cuit, that.cuit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, description,
                mail, localityId, deleted,
                createdAt, modifiedAt, deletedAt,
                address, cuit
        );
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public Collection<PurchaseInvoice> getPurchaseInvoicesById() {
        return purchaseInvoicesById;
    }

    public void setPurchaseInvoicesById(
            Collection<PurchaseInvoice> purchaseInvoicesById
    ) {
        this.purchaseInvoicesById = purchaseInvoicesById;
    }

    @ManyToOne
    @JoinColumn(name = "locality_id", referencedColumnName = "id", nullable = false,
                table = "supplier", insertable = false, updatable = false)
    public Locality getLocalityByLocalityId() {
        return localityByLocalityId;
    }

    public void setLocalityByLocalityId(Locality localityByLocalityId) {
        this.localityByLocalityId = localityByLocalityId;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public Collection<SupplierCatalog> getSupplierCatalogsById() {
        return supplierCatalogsById;
    }

    public void setSupplierCatalogsById(
            Collection<SupplierCatalog> supplierCatalogsById
    ) {
        this.supplierCatalogsById = supplierCatalogsById;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public Collection<SupplierPhone> getSupplierPhonesById() {
        return supplierPhonesById;
    }

    public void setSupplierPhonesById(
            Collection<SupplierPhone> supplierPhonesById
    ) {
        this.supplierPhonesById = supplierPhonesById;
    }
}
