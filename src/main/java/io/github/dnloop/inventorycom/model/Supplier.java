package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "supplier")
@SQLDelete(sql = "UPDATE supplier SET deleted=1 WHERE id=?")
public class Supplier {
    private Integer id;
    private String name;
    private String description;
    private String mail;
    private Integer localityId;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private String address;
    private Long cuit;
    private Collection<PurchaseInvoice
            > purchaseInvoicesById;
    private Locality localityByLocalityId;
    private Collection<SupplierCatalog> supplierCatalogsById;
    private Collection<SupplierPhone> supplierPhonesById;

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
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;
        Supplier supplier = (Supplier) o;
        return getId().equals(supplier.getId()) &&
               getName().equals(supplier.getName()) &&
               getDescription().equals(supplier.getDescription()) &&
               getMail().equals(supplier.getMail()) &&
               getLocalityId().equals(supplier.getLocalityId()) &&
               getDeleted().equals(supplier.getDeleted()) &&
               getCreatedAt().equals(supplier.getCreatedAt()) &&
               getModifiedAt().equals(supplier.getModifiedAt()) &&
               getDeletedAt().equals(supplier.getDeletedAt()) &&
               getAddress().equals(supplier.getAddress()) &&
               getCuit().equals(supplier.getCuit()) &&
               getLocalityByLocalityId().equals(supplier.getLocalityByLocalityId());
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
                insertable = false, updatable = false)
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
