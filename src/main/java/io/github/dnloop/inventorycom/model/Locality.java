package io.github.dnloop.inventorycom.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Locality {
    private String name;
    private Integer id;
    private String function;
    private String category;
    private Double centroidLat;
    private Double centroidLon;
    private Integer municipalityId;
    private Integer departamentId;
    private Integer provinceId;
    private Collection<Client> clientsById;
    private Municipality municipalityByMunicipalityId;
    private Departments departmentsByDepartamentId;
    private Province provinceByProvinceId;
    private Collection<Supplier> suppliersById;

    @Basic
    @Column(name = "name", nullable = false, length = 83)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "function", length = 21)
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Basic
    @Column(name = "category", length = 33)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "centroid_lat")
    public Double getCentroidLat() {
        return centroidLat;
    }

    public void setCentroidLat(Double centroidLat) {
        this.centroidLat = centroidLat;
    }

    @Basic
    @Column(name = "centroid_lon")
    public Double getCentroidLon() {
        return centroidLon;
    }

    public void setCentroidLon(Double centroidLon) {
        this.centroidLon = centroidLon;
    }

    @Basic
    @Column(name = "municipality_id")
    public Integer getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }

    @Basic
    @Column(name = "departament_id")
    public Integer getDepartamentId() {
        return departamentId;
    }

    public void setDepartamentId(Integer departamentId) {
        this.departamentId = departamentId;
    }

    @Basic
    @Column(name = "province_id", nullable = false)
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locality locality = (Locality) o;
        return Objects.equals(name, locality.name) && Objects.equals(id, locality.id) &&
               Objects.equals(function, locality.function) &&
               Objects.equals(category, locality.category) &&
               Objects.equals(centroidLat, locality.centroidLat) &&
               Objects.equals(centroidLon, locality.centroidLon) &&
               Objects.equals(municipalityId, locality.municipalityId) &&
               Objects.equals(departamentId, locality.departamentId) &&
               Objects.equals(provinceId, locality.provinceId);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(
                        name, id, function, category, centroidLat, centroidLon, municipalityId, departamentId,
                        provinceId
                );
    }

    @OneToMany(mappedBy = "localityByLocalityId")
    public Collection<Client> getClientsById() {
        return clientsById;
    }

    public void setClientsById(Collection<Client> clientsById) {
        this.clientsById = clientsById;
    }

    @ManyToOne
    @JoinColumn(name = "municipality_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Municipality getMunicipalityByMunicipalityId() {
        return municipalityByMunicipalityId;
    }

    public void setMunicipalityByMunicipalityId(Municipality municipalityByMunicipalityId) {
        this.municipalityByMunicipalityId = municipalityByMunicipalityId;
    }

    @ManyToOne
    @JoinColumn(name = "departament_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Departments getDepartmentsByDepartamentId() {
        return departmentsByDepartamentId;
    }

    public void setDepartmentsByDepartamentId(Departments departmentsByDepartamentId) {
        this.departmentsByDepartamentId = departmentsByDepartamentId;
    }

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", nullable = false, insertable = false,
                updatable = false)
    public Province getProvinceByProvinceId() {
        return provinceByProvinceId;
    }

    public void setProvinceByProvinceId(Province provinceByProvinceId) {
        this.provinceByProvinceId = provinceByProvinceId;
    }

    @OneToMany(mappedBy = "localityByLocalityId")
    public Collection<Supplier> getSuppliersById() {
        return suppliersById;
    }

    public void setSuppliersById(Collection<Supplier> suppliersById) {
        this.suppliersById = suppliersById;
    }
}
