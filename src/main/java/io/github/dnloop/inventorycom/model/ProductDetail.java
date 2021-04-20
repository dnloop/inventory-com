package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * <h4>Product Detail</h4>
 * <p>
 * Requires a default 'generic' value preloaded. This allows to have products that won't require
 * further details.
 */
@Entity
@Table(name = "product_detail")
@SQLDelete(sql = "UPDATE product_detail SET deleted=1 WHERE id=?")
public class ProductDetail {
    private Integer id;
    private String brand;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer measureId;
    private Integer presentationId;
    private Integer materialId;
    private Collection<Product> productsById;
    private Measure measureByMeasureId;
    private Presentation presentationByPresentationId;
    private Material materialByMaterialId;

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
    @Column(name = "brand", nullable = false, length = 140)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
    @Column(name = "measure_id", nullable = false)
    public Integer getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }

    @Basic
    @Column(name = "presentation_id", nullable = false)
    public Integer getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(Integer presentationId) {
        this.presentationId = presentationId;
    }

    @Basic
    @Column(name = "material_id", nullable = false)
    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetail that = (ProductDetail) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(brand, that.brand) &&
               Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) &&
               Objects.equals(measureId, that.measureId) &&
               Objects.equals(presentationId, that.presentationId) &&
               Objects.equals(materialId, that.materialId);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, brand, deleted, createdAt, modifiedAt, deletedAt, measureId, presentationId, materialId);
    }

    @OneToMany(mappedBy = "productDetailByDetailId")
    public Collection<Product> getProductsById() {
        return productsById;
    }

    public void setProductsById(Collection<Product> productsById) {
        this.productsById = productsById;
    }

    @ManyToOne
    @JoinColumn(name = "measure_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Measure getMeasureByMeasureId() {
        return measureByMeasureId;
    }

    public void setMeasureByMeasureId(Measure measureByMeasureId) {
        this.measureByMeasureId = measureByMeasureId;
    }

    @ManyToOne
    @JoinColumn(name = "presentation_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Presentation getPresentationByPresentationId() {
        return presentationByPresentationId;
    }

    public void setPresentationByPresentationId(Presentation presentationByPresentationId) {
        this.presentationByPresentationId = presentationByPresentationId;
    }

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Material getMaterialByMaterialId() {
        return materialByMaterialId;
    }

    public void setMaterialByMaterialId(Material materialByMaterialId) {
        this.materialByMaterialId = materialByMaterialId;
    }
}
