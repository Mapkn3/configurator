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
    @NonNull
    @ManyToOne
    @JoinColumn(name = "factory_id", referencedColumnName = "id", nullable = false)
    private FactoryEntity factory;
    @OneToMany(mappedBy = "series", cascade = CascadeType.REMOVE)
    private Collection<GroupEntity> groups;

    public SeriesEntity() {}

    public SeriesEntity(String name, String description, String article, FactoryEntity factory) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.article = article;
        this.factory = factory;
    }
}
