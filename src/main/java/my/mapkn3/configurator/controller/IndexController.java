package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.CurrencyRates.CurrencyRatesService;
import my.mapkn3.configurator.model.CommercialOffer;
import my.mapkn3.configurator.model.CurrencyEntity;
import my.mapkn3.configurator.model.ItemEntity;
import my.mapkn3.configurator.view.MyDocxView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (currencyRatesService.getUSD() == null || currencyRatesService.getEUR() == null) {
            currencyRatesService.updateCurrencyRates();
        }
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
        commercialOffer.setCurrency(currentCurrency);
        model.addAttribute("commercialOffer", commercialOffer);
        return "index";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
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
        return "redirect:/";
    }

    @RequestMapping(value = "/increment/{id}", method = RequestMethod.POST)
    public String incrementItemEntityBalance(@PathVariable("id") long id) {
        ItemEntity item = itemService.getItemById(id);
        item.setBalance(item.getBalance() + 1);
        itemService.updateItem(item);
        return "redirect:/filter";
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public String addItem(@PathVariable("id") long id) {
        ItemEntity item = itemService.getItemById(id);
        if (item.getBalance() > 0) {
            item.setBalance(item.getBalance() - 1);
            commercialOffer.addItem(itemService.updateItem(item));
        }
        return "redirect:/filter";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public String removeItem(@PathVariable("id") long id) {
        ItemEntity item = itemService.getItemById(id);
        item.setBalance(item.getBalance() + 1);
        itemService.updateItem(item);
        commercialOffer.removeItemById(id);
        return "redirect:/filter";
    }

    @RequestMapping(value = "/recalculate", method = RequestMethod.POST)
    public String changeCurrencyForTotalPrice(CommercialOffer commercialOffer) {
        this.currentCurrency = commercialOffer.getCurrency();
        this.commercialOffer.setCurrency(this.currentCurrency);
        return "redirect:/filter";
    }

    @RequestMapping(value = "/sell", method = RequestMethod.POST)
    public String sellAllItems(ModelMap model) {
        commercialOffer.setCurrency(currentCurrency);
        commercialOffer.getItems().clear();
        return "redirect:/filter";
    }

    @RequestMapping(value = "/commercialOffer", method = RequestMethod.GET)
    public ModelAndView generateCommercialOffer() {
        Map<String, Object> model = new HashMap<>();
        model.put("commercialOffer", commercialOffer);
        return new ModelAndView(new MyDocxView(), model);
    }

    @RequestMapping(value = "/discount", params = {"plus"}, method = RequestMethod.POST)
    public String calculateDiscountPlus(CommercialOffer commercialOffer) {
        if (commercialOffer.getDiscount().signum() == -1) {
            commercialOffer.toggleDiscount();
        }
        this.commercialOffer.setDiscount(commercialOffer.getDiscount());
        return "redirect:/filter";
    }

    @RequestMapping(value = "/discount", params = {"minus"}, method = RequestMethod.POST)
    public String calculateDiscountMinus(CommercialOffer commercialOffer) {
        if (commercialOffer.getDiscount().signum() == 1) {
            commercialOffer.toggleDiscount();
        }
        this.commercialOffer.setDiscount(commercialOffer.getDiscount());
        return "redirect:/filter";
    }

    public static class FilterConfig {
        private CurrencyEntity currency;
        private String tfsgConfig;//type > factory > series > group

        public FilterConfig() {
            currency = null;
            tfsgConfig = "ALL";
        }

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

        public enum Filter {
            ALL,
            TYPE,
            FACTORY,
            SERIES,
            GROUP
        }
    }
}