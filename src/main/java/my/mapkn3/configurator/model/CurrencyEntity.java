package my.mapkn3.configurator.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "currency")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Basic
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
