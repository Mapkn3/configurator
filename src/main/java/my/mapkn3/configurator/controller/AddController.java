package my.mapkn3.configurator.controller;

import my.mapkn3.configurator.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AddController extends MainController {
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEntity() {
        return "newEntity";
    }

    @RequestMapping(value = "/new/type", method = RequestMethod.GET)
    public String newType(final TypeEntity typeEntity, final ModelMap model) {
        model.addAttribute("type", typeEntity);
        return "new/newType";
    }

    @RequestMapping(value = "/new/type", method = RequestMethod.POST)
    public String saveType(final TypeEntity typeEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "new/newType";
        }
        typeService.addType(typeEntity);
        model.clear();
        return "redirect:/new";
    }

    @RequestMapping(value = "/new/factory", method = RequestMethod.GET)
    public String newFactory(final FactoryEntity factoryEntity, final ModelMap model) {
        model.addAttribute("factory", factoryEntity);
        return "new/newFactory";
    }

    @RequestMapping(value = "/new/factory", method = RequestMethod.POST)
    public String saveFactory(final FactoryEntity factoryEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "new/newFactory";
        }
        factoryService.addFactory(factoryEntity);
        model.clear();
        return "redirect:/new";
    }

    @RequestMapping(value = "/new/series", method = RequestMethod.GET)
    public String newSeries(final SeriesEntity seriesEntity, final ModelMap model) {
        model.addAttribute("series", seriesEntity);
        return "new/newSeries";
    }

    @RequestMapping(value = "/new/series", method = RequestMethod.POST)
    public String saveSeries(final SeriesEntity seriesEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "new/newSeries";
        }
        SeriesEntity newSeries = seriesService.addSeries(seriesEntity);
        groupService.addGroup(new GroupEntity("Без группы", newSeries));
        model.clear();
        return "redirect:/new";
    }

    @RequestMapping(value = "/new/group", method = RequestMethod.GET)
    public String newGroup(final GroupEntity groupEntity, final ModelMap model) {
        model.addAttribute("group", groupEntity);
        return "new/newGroup";
    }

    @RequestMapping(value = "/new/group", method = RequestMethod.POST)
    public String saveGroup(final GroupEntity groupEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "new/newGroup";
        }
        groupService.addGroup(groupEntity);
        model.clear();
        return "redirect:/new";
    }

    @RequestMapping(value = "/new/item", method = RequestMethod.GET)
    public String newItem(final ItemEntity itemEntity, final ModelMap model) {
        model.addAttribute("item", itemEntity);
        return "new/newItem";
    }

    @RequestMapping(value = "/new/item", method = RequestMethod.POST)
    public String saveItem(final ItemEntity itemEntity, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "new/newItem";
        }
        itemEntity.setPathToPhoto("");
        itemEntity.setSeries(itemEntity.getGroup().getSeries());
        itemEntity.setFactory(itemEntity.getSeries().getFactory());
        itemEntity.setType(itemEntity.getFactory().getType());
        itemService.addItem(itemEntity);
        model.clear();
        return "redirect:/new";
    }

    @Value("true")
    private boolean isFirstStart;
    @RequestMapping(value = "/createDatabase", method = RequestMethod.GET)
    public String addAll() {
        if (isFirstStart) {
            System.out.println("Create database");
            List<CurrencyEntity> currencies = new ArrayList<CurrencyEntity>() {{
                add(currencyService.addCurrency(new CurrencyEntity("RUR", "₽")));
                add(currencyService.addCurrency(new CurrencyEntity("USD", "$")));
                add(currencyService.addCurrency(new CurrencyEntity("EUR", "€")));
            }};
            List<TypeEntity> types = new ArrayList<TypeEntity>() {{
                add(typeService.addType(new TypeEntity("ИБП")));
                add(typeService.addType(new TypeEntity("Батарейные комплекты")));
                add(typeService.addType(new TypeEntity("Стабилизаторы")));
                add(typeService.addType(new TypeEntity("ДГУ")));
            }};
            List<FactoryEntity> factories = new ArrayList<FactoryEntity>() {{
                add(factoryService.addFactory(new FactoryEntity("East (N-Power)", types.get(0))));
                add(factoryService.addFactory(new FactoryEntity("PowerBank (N-Power)", types.get(0))));
                add(factoryService.addFactory(new FactoryEntity("Siel (N-Power)", types.get(0))));

                add(factoryService.addFactory(new FactoryEntity("Кабинеты, стеллажи", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("Батарейные аксессуары", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("Sacred-sun", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("Delta", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("Fiamm", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("Leoch", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("LNB", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("WBR", types.get(1))));
                add(factoryService.addFactory(new FactoryEntity("CSB", types.get(1))));

                add(factoryService.addFactory(new FactoryEntity("East (N-Power)", types.get(2))));
                add(factoryService.addFactory(new FactoryEntity("Irem (N-Power)", types.get(2))));

                add(factoryService.addFactory(new FactoryEntity("Visa (N-Power)", types.get(3))));
            }};

            List<SeriesEntity> series = new ArrayList<SeriesEntity>() {{
                add(seriesService.addSeries(new SeriesEntity("Gamma-Vision", "ИБП Line-Interactive, AVR, 1ф/1ф, аппроксиматичный синус", "EA200", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Smart-Vision", "ИБП Line-Interactive, AVR, ШИМ-инвертор, чистый синус на выходе, 1ф/1ф", "EA600", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Pro-Vision Black", "Онлайн ИБП с двойным преобразованием напряжения, ВЧ, PF=0.8, 1ф/1ф", "EA900", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Pro-Vision Black M", "Онлайн ИБП с двойным преобразованием напряжения, ВЧ, PF=0.8", "EA900", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Pro-Vision Black M P", "ИБП с двойным преобразованием напряжения, On-line, ВЧ, PF=0.9, 1ф/1ф", "EA900II", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Pro-Vision Black 3/3", "Онлайн ИБП с двойным преобразованием напряжения, ВЧ, PF=0.9, 3ф/3ф", "EA900Pro", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Power-Vision Black HF", "Онлайн ИБП с двойным преобразованием напряжения, ВЧ, PF=0.9, 3ф/3ф", "EA990", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Power-Vision Black", "Онлайн ИБП с двойным преобразованием напряжения, НЧ, PF=0.8, 1ф/1ф, 3ф/1ф", "EA800", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Power-Vision Black W", "ИБП с двойным преобразованием напряжения, On-line, PF=0.9, 3ф/3ф", "EA890", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Power-Vision HF Module", "Модульные ИБП, онлайн с двойным преобразованием напряжения, ВЧ, PF=0.9/1.0, 3ф/3ф", "EA600", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Home-Vision W", "Домашние ИБП с линейно-интерактивной схемой и синусоидальным выходным напряжением, 1ф/1ф", "HOME UPS", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Power Vision 3F", "Трехфазные ИБП с гальванической развязкой", "", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Аксессуары", "", "", factories.get(0))));
                add(seriesService.addSeries(new SeriesEntity("Распродажа", "", "", factories.get(0))));

                add(seriesService.addSeries(new SeriesEntity("Smart-Vision Prime", "Line-interactive", "SC", factories.get(1))));
                add(seriesService.addSeries(new SeriesEntity("Grand-Vision", "On-Line", "KO", factories.get(1))));
                add(seriesService.addSeries(new SeriesEntity("Mega-Vision", "On-Line", "MO/ERT", factories.get(1))));
                add(seriesService.addSeries(new SeriesEntity("Master-Vision", "On-Line", "PO", factories.get(1))));
                add(seriesService.addSeries(new SeriesEntity("Home-Vision", "", "Home Inverter", factories.get(1))));
                add(seriesService.addSeries(new SeriesEntity("Аксессуары", "", "", factories.get(1))));
                add(seriesService.addSeries(new SeriesEntity("Распродажа", "", "", factories.get(1))));

                add(seriesService.addSeries(new SeriesEntity("N-Power Evo", "ИБП On-Line с двойным преобразованием, выходной изолирующий тр-р, 3ф/3ф", "Safe-Power Evo", factories.get(2))));
                add(seriesService.addSeries(new SeriesEntity("Опции", "", "", factories.get(2))));

                add(seriesService.addSeries(new SeriesEntity("РБК", "Батарейный кабинет", "", factories.get(3))));
                add(seriesService.addSeries(new SeriesEntity("C", "Батарейный кабинет", "", factories.get(3))));
                add(seriesService.addSeries(new SeriesEntity("BFT", "Батарейный кабинет", "", factories.get(3))));

                add(seriesService.addSeries(new SeriesEntity("Размыкатель", "Батарейный размыкатель", "", factories.get(4))));

                add(seriesService.addSeries(new SeriesEntity("SSP", "Аккумуляторная батарея, AGM, срок службы 5 лет", "SSP", factories.get(5))));
                add(seriesService.addSeries(new SeriesEntity("SP", "Аккумуляторная батарея, AGM, срок службы 10 лет", "SP", factories.get(5))));
                add(seriesService.addSeries(new SeriesEntity("SPG", "Аккумуляторная батарея, SPG, срок службы 10-12 лет", "SPG", factories.get(5))));

                add(seriesService.addSeries(new SeriesEntity("HR", "Аккумуляторная батарея, AGM, срок службы 5 лет", "HR", factories.get(6))));
                add(seriesService.addSeries(new SeriesEntity("HRL", "Аккумуляторная батарея, AGM, срок службы 10 лет", "", factories.get(6))));

                add(seriesService.addSeries(new SeriesEntity("FG", "Аккумуляторная батарея, срок службы 5 лет", "FG", factories.get(7))));
                add(seriesService.addSeries(new SeriesEntity("FGH", "Аккумуляторная батарея, срок службы 10 лет", "FGH", factories.get(7))));
                add(seriesService.addSeries(new SeriesEntity("FGL", "Аккумуляторная батарея, срок службы 10 лет", "FGL", factories.get(7))));
                add(seriesService.addSeries(new SeriesEntity("FGHL", "Аккумуляторная батарея, срок службы 10 лет", "FGHL", factories.get(7))));

                add(seriesService.addSeries(new SeriesEntity("DJW", "Аккумуляторная батарея, срок службы 5 лет", "DJW", factories.get(8))));
                add(seriesService.addSeries(new SeriesEntity("DJM", "Аккумуляторная батарея, срок службы 10 лет", "DJM", factories.get(8))));

                add(seriesService.addSeries(new SeriesEntity("MS", "Аккумуляторная батарея, AGM, срок службы 5 лет", "MS", factories.get(9))));
                add(seriesService.addSeries(new SeriesEntity("MM", "Аккумуляторная батарея, AGM, срок службы 10 лет", "MM", factories.get(9))));
                add(seriesService.addSeries(new SeriesEntity("MNG", "Аккумуляторная батарея, GEL, срок службы 10-12 лет", "MNG", factories.get(9))));

                add(seriesService.addSeries(new SeriesEntity("GP", "Аккумуляторная батарея, срок службы 5 лет", "GP", factories.get(10))));
                add(seriesService.addSeries(new SeriesEntity("GPL", "Аккумуляторная батарея, срок службы 10 лет", "GPL", factories.get(10))));
                add(seriesService.addSeries(new SeriesEntity("HR", "Аккумуляторная батарея, срок службы 5 лет", "HR", factories.get(10))));
                add(seriesService.addSeries(new SeriesEntity("HRL", "Аккумуляторная батарея, срок службы 10 лет", "HRL", factories.get(10))));

                add(seriesService.addSeries(new SeriesEntity("GP", "", "GP", factories.get(11))));
                add(seriesService.addSeries(new SeriesEntity("GPL", "", "GPL", factories.get(11))));
                add(seriesService.addSeries(new SeriesEntity("TPL", "", "TPL", factories.get(11))));
                add(seriesService.addSeries(new SeriesEntity("HR", "", "HR", factories.get(11))));
                add(seriesService.addSeries(new SeriesEntity("HRL", "", "HRL", factories.get(11))));

                add(seriesService.addSeries(new SeriesEntity("Eco", "Стабилизатор напряжения", "", factories.get(12))));

                add(seriesService.addSeries(new SeriesEntity("Oberon M", "Стабилизатор напряжения, однофазные, сервоприводный, электродинамический", "M", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Oberon A", "Трехфазные сервоприводные с регулировкой по среднефазному значению", "A", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Oberon Y", "Стабилизатор напряжения, трехфазный, сервоприводный с независимой регулировкой по фазам", "Y", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Oberon RM", "Однофазные электронные", "", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Oberon YE LC", "Трехфазные сетевые кондиционеры электронные", "", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Oberon Y LC", "Трехфазные сетевые кондиционеры", "", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Oberon M IP54", "Однофазные сервоприводные пылевлагозащищенные", "", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Oberon Y IP54", "Трехфазные сервоприводные пылевлагозащищенные", "", factories.get(13))));
                add(seriesService.addSeries(new SeriesEntity("Опции", "", "", factories.get(13))));

                add(seriesService.addSeries(new SeriesEntity("Perkins - Stamford", "ДГУ с двигателем Perkins", "", factories.get(14))));
                add(seriesService.addSeries(new SeriesEntity("John Deere - Stamford", "ДГУ с двигателем John Deere", "", factories.get(14))));
                add(seriesService.addSeries(new SeriesEntity("Опции", "", "", factories.get(14))));
            }};

            List<GroupEntity> defaultGroups = new ArrayList<>();
            for (SeriesEntity s : series) {
                defaultGroups.add(groupService.addGroup(new GroupEntity("Без группы", s)));
            }

            List<GroupEntity> groups = new ArrayList<GroupEntity>() {{
                add(groupService.addGroup(new GroupEntity("TW", series.get(0))));
                add(groupService.addGroup(new GroupEntity("RM", series.get(0))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(1))));
                add(groupService.addGroup(new GroupEntity("TW LT", series.get(1))));
                add(groupService.addGroup(new GroupEntity("RT", series.get(1))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(2))));
                add(groupService.addGroup(new GroupEntity("TW LT", series.get(2))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(3))));
                add(groupService.addGroup(new GroupEntity("TW LT", series.get(3))));
                add(groupService.addGroup(new GroupEntity("RT", series.get(3))));
                add(groupService.addGroup(new GroupEntity("RT LT", series.get(3))));
                add(groupService.addGroup(new GroupEntity("3/1", series.get(3))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(4))));
                add(groupService.addGroup(new GroupEntity("TW LT", series.get(4))));
                add(groupService.addGroup(new GroupEntity("RT", series.get(4))));
                add(groupService.addGroup(new GroupEntity("RT LT", series.get(4))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(5))));
                add(groupService.addGroup(new GroupEntity("TW LT", series.get(5))));

                add(groupService.addGroup(new GroupEntity("3/3", series.get(6))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(7))));
                add(groupService.addGroup(new GroupEntity("TW LT", series.get(7))));
                add(groupService.addGroup(new GroupEntity("3/1", series.get(7))));

                add(groupService.addGroup(new GroupEntity("3/3", series.get(8))));

                add(groupService.addGroup(new GroupEntity("PF=1.0", series.get(9))));
                add(groupService.addGroup(new GroupEntity("PF=0.9", series.get(9))));
                add(groupService.addGroup(new GroupEntity("BAT", series.get(9))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(10))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(11))));

                add(groupService.addGroup(new GroupEntity("TW", series.get(13))));
                add(groupService.addGroup(new GroupEntity("TW LT", series.get(13))));
                add(groupService.addGroup(new GroupEntity("TW", series.get(13))));
                add(groupService.addGroup(new GroupEntity("BAT", series.get(13))));

                add(groupService.addGroup(new GroupEntity("Tow", series.get(15))));
                add(groupService.addGroup(new GroupEntity("Tow LT", series.get(15))));
                add(groupService.addGroup(new GroupEntity("RM", series.get(15))));
                add(groupService.addGroup(new GroupEntity("3/1", series.get(15))));
                add(groupService.addGroup(new GroupEntity("3/3", series.get(15))));

                add(groupService.addGroup(new GroupEntity("Tow", series.get(16))));
                add(groupService.addGroup(new GroupEntity("Tow LT", series.get(16))));
                add(groupService.addGroup(new GroupEntity("ERT LT", series.get(16))));
                add(groupService.addGroup(new GroupEntity("3/1", series.get(16))));

                add(groupService.addGroup(new GroupEntity("3/1", series.get(21))));
                add(groupService.addGroup(new GroupEntity("6p/s", series.get(21))));
                add(groupService.addGroup(new GroupEntity("12p/s", series.get(21))));
                add(groupService.addGroup(new GroupEntity("6p/p", series.get(21))));
                add(groupService.addGroup(new GroupEntity("12p/p", series.get(21))));

                add(groupService.addGroup(new GroupEntity("1Ф", series.get(50))));
                add(groupService.addGroup(new GroupEntity("3Ф", series.get(50))));

                add(groupService.addGroup(new GroupEntity("10", series.get(51))));
                add(groupService.addGroup(new GroupEntity("15/20", series.get(51))));
                add(groupService.addGroup(new GroupEntity("15", series.get(51))));
                add(groupService.addGroup(new GroupEntity("20", series.get(51))));
                add(groupService.addGroup(new GroupEntity("25", series.get(51))));
                add(groupService.addGroup(new GroupEntity("30", series.get(51))));
                add(groupService.addGroup(new GroupEntity("15/20/25/30", series.get(51))));

                add(groupService.addGroup(new GroupEntity("10", series.get(52))));
                add(groupService.addGroup(new GroupEntity("15", series.get(52))));
                add(groupService.addGroup(new GroupEntity("20", series.get(52))));
                add(groupService.addGroup(new GroupEntity("25", series.get(52))));
                add(groupService.addGroup(new GroupEntity("30", series.get(52))));
                add(groupService.addGroup(new GroupEntity("15/20/25/30", series.get(52))));

                add(groupService.addGroup(new GroupEntity("10", series.get(53))));
                add(groupService.addGroup(new GroupEntity("15", series.get(53))));
                add(groupService.addGroup(new GroupEntity("20", series.get(53))));
                add(groupService.addGroup(new GroupEntity("25", series.get(53))));
                add(groupService.addGroup(new GroupEntity("30", series.get(53))));
                add(groupService.addGroup(new GroupEntity("15/20", series.get(53))));
                add(groupService.addGroup(new GroupEntity("15/20/25/30", series.get(53))));

                add(groupService.addGroup(new GroupEntity("10", series.get(58))));
                add(groupService.addGroup(new GroupEntity("15", series.get(58))));
                add(groupService.addGroup(new GroupEntity("20", series.get(58))));
                add(groupService.addGroup(new GroupEntity("25", series.get(58))));
            }};

            List<ItemEntity> items = new ArrayList<ItemEntity>() {{
                add(itemService.addItem(new ItemEntity("https:/", "400", "GMV400", "EA240", new BigDecimal(29.9), 1, "400ВА/240Вт, 12В/4.5Ач (5 мин.), 230В±25%, 100х280х140мм, 3,8кг", "/", "LED, 2xEU, USB, RJ11", currencies.get(1), groups.get(0))));
                add(itemService.addItem(new ItemEntity("https:/", "600", "GMV600", "EA260", new BigDecimal(31.2), 1, "600ВА/360Вт, 12В/7Ач (5 мин.), 230В±25%, 100х280х140мм, 4,3кг", "/", "LED, 2xEU, USB, RJ11", currencies.get(1), groups.get(0))));
                add(itemService.addItem(new ItemEntity("https:/", "800", "GMV800", "EA280", new BigDecimal(39.0), 1, "800ВА/480Вт, 12В/8Ач (5 мин.), 230В±25%, 100х280х140мм, 6,7кг", "/", "LED, 2xEU, USB, RJ11", currencies.get(1), groups.get(0))));
                add(itemService.addItem(new ItemEntity("https:/", "1200LCD", "GMV1200LCD", "EA2120", new BigDecimal(61.23), 1, "1200ВА/720Вт, 12В/2х7Ач (10 мин.), 230В±25%, 140х345х170мм, 9,5кг", "/", "LCD, 3xEU, USB, RJ45", currencies.get(1), groups.get(0))));
                add(itemService.addItem(new ItemEntity("https:/", "1500LCD", "GMV1500LCD", "EA2120", new BigDecimal(82.29), 1, "1500ВА/900Вт, 12В/2х8Ач (10 мин.), 230В±25%, 140х345х170мм, 10,7кг", "/", "LCD, 3xEU, USB, RJ45", currencies.get(1), groups.get(0))));
                add(itemService.addItem(new ItemEntity("https:/", "650PLUS", "GMV650PLUS", "EA260P", new BigDecimal(37.05), 1, "650ВА/390Вт, 12В/7Ач (5 мин.), 230В±25%, 185х280х95мм, 5.5кг", "/", "", currencies.get(1), groups.get(0))));
                add(itemService.addItem(new ItemEntity("https:/", "850PLUS", "GMV850PLUS", "EA280P", new BigDecimal(44.85), 1, "850ВА/510Вт, 12В/8Ач (5 мин.), 230В±25%, 185х280х95мм, 6.5кг", "/", "", currencies.get(1), groups.get(0))));

                add(itemService.addItem(new ItemEntity("https:/", "1200RM", "GMV1200RM", "EA2120", new BigDecimal(93.73), 1, "LCD, 2xIEC, USB, RJ11", "/", "1200ВА/720Вт, 10 мин., 230В±25%, 480х350х88 (2U)мм, 12,6кг", currencies.get(1), groups.get(1))));
                add(itemService.addItem(new ItemEntity("https:/", "1500RM", "GMV1500RM", "EA2150", new BigDecimal(117.0), 1, "LCD, 2xIEC, USB, RJ11", "/", "1500ВА/900Вт, 10 мин., 230В±25%, 480х350х150 (3U)мм, 15,8кг", currencies.get(1), groups.get(1))));

                add(itemService.addItem(new ItemEntity("https:/", "S1000N", "SVS1000N", "EA610", new BigDecimal(98.8), 1, "1000ВА / 800Вт, 2х7Ач, 220/230/240В±26%, 144х345х215мм, 12,2кг", "/", "3xIEC", currencies.get(1), groups.get(2))));
                add(itemService.addItem(new ItemEntity("https:/", "S1500N", "SVS1500N", "EA615", new BigDecimal(118.3), 1, "1500ВА / 1200Вт, 2х8Ач, 220/230/240В±26%, 144х345х215мм, 14,2кг", "/", "3xIEC", currencies.get(1), groups.get(2))));
                add(itemService.addItem(new ItemEntity("https:/", "S2000N", "SVS2000N", "EA620", new BigDecimal(144.3), 1, "2000ВА / 1600Вт, 3х9Ач, 220/230/240В±26%, 144х410х215мм, 18,5кг", "/", "3xIEC", currencies.get(1), groups.get(2))));
                add(itemService.addItem(new ItemEntity("https:/", "S3000N", "SVS3000N", "EA630", new BigDecimal(198.9), 1, "3000ВА / 2400Вт, 4х9Ач, 220/230/240В±26%, 190х467х335.5мм, 28,1кг", "/", "4xIEC", currencies.get(1), groups.get(2))));

                add(itemService.addItem(new ItemEntity("https:/", "S1000N LT", "SVS1000NLT", "EA610", new BigDecimal(95.5), 1, "1000ВА / 800Вт, 24В, 220/230/240В±26%, 144х345х215мм, 11,6кг", "/", "3xIEC", currencies.get(1), groups.get(3))));
                add(itemService.addItem(new ItemEntity("https:/", "S2000N LT", "SVS2000NLT", "EA620", new BigDecimal(131.04), 1, "2000ВА / 1600Вт, 48В, 220/230/240В±26%, 144х345х215мм, 17,8кг", "/", "3xIEC", currencies.get(1), groups.get(3))));
                add(itemService.addItem(new ItemEntity("https:/", "S3000N LT", "SVS3000NLT", "EA630", new BigDecimal(183.3), 1, "3000ВА / 2400Вт, 48В, 220/230/240В±26%, 190х467х335.5мм, 28кг", "/", "4xIEC", currencies.get(1), groups.get(3))));
            }};
            isFirstStart = false;
        }
        return "redirect:/";
    }
}
