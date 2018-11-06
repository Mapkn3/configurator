package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.model.ItemEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController extends MainController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAll(ModelMap model) {
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
        private long typeId;
        private String fsgConfig;//factory > series > group

        public long getTypeId() {
            return typeId;
        }

        public void setTypeId(long typeId) {
            this.typeId = typeId;
        }

        public String getFsgConfig() {
            return fsgConfig;
        }

        public void setFsgConfig(String fsgConfig) {
            this.fsgConfig = fsgConfig;
        }

        public Filter getCurrentFilter() {
            String[] params = fsgConfig.split(":");
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
            String[] params = fsgConfig.split(":");
            return Long.valueOf(params[1]);
        }

        /*public Filter getFilter() {
            List<Long> ids = Arrays.stream(fsgConfig.split("\\|")).map(Long::valueOf).collect(Collectors.toList());
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