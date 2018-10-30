package my.mapkn3.configurator.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item_table")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Basic
    @Column(name = "model", unique = true, nullable = false)
    private String model;
    @Basic
    @Column(name = "our_article")
    private String ourArticle;
    @Basic
    @Column(name = "factory_article")
    private String factoryArticle;
    @Basic
    @Column(name = "cost")
    private BigDecimal cost;
    @Basic
    @Column(name = "eup")
    private BigDecimal eup;
    @Basic
    @Column(name = "ddp0")
    private BigDecimal ddp0;
    @Basic
    @Column(name = "ddp1")
    private BigDecimal ddp1;
    @Basic
    @Column(name = "ddp2")
    private BigDecimal ddp2;
    @Basic
    @Column(name = "ddp3")
    private BigDecimal ddp3;
    @Basic
    @Column(name = "ddp4")
    private BigDecimal ddp4;
    @Basic
    @Column(name = "ddp1min")
    private BigDecimal ddp1min;
    @Basic
    @Column(name = "exw")
    private BigDecimal exw;
    @Basic
    @Column(name = "balance")
    private long balance;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "path_to_photo")
    private String pathToPhoto;
    @Basic
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private CurrencyEntity currency;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private TypeEntity type;
    @ManyToOne
    @JoinColumn(name = "factory_id", referencedColumnName = "id", nullable = false)
    private FactoryEntity factory;
    @ManyToOne
    @JoinColumn(name = "series_id", referencedColumnName = "id", nullable = false)
    private SeriesEntity series;
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private GroupEntity group;
}
