package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "sale_detail")
@SQLDelete(sql = "UPDATE sale_detail SET deleted=1 WHERE id=?")
public class SaleDetail {
    private Integer id;
    private Integer amount;
    private BigDecimal unitPrice;
    private Byte iva;
    private Byte deleted;
    private Timestamp createdAt = Timestamp.from(Instant.now());
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Integer saleInvoiceId;
    private Integer productId;
    private SaleInvoice saleInvoiceBySaleInvoiceId;
    private Product productByProductId;

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
    @Column(name = "unit_price", nullable = false, precision = 5)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "iva")
    public Byte getIva() {
        return iva;
    }

    public void setIva(Byte iva) {
        this.iva = iva;
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

    @Basic
    @Column(name = "product_id", nullable = false)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDetail that = (SaleDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) &&
               Objects.equals(unitPrice, that.unitPrice) && Objects.equals(iva, that.iva) &&
               Objects.equals(deleted, that.deleted) && Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(modifiedAt, that.modifiedAt) &&
               Objects.equals(deletedAt, that.deletedAt) &&
               Objects.equals(saleInvoiceId, that.saleInvoiceId) &&
               Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, amount, unitPrice, iva, deleted, createdAt, modifiedAt, deletedAt, saleInvoiceId, productId);
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

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }
}
