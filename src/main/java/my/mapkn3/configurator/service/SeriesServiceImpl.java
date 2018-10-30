package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.SeriesEntity;
import my.mapkn3.configurator.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class SeriesServiceImpl implements SeriesService {
    private final SeriesRepository seriesRepository;

    @Autowired
    public SeriesServiceImpl(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeriesEntity> getAllSeries() {
        List<SeriesEntity> seriesEntities = seriesRepository.findAll();
        log.info(String.format("Get %d series:%n", seriesEntities.size()));
        for (SeriesEntity series : seriesEntities) {
            log.info(String.format("%s%n", series.toString()));
        }
        return seriesEntities;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeriesEntity> getAllSeriesByFactory(FactoryEntity factory) {
        List<SeriesEntity> seriesEntities = seriesRepository.findAllByFactory(factory);
        log.info(String.format("Get %d series for factory:%n%s%n", seriesEntities.size(), factory.toString()));
        for (SeriesEntity series : seriesEntities) {
            log.info(String.format("%s%n", series.toString()));
        }
        return seriesEntities;
    }

    @Override
    @Transactional(readOnly = true)
    public SeriesEntity getSeriesById(long id) {
        log.info(String.format("Getting series with id = %d", id));
        SeriesEntity series = seriesRepository.findById(id).orElse(null);
        if (series == null) {
            log.info(String.format("Series with id = %d not found", id));
        }
        return series;
    }

    @Override
    @Transactional(readOnly = true)
    public SeriesEntity getSeriesByName(String name) {
        log.info(String.format("Getting series with name = %s", name));
        SeriesEntity series = seriesRepository.findByName(name).orElse(null);
        if (series == null) {
            log.info(String.format("Series with name = %s not found", name));
        }
        return series;
    }

    @Override
    @Transactional(readOnly = true)
    public SeriesEntity getSeriesByArticle(String article) {
        log.info(String.format("Getting series with article = %s", article));
        SeriesEntity series = seriesRepository.findByArticle(article).orElse(null);
        if (series == null) {
            log.info(String.format("Series with article = %s not found", article));
        }
        return series;
    }

    @Override
    public SeriesEntity addSeries(SeriesEntity series) {
        SeriesEntity entity = seriesRepository.findByName(series.getName()).orElse(null);
        if (entity != null) {
            log.info(String.format("Series already exist:%n%s", entity.toString()));
            return null;
        } else {
            log.info(String.format("Add new series:%n%s", series.toString()));
            SeriesEntity newSeries = seriesRepository.saveAndFlush(series);
            log.info(String.format("Id for new series: %d", newSeries.getId()));
            return newSeries;
        }
    }

    @Override
    public SeriesEntity updateSeries(SeriesEntity series) {
        SeriesEntity updatedSeries = seriesRepository.saveAndFlush(series);
        log.info(String.format("Updated series:%n%s", updatedSeries.toString()));
        return updatedSeries;
    }

    @Override
    public void deleteSeries(SeriesEntity series) {
        log.info(String.format("Deleted series:%n%s", series.toString()));
        seriesRepository.delete(series);
    }
}
