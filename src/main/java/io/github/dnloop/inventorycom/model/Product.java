package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

/**
 * <h4>Product</h4>
 * <p>
 * Entity used to represent products. Due to constraint violations, Details must be loaded first.
 */
@Entity
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted=1 WHERE id=?")
public class Product {
    private Integer id;
    private String description;
    private Integer stock = 0;
    private BigDecimal price;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Integer categoryId;
    private Timestamp deletedAt;
    private String image = "";
    private Integer detailId = 1;
    private Category categoryByCategoryId;
    private ProductDetail productDetailByDetailId;
    private Collection<PurchaseDetail> purchaseDetailsById;
    private Collection<SaleDetail> saleDetailsById;
    private Collection<SupplierCatalog> supplierCatalogsById;

    public Product() {}

    public Product(String description, Integer stock, BigDecimal price, Integer categoryId, Integer detailId) {
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.detailId = detailId;
        this.categoryId = categoryId;
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
    @Column(name = "detail_id", nullable = false)
    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(description, that.description) &&
               Objects.equals(stock, that.stock) &&
               Objects.equals(price, that.price) &&
               Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(categoryId, that.categoryId) &&
               Objects.equals(deletedAt, that.deletedAt) &&
               Objects.equals(image, that.image) &&
               Objects.equals(detailId, that.detailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, stock, price, deleted, createdAt, modifiedAt, categoryId, deletedAt, image,
                            detailId
        );
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Category getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(Category categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    @ManyToOne
    @JoinColumn(name = "detail_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public ProductDetail getProductDetailByDetailId() {
        return productDetailByDetailId;
    }

    public void setProductDetailByDetailId(ProductDetail productDetailByDetailId) {
        this.productDetailByDetailId = productDetailByDetailId;
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

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", stock=" + stock +
               ", price=" + price +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", categoryId=" + categoryId +
               ", deletedAt=" + deletedAt +
               ", image='" + image + '\'' +
               ", detailId=" + detailId +
               '}';
    }
}
