package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "purchase_share")
@SQLDelete(sql = "UPDATE purchase_share SET deleted = 1 WHERE id= ?")
public class PurchaseShare {
    private Integer id;
    private Integer number;
    private Date paymentDate;
    private Date dueDate;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer purchaseInvoiceId;
    private PurchaseInvoice purchaseInvoiceByPurchaseInvoiceId;

    public PurchaseShare() {}

    public PurchaseShare(
            Integer number, Date paymentDate, Date dueDate, Byte deleted, Timestamp createdAt, Timestamp modifiedAt,
            Timestamp deletedAt,
            Integer purchaseInvoiceId
    ) {
        this.number = number;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
        this.purchaseInvoiceId = purchaseInvoiceId;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "purchase_share_sequence",
            allocationSize = 100
    )
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number", nullable = false)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "payment_date")
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Basic
    @Column(name = "due_date", nullable = false)
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
    @Column(name = "purchase_invoice_id", nullable = false)
    public Integer getPurchaseInvoiceId() {
        return purchaseInvoiceId;
    }

    public void setPurchaseInvoiceId(Integer purchaseInvoiceId) {
        this.purchaseInvoiceId = purchaseInvoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseShare that = (PurchaseShare) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) &&
               Objects.equals(paymentDate, that.paymentDate) && Objects.equals(dueDate, that.dueDate) &&
               Objects.equals(deleted, that.deleted) && Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) &&
               Objects.equals(purchaseInvoiceId, that.purchaseInvoiceId);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, number, paymentDate, dueDate, deleted, createdAt, modifiedAt, deletedAt, purchaseInvoiceId);
    }

    @ManyToOne
    @JoinColumn(name = "purchase_invoice_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public PurchaseInvoice getPurchaseInvoiceByPurchaseInvoiceId() {
        return purchaseInvoiceByPurchaseInvoiceId;
    }

    public void setPurchaseInvoiceByPurchaseInvoiceId(
            PurchaseInvoice purchaseInvoiceByPurchaseInvoiceId
    ) {
        this.purchaseInvoiceByPurchaseInvoiceId = purchaseInvoiceByPurchaseInvoiceId;
    }

    @Override
    public String toString() {
        return "PurchaseShare{" +
               "id=" + id +
               ", number=" + number +
               ", paymentDate=" + paymentDate +
               ", dueDate=" + dueDate +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               '}';
    }
}
