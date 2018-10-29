package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.*;

import java.util.List;

public interface ItemService {
    List<ItemEntity> getAllItems();

    List<ItemEntity> getAllByType(TypeEntity type);

    List<ItemEntity> getAllByFactory(FactoryEntity factory);

    List<ItemEntity> getAllBySeries(SeriesEntity series);

    List<ItemEntity> getAllByGroup(GroupEntity group);

    List<ItemEntity> getAllByFactoryAndSeriesAndGroup(FactoryEntity factory, SeriesEntity series, GroupEntity group);

    List<ItemEntity> getAllByFactoryAndSeries(FactoryEntity factory, SeriesEntity series);

    List<ItemEntity> getAllByFactoryAndGroup(FactoryEntity factory, GroupEntity group);

    List<ItemEntity> getAllBySeriesAndGroup(SeriesEntity series, GroupEntity group);

    ItemEntity getById(long id);

    ItemEntity getByModel(String model);

    ItemEntity getByOurArticle(String ourArticle);

    ItemEntity getByFactoryArticle(String factoryArticle);

    ItemEntity addItem(ItemEntity item);

    ItemEntity updateItem(ItemEntity item);

    void deleteItem(ItemEntity item);
}
