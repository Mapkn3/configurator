package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.GroupEntity;
import my.mapkn3.configurator.model.SeriesEntity;
import my.mapkn3.configurator.model.TypeEntity;
import my.mapkn3.configurator.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupEntity getDefaultGroup() {
        GroupEntity defaultGroup = new GroupEntity();
        defaultGroup.setId(-1);
        defaultGroup.setName("-");

        SeriesEntity defaultSeries = new SeriesEntity();
        defaultSeries.setId(-1);
        defaultSeries.setName("-");
        defaultSeries.setDescription("");
        defaultSeries.setArticle("");

        FactoryEntity defaultFactory = new FactoryEntity();
        defaultFactory.setId(-1);
        defaultFactory.setName("-");
        defaultFactory.setSeries(Collections.singletonList(defaultSeries));

        TypeEntity defaultType = new TypeEntity();
        defaultType.setId(-1);
        defaultType.setName("-");
        defaultType.setFactories(Collections.singletonList(defaultFactory));

        defaultFactory.setType(defaultType);

        defaultSeries.setFactory(defaultFactory);
        defaultSeries.setGroups(Collections.singletonList(defaultGroup));

        defaultGroup.setSeries(defaultSeries);
        return defaultGroup;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupEntity> getAllGroups() {
        List<GroupEntity> groups = groupRepository.findAll();
        log.info(String.format("Get %d groups:", groups.size()));
        for (GroupEntity group : groups) {
            log.info(String.format("%s", group.toString()));
        }
        return groups;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupEntity> getAllGroupsBySeries(SeriesEntity series) {
        List<GroupEntity> groups = groupRepository.findAllBySeries(series);
        log.info(String.format("Get %d groups for series:%n%s", groups.size(), series.toString()));
        for (GroupEntity group : groups) {
            log.info(String.format("%s", group.toString()));
        }
        return groups;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupEntity getGroupById(long id) {
        log.info(String.format("Getting group with id = %d", id));
        GroupEntity group = groupRepository.findById(id).orElse(getDefaultGroup());
        if (group.equals(getDefaultGroup())) {
            log.info(String.format("Group with id = %d not found", id));
        }
        return group;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupEntity getGroupByName(String name) {
        log.info(String.format("Getting group with name = %s", name));
        GroupEntity group = groupRepository.findByName(name).orElse(getDefaultGroup());
        if (group.equals(getDefaultGroup())) {
            log.info(String.format("Group with name = %s not found", name));
        }
        return group;
    }

    @Override
    public GroupEntity addGroup(GroupEntity group) {
        GroupEntity entity = group.getSeries().getGroups().stream().filter((g)->g.getName().equals(group.getName())).findFirst().orElse(null);
        if (entity != null) {
            log.info(String.format("Group already exist:%n%s", entity.toString()));
            group.setId(entity.getId());
            updateGroup(group);
            return entity;
        } else {
            log.info(String.format("Add new group:%n%s", group.toString()));
            GroupEntity newGroup = groupRepository.saveAndFlush(group);
            log.info(String.format("Id for new group: %d", newGroup.getId()));
            return newGroup;
        }
    }

    @Override
    public GroupEntity updateGroup(GroupEntity group) {
        GroupEntity updatedGroup = groupRepository.saveAndFlush(group);
        log.info(String.format("Updated group:%n%s", updatedGroup.toString()));
        return updatedGroup;
    }

    @Override
    public void deleteGroup(GroupEntity group) {
        log.info(String.format("Deleted group:%n%s", group.toString()));
        groupRepository.delete(group);
    }
}
