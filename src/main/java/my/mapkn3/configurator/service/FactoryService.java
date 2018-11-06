package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.TypeEntity;

import java.util.List;

public interface FactoryService {
    FactoryEntity getDefaultFactory();

    List<FactoryEntity> getAllFactories();

    List<FactoryEntity> getAllFactoriesByType(TypeEntity type);

    FactoryEntity getFactoryById(long id);

    FactoryEntity getFactoryByName(String name);

    FactoryEntity addFactory(FactoryEntity factory);

    FactoryEntity updateFactory(FactoryEntity factory);

    void deleteFactory(FactoryEntity factory);
}
