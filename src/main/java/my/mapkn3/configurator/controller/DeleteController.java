package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class DeleteController extends MainController {
    @RequestMapping(value = "/delete/type/{id}", method = RequestMethod.GET)
    public String confirmDeleteType(@PathVariable("id") long id, final ModelMap model) {
        TypeEntity typeEntity = typeService.getTypeById(id);
        Map<String, String> description = new LinkedHashMap<>();
        description.put("title", "Тип");
        description.put("Название", typeEntity.getName());
        model.addAttribute("entity", "type");
        model.addAttribute("id", id);
        model.addAttribute("description", description);
        return "confirmDelete";
    }

    @RequestMapping(value = "/delete/type/{id}", method = RequestMethod.POST)
    public String deleteType(@PathVariable("id") long id) {
        TypeEntity typeEntity = typeService.getTypeById(id);
        if (!typeEntity.equals(typeService.getDefaultType())) {
            typeService.deleteType(typeEntity);
        }
        return "redirect:/edit";
    }

    @RequestMapping(value = "/delete/factory/{id}", method = RequestMethod.GET)
    public String confirmDeleteFactory(@PathVariable("id") long id, final ModelMap model) {
        FactoryEntity factoryEntity = factoryService.getFactoryById(id);
        Map<String, String> description = new LinkedHashMap<>();
        description.put("title", "Завод");
        description.put("Название", factoryEntity.getName());
        description.put("Тип производимой продукции", factoryEntity.getType().getName());
        model.addAttribute("entity", "factory");
        model.addAttribute("id", id);
        model.addAttribute("description", description);
        return "confirmDelete";
    }

    @RequestMapping(value = "/delete/factory/{id}", method = RequestMethod.POST)
    public String deleteFactory(@PathVariable("id") long id) {
        FactoryEntity factoryEntity = factoryService.getFactoryById(id);
        if (!factoryEntity.equals(factoryService.getDefaultFactory()))
        {
            factoryService.deleteFactory(factoryEntity);
        }
        return "redirect:/edit";
    }

    @RequestMapping(value = "/delete/series/{id}", method = RequestMethod.GET)
    public String confirmDeleteSeries(@PathVariable("id") long id, final ModelMap model) {
        SeriesEntity seriesEntity = seriesService.getSeriesById(id);
        Map<String, String> description = new LinkedHashMap<>();
        description.put("title", "Серия");
        description.put("Название", seriesEntity.getName());
        description.put("Описание", seriesEntity.getDescription());
        description.put("Артикул", seriesEntity.getArticle());
        description.put("Завод", String.format("[%s] %s", seriesEntity.getFactory().getType().getName(), seriesEntity.getFactory().getName()));
        model.addAttribute("entity", "series");
        model.addAttribute("id", id);
        model.addAttribute("description", description);
        return "confirmDelete";
    }

    @RequestMapping(value = "/delete/series/{id}", method = RequestMethod.POST)
    public String deleteSeries(@PathVariable("id") long id) {
        SeriesEntity seriesEntity = seriesService.getSeriesById(id);
        if (!seriesEntity.equals(seriesService.getDefaultSeries())) {
            seriesService.deleteSeries(seriesEntity);
        }
        return "redirect:/edit";
    }

    @RequestMapping(value = "/delete/group/{id}", method = RequestMethod.GET)
    public String confirmDeleteGroup(@PathVariable("id") long id, final ModelMap model) {
        GroupEntity groupEntity = groupService.getGroupById(id);
        Map<String, String> description = new LinkedHashMap<>();
        description.put("title", "Группа");
        description.put("Название", groupEntity.getName());
        description.put("Серия", String.format("[%s] %s | %s", groupEntity.getSeries().getFactory().getType().getName(), groupEntity.getSeries().getFactory().getName(), groupEntity.getSeries().getName()));
        model.addAttribute("entity", "group");
        model.addAttribute("id", id);
        model.addAttribute("description", description);
        return "confirmDelete";
    }

    @RequestMapping(value = "/delete/group/{id}", method = RequestMethod.POST)
    public String deleteGroup(@PathVariable("id") long id) {
        GroupEntity groupEntity = groupService.getGroupById(id);
        if (!groupEntity.equals(groupService.getDefaultGroup())) {
            groupService.deleteGroup(groupEntity);
        }
        return "redirect:/edit";
    }

    @RequestMapping(value = "/delete/item/{id}", method = RequestMethod.GET)
    public String confirmDeleteItem(@PathVariable("id") long id, final ModelMap model) {
        ItemEntity itemEntity = itemService.getItemById(id);
        Map<String, String> description = new LinkedHashMap<>();
        description.put("title", "Товар");
        description.put("Модель", itemEntity.getModel());
        description.put("Ссылка", itemEntity.getUrl());
        description.put("Внутренний артикул", itemEntity.getOurArticle());
        description.put("Артикул завода", itemEntity.getFactoryArticle());
        description.put("Цена", String.format("%s %s", itemEntity.getCost(), itemEntity.getCurrency().getSymbol()));
        description.put("Остаток", String.valueOf(itemEntity.getBalance()));
        description.put("Описание", itemEntity.getDescription());
        description.put("Комментарий",  itemEntity.getComment());
        description.put("Группа", String.format("[%s] %s | %s: %s", itemEntity.getGroup().getSeries().getFactory().getType().getName(), itemEntity.getGroup().getSeries().getFactory().getName(), itemEntity.getGroup().getSeries().getName(), itemEntity.getGroup().getName()));
        model.addAttribute("entity", "item");
        model.addAttribute("id", id);
        model.addAttribute("description", description);
        return "confirmDelete";
    }

    @RequestMapping(value = "/delete/item/{id}", method = RequestMethod.POST)
    public String deleteItem(@PathVariable("id") long id) {
        ItemEntity itemEntity = itemService.getItemById(id);
        if (!itemEntity.equals(itemService.getDefaultItem())) {
            itemService.deleteItem(itemEntity);
        }
        return "redirect:/edit";
    }
}
