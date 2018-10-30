package my.mapkn3.configurator.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
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
    @OneToMany(mappedBy = "factory")
    private Collection<SeriesEntity> series;
    @OneToMany(mappedBy = "factory")
    private Collection<GroupEntity> groups;
}
