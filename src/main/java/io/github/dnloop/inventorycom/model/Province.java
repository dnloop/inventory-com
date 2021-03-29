package io.github.dnloop.inventorycom.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "province")
public class Province {
    private String name;
    private Integer id;
    private Collection<Locality> localitiesById;

    @Basic
    @Column(name = "name", nullable = false, length = 55)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Province province = (Province) o;
        return Objects.equals(name, province.name) && Objects.equals(id, province.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @OneToMany(mappedBy = "provinceByProvinceId")
    public Collection<Locality> getLocalitiesById() {
        return localitiesById;
    }

    public void setLocalitiesById(Collection<Locality> localitiesById) {
        this.localitiesById = localitiesById;
    }
}
