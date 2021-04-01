package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "category")
@SQLDelete(sql = "UPDATE category SET deleted=0 WHERE id=?")
public class Category {
    private Integer id;
    private String description;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Collection<Product> productsById;
    private Collection<CategoryLevel> categoryLevelsById;

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
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
               Objects.equals(description, category.description) &&
               Objects.equals(deleted, category.deleted) &&
               Objects.equals(createdAt, category.createdAt) &&
               Objects.equals(modifiedAt, category.modifiedAt) &&
               Objects.equals(deletedAt, category.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, deleted, createdAt, modifiedAt, deletedAt);
    }

    @OneToMany(mappedBy = "categoryByCategoryId")
    public Collection<Product> getProductsById() {
        return productsById;
    }

    public void setProductsById(Collection<Product> productsById) {
        this.productsById = productsById;
    }

    @OneToMany(mappedBy = "categoryByCategoryId")
    public Collection<CategoryLevel> getCategoryLevelsById() {
        return categoryLevelsById;
    }

    public void setCategoryLevelsById(Collection<CategoryLevel> categoryLevelsById) {
        this.categoryLevelsById = categoryLevelsById;
    }
}
