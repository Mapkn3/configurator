package my.mapkn3.configurator.model;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@ToString(exclude = {"factories"})
@Entity
@Table(name = "type_table")
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Basic
    @NonNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "type")
    private Collection<FactoryEntity> factories;
}
