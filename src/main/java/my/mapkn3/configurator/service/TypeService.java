package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.TypeEntity;

import java.util.List;

public interface TypeService {
    List<TypeEntity> getAllTypes();

    TypeEntity getTypeById(long id);

    TypeEntity getTypeByName(String name);

    TypeEntity addType(TypeEntity type);

    TypeEntity updateType(TypeEntity type);

    void deleteType(TypeEntity type);
}
