package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.CurrencyRates.CurrencyRatesService;
import my.mapkn3.configurator.model.CommercialOffer;
import my.mapkn3.configurator.model.CurrencyEntity;
import my.mapkn3.configurator.model.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes(types = {IndexController.FilterConfig.class, CurrencyRatesService.class}, names = "items")
public class IndexController extends MainController {

    private final CurrencyRatesService currencyRatesService;
    private CurrencyEntity currentCurrency;
    private CommercialOffer commercialOffer = null;

    @Autowired
    public IndexController(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAll(ModelMap model) {
        currencyRatesService.updateCurrencyRates();
        if (commercialOffer == null) {
            currentCurrency = currencyService.getCurrencyByName("RUR");
            commercialOffer = new CommercialOffer(currencyRatesService, currentCurrency);
        }
        if (!model.containsAttribute("filter")) {
            model.addAttribute("filter", new FilterConfig());
        }
        if (!model.containsAttribute("items")) {
            model.addAttribute("items", itemService.getAllItems());
        }
        if (!model.containsAttribute("currencyRateService")) {
            model.addAttribute("currencyRateService", currencyRatesService);
        }
        if (!model.containsAttribute("commercialOffer")) {
            commercialOffer.setCurrency(currentCurrency);
            model.addAttribute("commercialOffer", commercialOffer);
        }
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String showItem(FilterConfig filter, ModelMap model) {
        List<ItemEntity> itemEntities;
        switch (filter.getCurrentFilter()) {
            case ALL:
                itemEntities = itemService.getAllItems();
                break;
            case TYPE:
                itemEntities = itemService.getAllByType(typeService.getTypeById(filter.getCurrentId()));
                break;
            case FACTORY:
                itemEntities = itemService.getAllByFactory(factoryService.getFactoryById(filter.getCurrentId()));
                break;
            case SERIES:
                itemEntities = itemService.getAllBySeries(seriesService.getSeriesById(filter.getCurrentId()));
                break;
            case GROUP:
                itemEntities = itemService.getAllByGroup(groupService.getGroupById(filter.getCurrentId()));
                break;
            default:
                itemEntities = itemService.getAllItems();
                break;
        }
        model.addAttribute("filter", filter);
        model.addAttribute("items", itemEntities);
        return showAll(model);
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public String addItem(@PathVariable("id") long id) {
        commercialOffer.addItem(itemService.getById(id));
        return "redirect:/";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public String removeItem(@PathVariable("id") long id) {
        commercialOffer.removeItemById(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/recalculate", method = RequestMethod.POST)
    public String changeCurrencyForTotalPrice(CommercialOffer commercialOffer) {
        currentCurrency = commercialOffer.getCurrency();
        return "redirect:/";
    }

    public static class FilterConfig {
        private CurrencyEntity currency;
        private String tfsgConfig;//type > factory > series > group

        public String getTfsgConfig() {
            return tfsgConfig;
        }

        public void setTfsgConfig(String tfsgConfig) {
            this.tfsgConfig = tfsgConfig;
        }

        public CurrencyEntity getCurrency() {
            return currency;
        }

        public void setCurrency(CurrencyEntity currency) {
            this.currency = currency;
        }

        public Filter getCurrentFilter() {
            String[] params = tfsgConfig.split(":");
            switch (params[0]) {
                case "T":
                    return Filter.TYPE;
                case "F":
                    return Filter.FACTORY;
                case "S":
                    return Filter.SERIES;
                case "G":
                    return Filter.GROUP;
                default:
                    return Filter.ALL;
            }
        }

        public Long getCurrentId() {
            String[] params = tfsgConfig.split(":");
            return Long.valueOf(params[1]);
        }

        /*public Filter getFilter() {
            List<Long> ids = Arrays.stream(tfsgConfig.split("\\|")).map(Long::valueOf).collect(Collectors.toList());
            if (ids.get(0) == -1) {
                if (ids.get(1) == -1) {
                    if (ids.get(2) == -1) {
                        return Filter.ALL;
                    } else {
                        return Filter.SERIES;
                    }
                } else {
                    if (ids.get(2) == -1) {
                        return Filter.GROUP;
                    } else {
                        return Filter.SERIES_GROUP;
                    }
                }
            } else {
                if (ids.get(1) == -1) {
                    if (ids.get(2) == -1) {
                        return Filter.FACTORY;
                    } else {
                        return Filter.FACTORY_GROUP;
                    }
                } else {
                    if (ids.get(2) == -1) {
                        return Filter.FACTORY_SERIES;
                    } else {
                        return Filter.FACTORY_SERIES_GROUP;
                    }
                }
            }
        }*/

        public enum Filter {
            ALL,
            TYPE,
            FACTORY,
            SERIES,
            GROUP,
            FACTORY_SERIES,
            FACTORY_GROUP,
            SERIES_GROUP,
            FACTORY_SERIES_GROUP
        }
    }
}