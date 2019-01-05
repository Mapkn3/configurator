package my.mapkn3.configurator.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@ToString(exclude = {"items"})
@Entity
@Table(name = "group_table")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "series_id", referencedColumnName = "id", nullable = false)
    private SeriesEntity series;
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private Collection<ItemEntity> items;

    public GroupEntity() {}

    public GroupEntity(String name, SeriesEntity series) {
        this.id = 0;
        this.name = name;
        this.series = series;
    }
}
