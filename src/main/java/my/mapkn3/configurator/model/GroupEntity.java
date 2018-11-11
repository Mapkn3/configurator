package my.mapkn3.configurator.model;

import lombok.Data;
import lombok.NonNull;

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
    @NonNull
    @Column(name = "name")
    private String name;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "series_id", referencedColumnName = "id", nullable = false)
    private SeriesEntity series;
}
