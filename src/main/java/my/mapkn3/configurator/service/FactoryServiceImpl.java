package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.TypeEntity;
import my.mapkn3.configurator.repository.FactoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional
@Service
public class FactoryServiceImpl implements FactoryService {
    private final FactoryRepository factoryRepository;

    @Autowired
    public FactoryServiceImpl(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    @Override
    public FactoryEntity getDefaultFactory() {
        TypeEntity defaultType = new TypeEntity("-");
        defaultType.setId(-1);

        FactoryEntity defaultFactory = new FactoryEntity("-", defaultType);
        defaultFactory.setId(-1);
        defaultFactory.setSeries(Collections.emptyList());

        defaultType.setFactories(Collections.singletonList(defaultFactory));
        return defaultFactory;
    }

    @Override
    public List<FactoryEntity> getAllFactories() {
        List<FactoryEntity> factories = factoryRepository.findAll();
        log.info(String.format("Get %d factories:", factories.size()));
        /*
        for (FactoryEntity factory : factories) {
            log.info(String.format("%s", factory.toString()));
        }
        */
        return factories;
    }

    @Override
    public List<FactoryEntity> getAllFactoriesByType(TypeEntity type) {
        List<FactoryEntity> factories = factoryRepository.findAllByType(type);
        log.info(String.format("Get %d factories for type:%n%s", factories.size(), type.toString()));
        for (FactoryEntity factory : factories) {
            log.info(String.format("%s", factory.toString()));
        }
        return factories;
    }

    @Override
    @Transactional(readOnly = true)
    public FactoryEntity getFactoryById(long id) {
        log.info(String.format("Getting factory with id = %d", id));
        FactoryEntity factory = factoryRepository.findById(id).orElse(getDefaultFactory());
        if (factory.equals(getDefaultFactory())) {
            log.info(String.format("Factory with id = %d not found", id));
        }
        return factory;
    }

    @Override
    @Transactional(readOnly = true)
    public FactoryEntity getFactoryByName(String name) {
        log.info(String.format("Getting factory with name = %s", name));
        FactoryEntity factory = factoryRepository.findByName(name).orElse(getDefaultFactory());
        if (factory.equals(getDefaultFactory())) {
            log.info(String.format("Factory with name = %s not found", name));
        }
        return factory;
    }

    @Override
    public FactoryEntity addFactory(FactoryEntity factory) {
        Collection<FactoryEntity> factories = factory.getType().getFactories();
        if (factories != null) {
            FactoryEntity entity = factories.stream().filter((f) -> f.getName().equals(factory.getName())).findFirst().orElse(null);
            if (entity != null) {
                log.info(String.format("Factory already exist:%n%s", entity.toString()));
                return entity;
            }
        }
        log.info(String.format("Add new factory:%n%s", factory.toString()));
        FactoryEntity newFactory = factoryRepository.saveAndFlush(factory);
        log.info(String.format("id for new factory: %d", newFactory.getId()));
        return newFactory;
    }

    @Override
    public FactoryEntity updateFactory(FactoryEntity factory) {
        FactoryEntity updatedFactory = factoryRepository.saveAndFlush(factory);
        log.info(String.format("Updated factory:%n%s", updatedFactory.toString()));
        return updatedFactory;
    }

    @Override
    public void deleteFactory(FactoryEntity factory) {
        log.info(String.format("Deleted factory:%n%s", factory.toString()));
        factoryRepository.delete(factory);
    }
}