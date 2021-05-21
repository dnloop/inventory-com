package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

/**
 * <h4>Category Level</h4>
 *
 * <p>In order to insert a new level a {@link Category} must exist before.</p>
 * <p>
 * A generic category of level 0 indicates there is no category assigned in the hierarchy, this can happen
 * when a record is deleted, it is temporarily removed to be adjusted by the user.
 * </p>
 * <p></p>
 * <p>
 * Every time a new record is inserted it must check in which hierarchy is intended querying the current level value
 * then add +1. This must go in a transaction due to having two queries in one call.
 * </p>
 * <p></p>
 * <p>
 * When a branch is deleted all related product categories must be updated, this presents a problem when the stored
 * records increases. This behaviour must not be allowed, only empty categories can be deleted. Normal operations
 * include query and modification.
 * </p>
 */
@Entity
@Table(name = "category_level")
@SQLDelete(sql = "UPDATE sub_category SET deleted=1 WHERE id=?")
public class CategoryLevel {
    private Integer id;
    @NotNull(message = "{category.required}")
    private Integer categoryId;
    private Integer l1 = 0;
    private Integer l2 = 0;
    private Integer l3 = 0;
    private Integer l4 = 0;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Category categoryByCategoryId;

    public CategoryLevel() {}

    public CategoryLevel(Integer categoryId, Level level) {
        this.categoryId = categoryId;
        this.l1 = level.getLevelOne();
        this.l2 = level.getLevelTwo();
        this.l3 = level.getLevelThree();
        this.l4 = level.getLevelFour();

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
    @Column(name = "category_id", nullable = false)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "L1")
    public Integer getL1() { return l1; }

    public void setL1(Integer l1) { this.l1 = l1; }

    @Basic
    @Column(name = "L2")
    public Integer getL2() { return l2; }

    public void setL2(Integer l2) { this.l2 = l2; }

    @Basic
    @Column(name = "L3")
    public Integer getL3() { return l3; }

    public void setL3(Integer l3) { this.l3 = l3; }

    @Basic
    @Column(name = "L4")
    public Integer getL4() { return l4; }

    public void setL4(Integer l4) { this.l4 = l4; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryLevel that = (CategoryLevel) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(categoryId, that.categoryId) &&
               Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, deleted, createdAt, modifiedAt, deletedAt);
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

    @Override
    public String toString() {
        return "CategoryLevel{" +
               "id=" + id +
               ", categoryId=" + categoryId +
               ", l1=" + l1 +
               ", l2=" + l2 +
               ", l3=" + l3 +
               ", l4=" + l4 +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", categoryByCategoryId=" + categoryByCategoryId +
               '}';
    }
}
