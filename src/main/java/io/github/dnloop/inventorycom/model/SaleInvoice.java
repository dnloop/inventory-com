package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "sale_invoice")
@SQLDelete(sql = "UPDATE sale_invoice SET deleted=1 WHERE id=?")
public class SaleInvoice {
    private Integer id;
    private Integer number;
    private Timestamp generationDate;
    private BigDecimal surcharge;
    private BigDecimal total;
    private String invoiceType;
    private String paymentType;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private BigDecimal discount;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer clientId;
    private Collection<SaleDetail> saleDetailsById;
    private Collection<SaleShare> saleSharesById;

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
    @Column(name = "generation_date", nullable = false)
    public Timestamp getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(Timestamp generationDate) {
        this.generationDate = generationDate;
    }

    @Basic
    @Column(name = "surcharge", precision = 5)
    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    @Basic
    @Column(name = "total", nullable = false, precision = 5)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Basic
    @Column(name = "invoice_type", length = 3)
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Basic
    @Column(name = "payment_type", length = 8)
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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
    @Column(name = "discount", precision = 4)
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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
        SaleInvoice that = (SaleInvoice) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) &&
               Objects.equals(generationDate, that.generationDate) &&
               Objects.equals(surcharge, that.surcharge) && Objects.equals(total, that.total) &&
               Objects.equals(invoiceType, that.invoiceType) &&
               Objects.equals(paymentType, that.paymentType) && Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) && Objects.equals(discount, that.discount) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) && Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, number, generationDate, surcharge, total, invoiceType, paymentType, deleted, createdAt,
                      discount,
                      modifiedAt, deletedAt, clientId
                );
    }

    @OneToMany(mappedBy = "saleInvoiceBySaleInvoiceId")
    public Collection<SaleDetail> getSaleDetailsById() {
        return saleDetailsById;
    }

    public void setSaleDetailsById(Collection<SaleDetail> saleDetailsById) {
        this.saleDetailsById = saleDetailsById;
    }

    @OneToMany(mappedBy = "saleInvoiceBySaleInvoiceId")
    public Collection<SaleShare> getSaleSharesById() {
        return saleSharesById;
    }

    public void setSaleSharesById(Collection<SaleShare> saleSharesById) {
        this.saleSharesById = saleSharesById;
    }

    @Override
    public String toString() {
        return "SaleInvoice{" +
               "id=" + id +
               ", number=" + number +
               ", generationDate=" + generationDate +
               ", surcharge=" + surcharge +
               ", total=" + total +
               ", invoiceType='" + invoiceType + '\'' +
               ", paymentType='" + paymentType + '\'' +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", discount=" + discount +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", clientId=" + clientId +
               '}';
    }
}
