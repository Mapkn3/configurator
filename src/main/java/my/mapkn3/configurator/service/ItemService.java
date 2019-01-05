package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.*;

import java.util.List;

public interface ItemService {
    ItemEntity getDefaultItem();

    List<ItemEntity> getAllItems();

    List<ItemEntity> getAllByType(TypeEntity type);

    List<ItemEntity> getAllByFactory(FactoryEntity factory);

    List<ItemEntity> getAllBySeries(SeriesEntity series);

    List<ItemEntity> getAllByGroup(GroupEntity group);

    ItemEntity getItemById(long id);

    ItemEntity getItemByModel(String model);

    ItemEntity getItemByOurArticle(String ourArticle);

    ItemEntity getItemByFactoryArticle(String factoryArticle);

    ItemEntity addItem(ItemEntity item);

    ItemEntity updateItem(ItemEntity item);

    void deleteItem(ItemEntity item);
}
