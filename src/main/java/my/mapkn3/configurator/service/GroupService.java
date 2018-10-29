package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.GroupEntity;

import java.util.List;

public interface GroupService {
    List<GroupEntity> getAllGroups();

    List<GroupEntity> getAllGroupsByFactory(FactoryEntity factory);

    GroupEntity getGroupById(long id);

    GroupEntity getGroupByName(String name);

    GroupEntity addGroup(GroupEntity group);

    GroupEntity updateGroup(GroupEntity group);

    void deleteGroup(GroupEntity group);
}
