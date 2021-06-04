package io.github.dnloop.inventorycom.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * <h4>Municipality</h4>
 * <p>
 * Entity used to represent municipalities.
 */
@Entity
@Table(name = "municipality")
public class Municipality {
    private String name;
    private Integer id;
    private Collection<Locality> localitiesById;

    @Basic
    @Column(name = "name", nullable = false, length = 38)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Municipality that = (Municipality) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @OneToMany(mappedBy = "municipalityByMunicipalityId")
    public Collection<Locality> getLocalitiesById() {
        return localitiesById;
    }

    public void setLocalitiesById(Collection<Locality> localitiesById) {
        this.localitiesById = localitiesById;
    }
}
