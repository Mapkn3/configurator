package my.mapkn3.configurator.service;

import lombok.extern.slf4j.Slf4j;
import my.mapkn3.configurator.model.CurrencyEntity;
import my.mapkn3.configurator.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Transactional
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<CurrencyEntity> getAllCurrencies() {
        List<CurrencyEntity> currencies = currencyRepository.findAll();
        log.debug(String.format("Get %d currencies:%n", currencies.size()));
        for (CurrencyEntity currency : currencies) {
            log.debug(String.format("%s%n", currency.toString()));
        }
        return currencies;
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyEntity getCurrencyById(long id) {
        log.debug(String.format("Getting currency with id = %d", id));
        CurrencyEntity currency = currencyRepository.findById(id).orElse(null);
        if (currency == null) {
            log.debug(String.format("Currency with id = %d not found", id));
        }
        return currency;
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyEntity getCurrencyByName(String name) {
        log.debug(String.format("Getting currency with name = %s", name));
        CurrencyEntity currency = currencyRepository.findByName(name).orElse(null);
        if (currency == null) {
            log.debug(String.format("Currency with name = %s not found", name));
        }
        return currency;
    }

    @Override
    public CurrencyEntity addCurrency(CurrencyEntity currency) {
        CurrencyEntity entity = currencyRepository.findByName(currency.getName()).orElse(null);
        if (entity != null) {
            log.debug(String.format("Currency already exist:%n%s", entity.toString()));
            return null;
        } else {
            log.debug(String.format("Add new currency:%n%s", currency.toString()));
            CurrencyEntity newCurrency = currencyRepository.saveAndFlush(currency);
            log.debug(String.format("Id for new currency: %d", newCurrency.getId()));
            return newCurrency;
        }
    }

    @Override
    public CurrencyEntity updateCurrency(CurrencyEntity currency) {
        CurrencyEntity updatedCurrency = currencyRepository.saveAndFlush(currency);
        log.debug(String.format("Updated currency:%n%s", updatedCurrency.toString()));
        return updatedCurrency;
    }

    @Override
    public void deleteCurrency(CurrencyEntity currency) {
        log.debug(String.format("Deleted currency:%n%s", currency.toString()));
        currencyRepository.delete(currency);
    }
}
