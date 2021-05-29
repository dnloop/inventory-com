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
 * <h4>Purchase Invoice</h4>
 * <p>
 * Entity representing a purchase invoice. Constraints must be enforced to
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
@Table(name = "purchase_invoice")
@SQLDelete(sql = "UPDATE purchase_invoice SET deleted = 1 WHERE id= ?")
public class PurchaseInvoice {
    private Integer id;
    @Min(value = 1, message = "{purchaseInvoice.number.min}")
    private Integer number;
    @FutureOrPresent(message = "{invoice.dateFoP}")
    private Timestamp generationDate;
    private String paymentType;
    private String invoiceType;
    @Digits(integer = 15, fraction = 2, message = "{purchaseInvoice.surcharge.digit}")
    private BigDecimal surcharge = BigDecimal.ZERO;
    @Digits(integer = 15, fraction = 2, message = "{purchaseInvoice.discount.digit}")
    private BigDecimal discount = BigDecimal.ZERO;
    @Digits(integer = 15, fraction = 2, message = "{purchaseInvoice.total.digit}")
    private BigDecimal total = BigDecimal.ZERO;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    @NotNull(message = "{purchaseInvoice.supplier.required}")
    private Integer supplierId;
    private Collection<PurchaseDetail> purchaseDetailsById;
    private Supplier supplierBySupplierId;
    private Collection<PurchaseShare> purchaseSharesById;

    public PurchaseInvoice() {}

    public PurchaseInvoice(
            Integer number, Timestamp generationDate, String paymentType, String invoiceType, BigDecimal surcharge,
            BigDecimal discount,
            BigDecimal total,
            Byte deleted,
            Timestamp createdAt,
            Timestamp modifiedAt,
            Timestamp deletedAt,
            Integer supplierId
    ) {
        this.number = number;
        this.generationDate = generationDate;
        this.paymentType = paymentType;
        this.invoiceType = invoiceType;
        this.surcharge = surcharge;
        this.discount = discount;
        this.total = total;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
        this.supplierId = supplierId;
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
    @Column(name = "payment_type", length = 8)
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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
    @Column(name = "surcharge", precision = 15, scale = 5)
    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
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
    @Column(name = "total", nullable = false, precision = 15, scale = 5)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
    @Column(name = "supplier_id", nullable = false)
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseInvoice that = (PurchaseInvoice) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) &&
               Objects.equals(generationDate, that.generationDate) &&
               Objects.equals(paymentType, that.paymentType) &&
               Objects.equals(invoiceType, that.invoiceType) &&
               Objects.equals(surcharge, that.surcharge) && Objects.equals(discount, that.discount) &&
               Objects.equals(total, that.total) && Objects.equals(deleted, that.deleted) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) && Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, number, generationDate, paymentType, invoiceType, surcharge, discount, total, deleted,
                      createdAt,
                      modifiedAt, deletedAt, supplierId
                );
    }

    @OneToMany(mappedBy = "purchaseInvoiceByPurchaseInvoiceId")
    public Collection<PurchaseDetail> getPurchaseDetailsById() {
        return purchaseDetailsById;
    }

    public void setPurchaseDetailsById(
            Collection<PurchaseDetail> purchaseDetailsById
    ) {
        this.purchaseDetailsById = purchaseDetailsById;
    }

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Supplier getSupplierBySupplierId() {
        return supplierBySupplierId;
    }

    public void setSupplierBySupplierId(Supplier supplierBySupplierId) {
        this.supplierBySupplierId = supplierBySupplierId;
    }

    @OneToMany(mappedBy = "purchaseInvoiceByPurchaseInvoiceId")
    public Collection<PurchaseShare> getPurchaseSharesById() {
        return purchaseSharesById;
    }

    public void setPurchaseSharesById(Collection<PurchaseShare> purchaseSharesById) {
        this.purchaseSharesById = purchaseSharesById;
    }

    @Override
    public String toString() {
        return "PurchaseInvoice{" +
               "id=" + id +
               ", number=" + number +
               ", generationDate=" + generationDate +
               ", paymentType='" + paymentType + '\'' +
               ", invoiceType='" + invoiceType + '\'' +
               ", surcharge=" + surcharge +
               ", discount=" + discount +
               ", total=" + total +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", supplierId=" + supplierId +
               '}';
    }
}

