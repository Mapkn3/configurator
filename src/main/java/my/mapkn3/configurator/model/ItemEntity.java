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
    @Column(name = "url")
    private String url;
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

    public ItemEntity() {}

    public ItemEntity(String url, String model, String ourArticle, String factoryArticle, BigDecimal cost, long balance, String description, String pathToPhoto, String comment, CurrencyEntity currency, GroupEntity group) {
        this.url = url;
        this.model = model;
        this.ourArticle = ourArticle;
        this.factoryArticle = factoryArticle;
        this.cost = cost;
        this.balance = balance;
        this.description = description;
        this.pathToPhoto = pathToPhoto;
        this.comment = comment;
        this.currency = currency;
        this.group = group;
        this.series = this.group.getSeries();
        this.factory = this.series.getFactory();
        this.type = this.factory.getType();
    }
}
