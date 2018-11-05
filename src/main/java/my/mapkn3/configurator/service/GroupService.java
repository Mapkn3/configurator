package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.GroupEntity;
import my.mapkn3.configurator.model.SeriesEntity;

import java.util.List;

public interface GroupService {
    GroupEntity getDefaultGroup();

    List<GroupEntity> getAllGroups();

    List<GroupEntity> getAllGroupsBySeries(SeriesEntity series);

    GroupEntity getGroupById(long id);

    GroupEntity getGroupByName(String name);

    GroupEntity addGroup(GroupEntity group);

    GroupEntity updateGroup(GroupEntity group);

    void deleteGroup(GroupEntity group);
}
