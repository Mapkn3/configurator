package my.mapkn3.configurator.repository;

import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<FactoryEntity, Long> {
    Optional<FactoryEntity> findByName(String name);

    List<FactoryEntity> findAllByType(TypeEntity type);
}
