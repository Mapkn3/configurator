package my.mapkn3.configurator.model;

import lombok.Data;

import javax.persistence.*;

@Data
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

    public GroupEntity() {}

    public GroupEntity(String name, SeriesEntity series) {
        this.id = 0;
        this.name = name;
        this.series = series;
    }
}
