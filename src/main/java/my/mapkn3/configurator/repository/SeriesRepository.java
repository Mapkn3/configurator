package my.mapkn3.configurator.repository;

import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesEntity, Long> {
    Optional<SeriesEntity> findByName(String name);

    Optional<SeriesEntity> findByArticle(String article);

    List<SeriesEntity> findAllByFactory(FactoryEntity factory);
}
