package my.mapkn3.configurator.service;

import my.mapkn3.configurator.model.CurrencyEntity;

import java.util.List;

public interface CurrencyService {
    CurrencyEntity getDefaultCurrency();

    List<CurrencyEntity> getAllCurrencies();

    CurrencyEntity getCurrencyById(long id);

    CurrencyEntity getCurrencyByName(String name);

    CurrencyEntity addCurrency(CurrencyEntity currency);

    CurrencyEntity updateCurrency(CurrencyEntity currency);

    void deleteCurrency(CurrencyEntity currency);
}
