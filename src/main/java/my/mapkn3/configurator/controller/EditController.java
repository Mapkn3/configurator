package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EditController extends MainController {
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String allEntityList() {
        return "editEntity";
    }

    @RequestMapping(value = "/edit/type/{id}", method = RequestMethod.GET)
    public String editType(@PathVariable("id") long id, final ModelMap model) {
        TypeEntity typeEntity = typeService.getTypeById(id);
        if (typeEntity.equals(typeService.getDefaultType())) {
            return "editEntity";
        }
        model.addAttribute("type", typeEntity);
        return "edit/editType";
    }

    @RequestMapping(value = "/edit/type/{id}", method = RequestMethod.POST)
    public String saveType(@PathVariable("id") long id, final TypeEntity typeEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "edit/editType";
        }
        typeService.updateType(typeEntity);
        model.clear();
        return "redirect:/edit";
    }

    @RequestMapping(value = "/edit/factory/{id}", method = RequestMethod.GET)
    public String editFactory(@PathVariable("id") long id, final ModelMap model) {
        FactoryEntity factoryEntity = factoryService.getFactoryById(id);
        if (factoryEntity.equals(factoryService.getDefaultFactory())) {
            return "editEntity";
        }
        model.addAttribute("factory", factoryEntity);
        return "edit/editFactory";
    }

    @RequestMapping(value = "/edit/factory/{id}", method = RequestMethod.POST)
    public String saveType(@PathVariable("id") long id, final FactoryEntity factoryEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "edit/editFactory";
        }
        factoryService.updateFactory(factoryEntity);
        model.clear();
        return "redirect:/edit";
    }

    @RequestMapping(value = "/edit/series/{id}", method = RequestMethod.GET)
    public String editSeries(@PathVariable("id") long id, final ModelMap model) {
        SeriesEntity seriesEntity = seriesService.getSeriesById(id);
        if (seriesEntity.equals(seriesService.getDefaultSeries())) {
            return "editEntity";
        }
        model.addAttribute("series", seriesEntity);
        return "edit/editSeries";
    }

    @RequestMapping(value = "/edit/series/{id}", method = RequestMethod.POST)
    public String saveSeries(@PathVariable("id") long id, final SeriesEntity seriesEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "edit/editSeries";
        }
        seriesService.updateSeries(seriesEntity);
        model.clear();
        return "redirect:/edit";
    }

    @RequestMapping(value = "/edit/group/{id}", method = RequestMethod.GET)
    public String editGroup(@PathVariable("id") long id, final ModelMap model) {
        GroupEntity groupEntity = groupService.getGroupById(id);
        if (groupEntity.equals(groupService.getDefaultGroup())) {
            return "editEntity";
        }
        model.addAttribute("group", groupEntity);
        return "edit/editGroup";
    }

    @RequestMapping(value = "/edit/group/{id}", method = RequestMethod.POST)
    public String saveGroup(@PathVariable("id") long id, final GroupEntity groupEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "edit/editGroup";
        }
        groupService.updateGroup(groupEntity);
        model.clear();
        return "redirect:/edit";
    }

    @RequestMapping(value = "/edit/item/{id}", method = RequestMethod.GET)
    public String editItem(@PathVariable("id") long id, final ModelMap model) {
        ItemEntity itemEntity = itemService.getItemById(id);
        if (itemEntity.equals(itemService.getDefaultItem())) {
            return "editEntity";
        }
        model.addAttribute("item", itemEntity);
        return "edit/editItem";
    }

    @RequestMapping(value = "/edit/item/{id}", method = RequestMethod.POST)
    public String saveItem(@PathVariable("id") long id, final ItemEntity itemEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "edit/editItem";
        }
        itemService.updateItem(itemEntity);
        model.clear();
        return "redirect:/edit";
    }
}
