package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.GroupEntity;
import my.mapkn3.configurator.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<GroupEntity> getAllGroups() {
        List<GroupEntity> groups = groupRepository.findAll();
        log.debug(String.format("Get %d groups:%n", groups.size()));
        for (GroupEntity group : groups) {
            log.debug(String.format("%s%n", group.toString()));
        }
        return groups;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupEntity> getAllGroupsByFactory(FactoryEntity factory) {
        List<GroupEntity> groups = groupRepository.findAllByFactory(factory);
        log.debug(String.format("Get %d groups for factory:%n%s%n", groups.size(), factory.toString()));
        for (GroupEntity group : groups) {
            log.debug(String.format("%s%n", group.toString()));
        }
        return groups;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupEntity getGroupById(long id) {
        log.debug(String.format("Getting group with id = %d", id));
        GroupEntity group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            log.debug(String.format("Group with id = %d not found", id));
        }
        return group;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupEntity getGroupByName(String name) {
        log.debug(String.format("Getting group with name = %d", name));
        GroupEntity group = groupRepository.findByName(name).orElse(null);
        if (group == null) {
            log.debug(String.format("Group with name = %d not found", name));
        }
        return group;
    }

    @Override
    public GroupEntity addGroup(GroupEntity group) {
        GroupEntity entity = groupRepository.findByName(group.getName()).orElse(null);
        if (entity != null) {
            log.debug(String.format("Group already exist:%n%s", entity.toString()));
            return null;
        } else {
            log.debug(String.format("Add new group:%n%s", group.toString()));
            GroupEntity newGroup = groupRepository.saveAndFlush(group);
            log.debug(String.format("Id for new group: %d", newGroup.getId()));
            return newGroup;
        }
    }

    @Override
    public GroupEntity updateGroup(GroupEntity group) {
        GroupEntity updatedGroup = groupRepository.saveAndFlush(group);
        log.debug(String.format("Updated group:%n%s", updatedGroup.toString()));
        return updatedGroup;
    }

    @Override
    public void deleteGroup(GroupEntity group) {
        log.debug(String.format("Deleted group:%n%s", group.toString()));
        groupRepository.delete(group);
    }
}
