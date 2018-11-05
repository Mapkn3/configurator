package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.model.*;
import my.mapkn3.configurator.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    CurrencyService currencyService;

    @Autowired
    TypeService typeService;

    @Autowired
    FactoryService factoryService;
    @Autowired
    SeriesService seriesService;
    @Autowired
    GroupService groupService;

    @Autowired
    ItemService itemService;

    @ModelAttribute("allCurrencies")
    public List<CurrencyEntity> allCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @ModelAttribute("allTypes")
    public List<TypeEntity> allTypes() {
        return typeService.getAllTypes();
    }

    @ModelAttribute("allFactories")
    public List<FactoryEntity> allFactories() {
        return factoryService.getAllFactories();
    }

    @ModelAttribute("allSeries")
    public List<SeriesEntity> allSeries() {
        return seriesService.getAllSeries();
    }

    @ModelAttribute("allGroups")
    public List<GroupEntity> allGroups() {
        return groupService.getAllGroups();
    }
}
