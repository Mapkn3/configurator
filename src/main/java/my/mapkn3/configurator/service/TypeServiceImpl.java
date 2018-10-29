package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.TypeEntity;
import my.mapkn3.configurator.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<TypeEntity> getAllTypes() {
        List<TypeEntity> types = typeRepository.findAll();
        log.debug(String.format("Get %d types:%n", types.size()));
        for (TypeEntity type : types) {
            log.debug(String.format("%s%n", type.toString()));
        }
        return types;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeEntity getTypeById(long id) {
        log.debug(String.format("Getting type with id = %d", id));
        TypeEntity type = typeRepository.findById(id).orElse(null);
        if (type == null) {
            log.debug(String.format("Type with id = %d not found", id));
        }
        return type;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeEntity getTypeByName(String name) {
        log.debug(String.format("Getting type with name = %s", name));
        TypeEntity type = typeRepository.findByName(name).orElse(null);
        if (type == null) {
            log.debug(String.format("Type with name = %s not found", name));
        }
        return type;
    }

    @Override
    public TypeEntity addType(TypeEntity type) {
        TypeEntity entity = typeRepository.findByName(type.getName()).orElse(null);
        if (entity != null) {
            log.debug(String.format("Type already exist:%n%s", entity.toString()));
            return null;
        } else {
            log.debug(String.format("Add new type:%n%s", type.toString()));
            TypeEntity newType = typeRepository.saveAndFlush(type);
            log.debug(String.format("Id for new type: %d", newType.getId()));
            return newType;
        }
    }

    @Override
    public TypeEntity updateType(TypeEntity type) {
        TypeEntity updatedType = typeRepository.saveAndFlush(type);
        log.debug(String.format("Updated type:%n%s", updatedType.toString()));
        return updatedType;
    }

    @Override
    public void deleteType(TypeEntity type) {
        log.debug(String.format("Deleted type:%n%s", type.toString()));
        typeRepository.delete(type);
    }
}