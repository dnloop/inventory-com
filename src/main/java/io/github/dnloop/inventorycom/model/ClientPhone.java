package io.github.dnloop.inventorycom.model;

import io.github.dnloop.inventorycom.support.validator.Phone;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

/**
 * <h4>Client Phone</h4>
 * <p>
 * Entity used to represent client's phones on the database.
 * Transient field is used for validation. If the number is valid, the implementation class must set
 * the number provided by {@link PhoneNumber#getValue()}
 * </p>
 */
@Entity
@Table(name = "client_phone")
@SQLDelete(sql = "UPDATE client_phone SET deleted=1 WHERE id=?")
public class ClientPhone {
    private Integer id;
    @Phone(message = "{client.phone.invalid}")
    private transient PhoneNumber phoneNumber;
    private String number;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer clientId;
    private Client clientByClientId;

    public ClientPhone() {}

    public ClientPhone(PhoneNumber phoneNumber, String number, Integer clientId) {
        this.phoneNumber = phoneNumber;
        this.number = number;
        this.clientId = clientId;
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
    @Column(name = "number", nullable = false, length = 12)
    public String getNumber() {
        return number;
    }

    /**
     * Store number to database.
     */
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
    } // TODO review equals

    @Override
    public int hashCode() {
        return Objects.hash(id, number, deleted, createdAt, modifiedAt, deletedAt, clientId);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false, insertable = false,
                updatable = false)
    public Client getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(Client clientByClientId) {
        this.clientByClientId = clientByClientId;
    }

    @Override
    public String toString() {
        return "ClientPhone{" +
               "id=" + id +
               ", number='" + number + '\'' +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", clientId=" + clientId +
               '}';
    }
    /**
     * Field used for phone validation. Once it passes user must ensure it is stored
     * with {@link ClientPhone#setNumber(String)}.
     */
    @Transient
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
