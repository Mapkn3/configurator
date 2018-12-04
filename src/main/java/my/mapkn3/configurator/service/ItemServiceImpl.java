package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.*;
import my.mapkn3.configurator.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemEntity getDefaultItem() {
        TypeEntity defaultType = new TypeEntity("-");
        defaultType.setId(-1);

        FactoryEntity defaultFactory = new FactoryEntity("-", defaultType);
        defaultFactory.setId(-1);

        SeriesEntity defaultSeries = new SeriesEntity("-", "", "", defaultFactory);
        defaultSeries.setId(-1);

        GroupEntity defaultGroup = new GroupEntity("-", defaultSeries);
        defaultGroup.setId(-1);

        defaultType.setFactories(Collections.singletonList(defaultFactory));
        defaultFactory.setSeries(Collections.singletonList(defaultSeries));
        defaultSeries.setGroups(Collections.singletonList(defaultGroup));

        CurrencyEntity defaultCurrency = new CurrencyEntity();
        defaultCurrency.setId(-1);
        defaultCurrency.setName("-");

        ItemEntity defaultItem = new ItemEntity();
        defaultItem.setId(-1);
        defaultItem.setModel("-");
        defaultItem.setOurArticle("");
        defaultItem.setFactoryArticle("");
        defaultItem.setCost(BigDecimal.ZERO);
        defaultItem.setEup(BigDecimal.ZERO);
        defaultItem.setDdp0(BigDecimal.ZERO);
        defaultItem.setDdp1(BigDecimal.ZERO);
        defaultItem.setDdp2(BigDecimal.ZERO);
        defaultItem.setDdp3(BigDecimal.ZERO);
        defaultItem.setDdp4(BigDecimal.ZERO);
        defaultItem.setDdp1min(BigDecimal.ZERO);
        defaultItem.setExw(BigDecimal.ZERO);
        defaultItem.setBalance(0);
        defaultItem.setDescription("");
        defaultItem.setPathToPhoto("");
        defaultItem.setComment("");
        defaultItem.setCurrency(defaultCurrency);
        defaultItem.setType(defaultType);
        defaultItem.setFactory(defaultFactory);
        defaultItem.setSeries(defaultSeries);
        defaultItem.setGroup(defaultGroup);

        return defaultItem;
    }

    @Override
    public List<ItemEntity> getAllItems() {
        List<ItemEntity> items = itemRepository.findAll();
        log.info(String.format("Get %d items:", items.size()));
        /*
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        */
        return items;
    }

    @Override
    public List<ItemEntity> getAllByType(TypeEntity type) {
        List<ItemEntity> items = itemRepository.findAllByType(type);
        log.info(String.format("Get %d items for type:%n%s", items.size(), type.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public List<ItemEntity> getAllByFactory(FactoryEntity factory) {
        List<ItemEntity> items = itemRepository.findAllByFactory(factory);
        log.info(String.format("Get %d items for factory:%n%s", items.size(), factory.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public List<ItemEntity> getAllBySeries(SeriesEntity series) {
        List<ItemEntity> items = itemRepository.findAllBySeries(series);
        log.info(String.format("Get %d items for series:%n%s", items.size(), series.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public List<ItemEntity> getAllByGroup(GroupEntity group) {
        List<ItemEntity> items = itemRepository.findAllByGroup(group);
        log.info(String.format("Get %d items for group:%n%s", items.size(), group.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public List<ItemEntity> getAllByFactoryAndSeriesAndGroup(FactoryEntity factory, SeriesEntity series, GroupEntity group) {
        List<ItemEntity> items = itemRepository.findAllByFactoryAndSeriesAndGroup(factory, series, group);
        log.info(String.format("Get %d items for factory, series and group:%n%s%n%s%n%s", items.size(),
                factory.toString(), series.toString(), group.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public List<ItemEntity> getAllByFactoryAndSeries(FactoryEntity factory, SeriesEntity series) {
        List<ItemEntity> items = itemRepository.findAllByFactoryAndSeries(factory, series);
        log.info(String.format("Get %d items for factory and series:%n%s%n%s", items.size(), factory.toString(), series.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public List<ItemEntity> getAllByFactoryAndGroup(FactoryEntity factory, GroupEntity group) {
        List<ItemEntity> items = itemRepository.findAllByFactoryAndGroup(factory, group);
        log.info(String.format("Get %d items for factory and group:%n%s%n%s", items.size(), factory.toString(), group.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public List<ItemEntity> getAllBySeriesAndGroup(SeriesEntity series, GroupEntity group) {
        List<ItemEntity> items = itemRepository.findAllBySeriesAndGroup(series, group);
        log.info(String.format("Get %d items for series and group:%n%s%n%s", items.size(), series.toString(), group.toString()));
        for (ItemEntity item : items) {
            log.info(String.format("%s", item.toString()));
        }
        return items;
    }

    @Override
    public ItemEntity getById(long id) {
        log.info(String.format("Getting item with id = %d", id));
        ItemEntity item = itemRepository.findById(id).orElse(getDefaultItem());
        if (item.equals(getDefaultItem())) {
            log.info(String.format("Item with id = %d not found", id));
        }
        return item;
    }

    @Override
    public ItemEntity getByModel(String model) {
        log.info(String.format("Getting item with model = %s", model));
        ItemEntity item = itemRepository.findByModel(model).orElse(getDefaultItem());
        if (item.equals(getDefaultItem())) {
            log.info(String.format("Item with model = %s not found", model));
        }
        return item;
    }

    @Override
    public ItemEntity getByOurArticle(String ourArticle) {
        log.info(String.format("Getting item with our article = %s", ourArticle));
        ItemEntity item = itemRepository.findByOurArticle(ourArticle).orElse(getDefaultItem());
        if (item.equals(getDefaultItem())) {
            log.info(String.format("Item with our article = %s not found", ourArticle));
        }
        return item;
    }

    @Override
    public ItemEntity getByFactoryArticle(String factoryArticle) {
        log.info(String.format("Getting item with factory article = %s", factoryArticle));
        ItemEntity item = itemRepository.findByFactoryArticle(factoryArticle).orElse(getDefaultItem());
        if (item.equals(getDefaultItem())) {
            log.info(String.format("Item with factory article = %s not found", factoryArticle));
        }
        return item;
    }

    @Override
    public ItemEntity addItem(ItemEntity item) {
        ItemEntity entity = itemRepository.findByModel(item.getModel()).orElse(null);
        if (entity != null) {
            log.info(String.format("Item already exist:%n%s", entity.toString()));
            return entity;
        } else {
            log.info(String.format("Add new item:%n%s", item.toString()));
            ItemEntity newItem = itemRepository.saveAndFlush(item);
            log.info(String.format("Id for new item: %d", newItem.getId()));
            return newItem;
        }
    }

    @Override
    public ItemEntity updateItem(ItemEntity item) {
        ItemEntity updatedItem = itemRepository.saveAndFlush(item);
        log.info(String.format("Updated item:%n%s", updatedItem.toString()));
        return updatedItem;
    }

    @Override
    public void deleteItem(ItemEntity item) {
        log.info(String.format("Deleted item:%n%s", item.toString()));
        itemRepository.delete(item);
    }
}
