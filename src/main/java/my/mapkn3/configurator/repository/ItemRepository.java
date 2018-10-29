package my.mapkn3.configurator.repository;

import my.mapkn3.configurator.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByModel(String model);

    Optional<ItemEntity> findByOurArticle(String ourArticle);

    Optional<ItemEntity> findByFactoryArticle(String factoryArticle);

    List<ItemEntity> findAllByType(TypeEntity type);

    List<ItemEntity> findAllByFactory(FactoryEntity factory);

    List<ItemEntity> findAllBySeries(SeriesEntity series);

    List<ItemEntity> findAllByGroup(GroupEntity group);

    List<ItemEntity> findAllByFactoryAndSeriesAndGroup(FactoryEntity factory, SeriesEntity series, GroupEntity group);

    List<ItemEntity> findAllByFactoryAndSeries(FactoryEntity factory, SeriesEntity series);

    List<ItemEntity> findAllByFactoryAndGroup(FactoryEntity factory, GroupEntity group);

    List<ItemEntity> findAllBySeriesAndGroup(SeriesEntity series, GroupEntity group);
}
