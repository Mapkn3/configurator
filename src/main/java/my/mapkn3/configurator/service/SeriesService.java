package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.FactoryEntity;
import my.mapkn3.configurator.model.SeriesEntity;

import java.util.List;

public interface SeriesService {
    SeriesEntity getDefaultSeries();

    List<SeriesEntity> getAllSeries();

    List<SeriesEntity> getAllSeriesByFactory(FactoryEntity factory);

    SeriesEntity getSeriesById(long id);

    SeriesEntity getSeriesByName(String name);

    SeriesEntity getSeriesByArticle(String article);

    SeriesEntity addSeries(SeriesEntity series);

    SeriesEntity updateSeries(SeriesEntity series);

    void deleteSeries(SeriesEntity series);
}
