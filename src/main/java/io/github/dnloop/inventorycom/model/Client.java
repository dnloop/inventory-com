package io.github.dnloop.inventorycom.model;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "client")
@SQLDelete(sql = "UPDATE client SET deleted=1 WHERE id=?")
public class Client {
    private Integer id;
    private String name;
    private String surname;
    private String address;
    private Long cuit;
    private String dni;
    private Integer localityId;
    private Byte deleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private String mail;
    private Locality localityByLocalityId;
    private Collection<ClientPhone> clientPhonesById;

    public Client() {}

    public Client(
            String name, String surname, String address,
            Long cuit, String dni, Integer localityId,
            Byte deleted,
            Timestamp createdAt,
            Timestamp modifiedAt,
            Timestamp deletedAt,
            String mail
    ) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cuit = cuit;
        this.dni = dni;
        this.localityId = localityId;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
        this.mail = mail;
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
    @Column(name = "name", nullable = false, length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", length = 140)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "address", length = 280)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "cuit")
    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    @Basic
    @Column(name = "dni", length = 8)
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Basic
    @Column(name = "locality_id", nullable = false)
    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
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
    @Column(name = "mail", length = 320)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) &&
               Objects.equals(surname, client.surname) && Objects.equals(address, client.address) &&
               Objects.equals(cuit, client.cuit) && Objects.equals(dni, client.dni) &&
               Objects.equals(localityId, client.localityId) &&
               Objects.equals(deleted, client.deleted) && Objects.equals(createdAt, client.createdAt) &&
               Objects.equals(modifiedAt, client.modifiedAt) &&
               Objects.equals(deletedAt, client.deletedAt) && Objects.equals(mail, client.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, surname, address, cuit, dni, localityId, deleted, createdAt, modifiedAt, deletedAt,
                mail
        );
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_id", referencedColumnName = "id", nullable = false,
                insertable = false, updatable = false)
    public Locality getLocalityByLocalityId() {
        return localityByLocalityId;
    }

    public void setLocalityByLocalityId(Locality localityByLocalityId) {
        this.localityByLocalityId = localityByLocalityId;
    }

    @OneToMany(mappedBy = "clientByClientId", fetch = FetchType.EAGER)
    public Collection<ClientPhone> getClientPhonesById() {
        return clientPhonesById;
    }

    public void setClientPhonesById(Collection<ClientPhone> clientPhonesById) {
        this.clientPhonesById = clientPhonesById;
    }
}
