package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "client_phone", schema = "inventario_comercial")
@SQLDelete(sql = "UPDATE client_phone SET deleted=1 WHERE id=?")
@Where(clause = "deleted = 0")
public class ClientPhone {
    private Integer id;
    private String number;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer clientId;
    private Client clientByClientId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number", nullable = false, length = 12)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
    @Column(name = "client_id", nullable = false)
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientPhone that = (ClientPhone) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) &&
               Objects.equals(deleted, that.deleted) && Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) && Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, deleted, createdAt, modifiedAt, deletedAt, clientId);
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false, insertable = false,
                updatable = false)
    public Client getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(Client clientByClientId) {
        this.clientByClientId = clientByClientId;
    }
}
