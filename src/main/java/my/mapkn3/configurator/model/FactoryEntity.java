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
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private TypeEntity type;
    @OneToMany(mappedBy = "factory")
    private Collection<SeriesEntity> series;
}
