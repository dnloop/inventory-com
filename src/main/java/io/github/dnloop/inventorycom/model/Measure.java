package io.github.dnloop.inventorycom.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "measure", schema = "inventario_comercial")
public class Measure {
    private Integer id;
    private String type;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Double lenght;
    private Double width;
    private Double diameter;
    private Collection<ProductDetail> productDetailsById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 4)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "lenght", precision = 2)
    public Double getLenght() {
        return lenght;
    }

    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    @Basic
    @Column(name = "width", precision = 2)
    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Basic
    @Column(name = "diameter", precision = 2)
    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measure that = (Measure) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(type, that.type) &&
               Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) &&
               Objects.equals(lenght, that.lenght) &&
               Objects.equals(width, that.width) &&
               Objects.equals(diameter, that.diameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, deleted, createdAt, modifiedAt, deletedAt, lenght, width, diameter);
    }

    @OneToMany(mappedBy = "measureByMeasureId")
    public Collection<ProductDetail> getProductDetailsById() {
        return productDetailsById;
    }

    public void setProductDetailsById(
            Collection<ProductDetail> productDetailsById
    ) {
        this.productDetailsById = productDetailsById;
    }
}
