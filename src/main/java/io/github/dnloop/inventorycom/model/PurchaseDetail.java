package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

/**
 * <h4>PurchaseDetail</h4>
 * <p>
 * Entity used to represent purchase details.
 */
@Entity
@Table(name = "purchase_detail")
@SQLDelete(sql = "UPDATE purchase_detail SET deleted = 1 WHERE id= ?")
public class PurchaseDetail {
    private Integer id;
    @Range(min = 1, message = "{purchaseDetail.amount.range}")
    private Integer amount;
    @DecimalMin(value = "0.0", inclusive = false, message = "{purchaseDetail.unitPrice.min}")
    @Digits(integer = 15, fraction = 2, message = "{purchaseDetail.unitPrice.digit}")
    private BigDecimal unitPrice;
    @DecimalMin(value = "0.0", inclusive = false, message = "{purchaseDetail.subtotal.min}")
    @Digits(integer = 15, fraction = 2, message = "{purchaseDetail.subtotal.digit}")
    private BigDecimal subtotal = BigDecimal.ZERO;
    private Byte deleted = 0;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    @NotNull(message = "{purchaseDetail.purchaseInvoice.required}")
    private Integer purchaseInvoiceId;
    @NotNull(message = "{purchaseDetail.product.required}")
    private Integer productId;
    private Byte iva;
    private PurchaseInvoice purchaseInvoiceByPurchaseInvoiceId;
    private Product productByProductId;

    public PurchaseDetail() {}

    public PurchaseDetail(
            Integer amount, BigDecimal unitPrice, BigDecimal subtotal, Byte deleted, Timestamp createdAt,
            Timestamp modifiedAt,
            Timestamp deletedAt,
            Integer purchaseInvoiceId,
            Integer productId,
            Byte iva
    ) {
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
        this.purchaseInvoiceId = purchaseInvoiceId;
        this.productId = productId;
        this.iva = iva;
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
    @Column(name = "amount", nullable = false)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "unit_price", nullable = false, precision = 15, scale = 5)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "subtotal", nullable = false, precision = 15, scale = 5)
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
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

    @Basic
    @Column(name = "product_id", nullable = false)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "iva")
    public Byte getIva() {
        return iva;
    }

    public void setIva(Byte iva) {
        this.iva = iva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseDetail that = (PurchaseDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) &&
               Objects.equals(unitPrice, that.unitPrice) && Objects.equals(subtotal, that.subtotal) &&
               Objects.equals(deleted, that.deleted) && Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) &&
               Objects.equals(purchaseInvoiceId, that.purchaseInvoiceId) &&
               Objects.equals(productId, that.productId) && Objects.equals(iva, that.iva);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, amount, unitPrice, subtotal, deleted, createdAt, modifiedAt, deletedAt, purchaseInvoiceId,
                      productId, iva
                );
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

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }

    @Override
    public String toString() {
        return "PurchaseDetail{" +
               "id=" + id +
               ", amount=" + amount +
               ", unitPrice=" + unitPrice +
               ", subtotal=" + subtotal +
               ", deleted=" + deleted +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               ", deletedAt=" + deletedAt +
               ", purchaseInvoiceId=" + purchaseInvoiceId +
               ", productId=" + productId +
               ", iva=" + iva +
               '}';
    }
}
