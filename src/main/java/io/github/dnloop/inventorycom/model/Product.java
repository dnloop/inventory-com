package io.github.dnloop.inventorycom.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Product {
    private Integer id;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Integer categoryId;
    private Timestamp deletedAt;
    private String image;
    private String presentation;
    private Category categoryByCategoryId;
    private Collection<PurchaseDetail> purchaseDetailsById;
    private Collection<SaleDetail> saleDetailsById;
    private Collection<SupplierCatalog> supplierCatalogsById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 140)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "stock", nullable = false)
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 4)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
    @Column(name = "category_id", nullable = false)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
    @Column(name = "image", length = 500)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "presentation", length = 100)
    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(description, product.description) &&
               Objects.equals(stock, product.stock) && Objects.equals(price, product.price) &&
               Objects.equals(deleted, product.deleted) &&
               Objects.equals(createdAt, product.createdAt) &&
               Objects.equals(modifiedAt, product.modifiedAt) &&
               Objects.equals(categoryId, product.categoryId) &&
               Objects.equals(deletedAt, product.deletedAt) && Objects.equals(image, product.image) &&
               Objects.equals(presentation, product.presentation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, stock, price, deleted, createdAt, modifiedAt, categoryId, deletedAt, image,
                            presentation
        );
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, insertable = false,
                updatable = false)
    public Category getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(Category categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    @OneToMany(mappedBy = "productByProductId")
    public Collection<PurchaseDetail> getPurchaseDetailsById() {
        return purchaseDetailsById;
    }

    public void setPurchaseDetailsById(
            Collection<PurchaseDetail> purchaseDetailsById
    ) {
        this.purchaseDetailsById = purchaseDetailsById;
    }

    @OneToMany(mappedBy = "productByProductId")
    public Collection<SaleDetail> getSaleDetailsById() {
        return saleDetailsById;
    }

    public void setSaleDetailsById(Collection<SaleDetail> saleDetailsById) {
        this.saleDetailsById = saleDetailsById;
    }

    @OneToMany(mappedBy = "productByProductId")
    public Collection<SupplierCatalog> getSupplierCatalogsById() {
        return supplierCatalogsById;
    }

    public void setSupplierCatalogsById(
            Collection<SupplierCatalog> supplierCatalogsById
    ) {
        this.supplierCatalogsById = supplierCatalogsById;
    }
}
