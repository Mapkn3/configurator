package my.mapkn3.configurator.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@ToString(exclude = {"series"})
@Entity
@Table(name = "factory_table")
public class FactoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private TypeEntity type;
    @OneToMany(mappedBy = "factory", cascade = CascadeType.REMOVE)
    private Collection<SeriesEntity> series;

    public FactoryEntity() {}

    public FactoryEntity(String name, TypeEntity type) {
        this.id = 0;
        this.name = name;
        this.type = type;
    }
}
