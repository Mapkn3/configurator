package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.TypeEntity;
import my.mapkn3.configurator.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional
@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public TypeEntity getDefaultType() {
        TypeEntity defaultType = new TypeEntity();
        defaultType.setId(-1);
        defaultType.setName("-");
        defaultType.setFactories(Collections.emptyList());
        return defaultType;
    }

    @Override
    public List<TypeEntity> getAllTypes() {
        List<TypeEntity> types = typeRepository.findAll();
        log.info(String.format("Get %d types:", types.size()));
        for (TypeEntity type : types) {
            log.info(String.format("%s", type.toString()));
        }
        return types;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeEntity getTypeById(long id) {
        log.info(String.format("Getting type with id = %d", id));
        TypeEntity type = typeRepository.findById(id).orElse(getDefaultType());
        if (type.equals(getDefaultType())) {
            log.info(String.format("Type with id = %d not found", id));
        }
        return type;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeEntity getTypeByName(String name) {
        log.info(String.format("Getting type with name = %s", name));
        TypeEntity type = typeRepository.findByName(name).orElse(getDefaultType());
        if (type.equals(getDefaultType())) {
            log.info(String.format("Type with name = %s not found", name));
        }
        return type;
    }

    @Override
    public TypeEntity addType(TypeEntity type) {
        TypeEntity entity = typeRepository.findByName(type.getName()).orElse(null);
        if (entity != null) {
            log.info(String.format("Type already exist:%n%s", entity.toString()));
            return entity;
        } else {
            log.info(String.format("Add new type:%n%s", type.toString()));
            TypeEntity newType = typeRepository.saveAndFlush(type);
            log.info(String.format("Id for new type: %d", newType.getId()));
            return newType;
        }
    }

    @Override
    public TypeEntity updateType(TypeEntity type) {
        TypeEntity updatedType = typeRepository.saveAndFlush(type);
        log.info(String.format("Updated type:%n%s", updatedType.toString()));
        return updatedType;
    }

    @Override
    public void deleteType(TypeEntity type) {
        log.info(String.format("Deleted type:%n%s", type.toString()));
        typeRepository.delete(type);
    }
}
