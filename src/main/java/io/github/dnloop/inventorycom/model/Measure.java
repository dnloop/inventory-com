package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

/**
 * <h4>Measure</h4>
 * <p>
 * Requires a default 'unavailable' value preloaded to ensure Product Detail is never
 * left in an invalid state. This allows for later UI controls to hide the nonexistent
 * value or suggest assignment.
 */
@Entity
@Table(name = "measure")
@SQLDelete(sql = "UPDATE measure SET deleted=1 WHERE id=?")
public class Measure {
    private Integer id;
    private String type; // mm, cm, in...
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Double length;
    private Double width;
    private Double diameter;
    private Collection<ProductDetail> productDetailsById;

    public Measure() {}

    public Measure(String type, Double length, Double width, Double diameter) {
        this.type = type;
        this.length = length;
        this.width = width;
        this.diameter = diameter;
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
    @Column(name = "LENGTH", precision = 2)
    public Double getLength() {
        return length;
    }

    public void setLength(Double lenght) {
        this.length = lenght;
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
               Objects.equals(length, that.length) &&
               Objects.equals(width, that.width) &&
               Objects.equals(diameter, that.diameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, deleted, createdAt, modifiedAt, deletedAt, length, width, diameter);
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

    @Override
    public String toString() {
        return "Measure{" +
               "id=" + id +
               ", type='" + type + '\'' +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", length=" + length +
               ", width=" + width +
               ", diameter=" + diameter +
               '}';
    }
}
