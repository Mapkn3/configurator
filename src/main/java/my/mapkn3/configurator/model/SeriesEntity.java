package my.mapkn3.configurator.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "series")
public class SeriesEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Basic
    @Column(name = "name", unique = true)
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "article")
    private String article;
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private FactoryEntity factory;
}
