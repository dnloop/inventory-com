package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "sale_share")
@SQLDelete(sql = "UPDATE sale_share SET deleted=1 WHERE id=?")
public class SaleShare {
    private Integer id;
    private Integer number;
    private Date paymentDate;
    private Date dueDate;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer saleInvoiceId;
    private SaleInvoice saleInvoiceBySaleInvoiceId;

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
    @Column(name = "sale_invoice_id", nullable = false)
    public Integer getSaleInvoiceId() {
        return saleInvoiceId;
    }

    public void setSaleInvoiceId(Integer saleInvoiceId) {
        this.saleInvoiceId = saleInvoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleShare saleShare = (SaleShare) o;
        return Objects.equals(id, saleShare.id) && Objects.equals(number, saleShare.number) &&
               Objects.equals(paymentDate, saleShare.paymentDate) &&
               Objects.equals(dueDate, saleShare.dueDate) &&
               Objects.equals(deleted, saleShare.deleted) &&
               Objects.equals(createdAt, saleShare.createdAt) &&
               Objects.equals(modifiedAt, saleShare.modifiedAt) &&
               Objects.equals(deletedAt, saleShare.deletedAt) &&
               Objects.equals(saleInvoiceId, saleShare.saleInvoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, paymentDate, dueDate, deleted, createdAt, modifiedAt, deletedAt, saleInvoiceId);
    }

    @ManyToOne
    @JoinColumn(name = "sale_invoice_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public SaleInvoice getSaleInvoiceBySaleInvoiceId() {
        return saleInvoiceBySaleInvoiceId;
    }

    public void setSaleInvoiceBySaleInvoiceId(SaleInvoice saleInvoiceBySaleInvoiceId) {
        this.saleInvoiceBySaleInvoiceId = saleInvoiceBySaleInvoiceId;
    }

    @Override
    public String toString() {
        return "SaleShare{" +
               "id=" + id +
               ", number=" + number +
               ", paymentDate=" + paymentDate +
               ", dueDate=" + dueDate +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", saleInvoiceId=" + saleInvoiceId +
               '}';
    }
}
