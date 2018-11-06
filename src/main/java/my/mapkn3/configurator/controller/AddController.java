package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddController extends MainController {
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEntity() {
        return "newEntity";
    }

    @RequestMapping(value = "/new/type", method = RequestMethod.GET)
    public String newType(final TypeEntity typeEntity, final ModelMap model) {
        model.addAttribute("type", typeEntity);
        return "newType";
    }

    @RequestMapping(value = "/new/type", method = RequestMethod.POST)
    public String saveType(final TypeEntity typeEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "newType";
        }
        typeService.addType(typeEntity);
        model.clear();
        return "redirect:/new";
    }

    @RequestMapping(value = "/new/factory", method = RequestMethod.GET)
    public String newFactory(final FactoryEntity factoryEntity, final ModelMap model) {
        model.addAttribute("factory", factoryEntity);
        return "newFactory";
    }

    @RequestMapping(value = "/new/factory", method = RequestMethod.POST)
    public String saveFactory(final FactoryEntity factoryEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "newFactory";
        }
        factoryService.addFactory(factoryEntity);
        model.clear();
        return "redirect:/new";
    }

    @RequestMapping(value = "/new/series", method = RequestMethod.GET)
    public String newSeries(final SeriesEntity seriesEntity, final ModelMap model) {
        model.addAttribute("series", seriesEntity);
        return "newSeries";
    }

    @RequestMapping(value = "/new/series", method = RequestMethod.POST)
    public String saveSeries(final SeriesEntity seriesEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "newSeries";
        }
        seriesService.addSeries(seriesEntity);
        model.clear();
        return "redirect:/new";
    }
    @RequestMapping(value = "/new/group", method = RequestMethod.GET)
    public String newGroup(final GroupEntity groupEntity, final ModelMap model) {
        model.addAttribute("group", groupEntity);
        return "newGroup";
    }

    @RequestMapping(value = "/new/group", method = RequestMethod.POST)
    public String saveGroup(final GroupEntity groupEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "newGroup";
        }
        groupService.addGroup(groupEntity);
        model.clear();
        return "redirect:/new";
    }

    @RequestMapping(value = "/new/item", method = RequestMethod.GET)
    public String newItem(final ItemEntity itemEntity, final ModelMap model) {
        model.addAttribute("item", itemEntity);
        return "newItem";
    }

    @RequestMapping(value = "/new/item", method = RequestMethod.POST)
    public String saveItem(final ItemEntity itemEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "newItem";
        }
        itemEntity.setSeries(itemEntity.getGroup().getSeries());
        itemEntity.setFactory(itemEntity.getSeries().getFactory());
        itemEntity.setType(itemEntity.getFactory().getType());
        itemService.addItem(itemEntity);
        model.clear();
        return "redirect:/new";
    }
}
