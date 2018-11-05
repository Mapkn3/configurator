package my.mapkn3.configurator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController extends MainController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAll(ModelMap model) {
        model.clear();
        model.addAttribute("filter", new FilterConfig());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String showItem(FilterConfig filter) {
        if (filter.getFsgConfig().equals("")) {
            return "redirect:/";
        }
        System.out.println(filter.getFilter());
        return "redirect:/";
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

        public Filter getFilter() {
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
        }

        public enum Filter {
            ALL,
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