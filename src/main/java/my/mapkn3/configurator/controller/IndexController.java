package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.CurrencyRates.CurrencyRatesService;
import my.mapkn3.configurator.model.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController extends MainController {

    @Autowired
    CurrencyRatesService currencyRatesService;

    @RequestMapping(value = "/currencyRate", method = RequestMethod.GET)
    public String currencyRate() {
        System.out.println(currencyRatesService.getUSD());
        System.out.println(currencyRatesService.getEUR());
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAll(ModelMap model) {
        currencyRatesService.updateCurrencyRates();
        model.addAttribute("filter", new FilterConfig());
        model.addAttribute("items", itemService.getAllItems());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String showItem(FilterConfig filter, ModelMap model) {
        List<ItemEntity> items = new ArrayList<>();
        switch (filter.getCurrentFilter()) {
            case ALL:
                items = itemService.getAllItems();
                break;
            case TYPE:
                items = itemService.getAllByType(typeService.getTypeById(filter.getCurrentId()));
                break;
            case FACTORY:
                items = itemService.getAllByFactory(factoryService.getFactoryById(filter.getCurrentId()));
                break;
            case SERIES:
                items = itemService.getAllBySeries(seriesService.getSeriesById(filter.getCurrentId()));
                break;
            case GROUP:
                items = itemService.getAllByGroup(groupService.getGroupById(filter.getCurrentId()));
                break;
        }
        model.addAttribute("filter", filter);
        model.addAttribute("items", items);
        return "index";
    }

    public static class FilterConfig {
        private String tfsgConfig;//type > factory > series > group

        public String getTfsgConfig() {
            return tfsgConfig;
        }

        public void setTfsgConfig(String tfsgConfig) {
            this.tfsgConfig = tfsgConfig;
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