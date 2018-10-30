package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.model.*;
import my.mapkn3.configurator.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class IndexController {
    private final CurrencyService currencyService;

    private final TypeService typeService;

    private final FactoryService factoryService;
    private final SeriesService seriesService;
    private final GroupService groupService;

    private final ItemService itemService;

    @Autowired
    public IndexController(CurrencyService currencyService, TypeService typeService, FactoryService factoryService, SeriesService seriesService, GroupService groupService, ItemService itemService) {
        this.currencyService = currencyService;
        this.typeService = typeService;
        this.factoryService = factoryService;
        this.seriesService = seriesService;
        this.groupService = groupService;
        this.itemService = itemService;
    }

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAll() {
        return "index";
    }
}