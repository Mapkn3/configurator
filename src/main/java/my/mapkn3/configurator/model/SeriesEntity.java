package my.mapkn3.configurator.model;


import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@ToString(exclude = {"groups"})
@Entity
@Table(name = "series_table")
public class SeriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Basic
    @NonNull
    @Column(name = "name")
    private String name;
    @Basic
    @NonNull
    @Column(name = "description")
    private String description;
    @Basic
    @NonNull
    @Column(name = "article")
    private String article;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "factory_id", referencedColumnName = "id", nullable = false)
    private FactoryEntity factory;
    @OneToMany(mappedBy = "series")
    private Collection<GroupEntity> groups;
}
