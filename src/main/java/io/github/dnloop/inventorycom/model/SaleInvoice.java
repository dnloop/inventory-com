package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

/**
 * <h4>Sale Invoice</h4>
 * <p>
 * Entity representing a sale invoice. Constraints must be enforced to
 * prevent record deletion as well as related entities.
 * </p>
 * <p>
 * Future implementations will include sale point number.
 * </p>
 * <p>
 * Payment and invoice type don't require constraints due to extracting
 * default values from UI. This means that no matter the scenario, it
 * always receives a value.
 * </p>
 */
@Entity
@Table(name = "sale_invoice")
@SQLDelete(sql = "UPDATE sale_invoice SET deleted=1 WHERE id=?")
public class SaleInvoice {
    private Integer id;
    @Min(value = 1, message = "{number.min}")
    private Integer number;
    @FutureOrPresent(message = "{invoice.dateFoP}")
    private Timestamp generationDate;
    @Digits(integer = 15, fraction = 2, message = "{price.digit}")
    private BigDecimal surcharge = BigDecimal.ZERO;
    @Digits(integer = 15, fraction = 2, message = "{price.digit}")
    private BigDecimal total = BigDecimal.ZERO;
    private String invoiceType;
    private String paymentType;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    @Digits(integer = 15, fraction = 2, message = "{price.digit}")
    private BigDecimal discount = BigDecimal.ZERO;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    @NotNull(message = "{client.required}")
    private Integer clientId;
    private Collection<SaleDetail> saleDetailsById;
    private Collection<SaleShare> saleSharesById;

    public SaleInvoice() {}

    public SaleInvoice(
            Integer number, Timestamp generationDate, BigDecimal surcharge, BigDecimal total, String invoiceType,
            String paymentType,
            Byte deleted,
            Timestamp createdAt,
            BigDecimal discount,
            Timestamp modifiedAt,
            Timestamp deletedAt,
            Integer clientId
    ) {
        this.number = number;
        this.generationDate = generationDate;
        this.surcharge = surcharge;
        this.total = total;
        this.invoiceType = invoiceType;
        this.paymentType = paymentType;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.discount = discount;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
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
    @Column(name = "surcharge", precision = 15, scale = 5)
    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    @Basic
    @Column(name = "total", nullable = false, precision = 15, scale = 5)
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
    @Column(name = "discount", precision = 15, scale = 5)
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
