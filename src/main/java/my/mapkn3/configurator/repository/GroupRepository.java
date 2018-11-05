package my.mapkn3.configurator.repository;

import my.mapkn3.configurator.model.GroupEntity;
import my.mapkn3.configurator.model.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    Optional<GroupEntity> findByName(String name);

    List<GroupEntity> findAllBySeries(SeriesEntity series);
}
