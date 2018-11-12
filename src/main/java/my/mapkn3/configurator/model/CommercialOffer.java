package my.mapkn3.configurator.model;

import my.mapkn3.configurator.CurrencyRates.CurrencyRatesService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommercialOffer {
    private CurrencyRatesService currencyRatesService;
    private CurrencyEntity currency;
    private List<Item> items;

    public CommercialOffer(CurrencyRatesService currencyRatesService, CurrencyEntity currency) {
        this.currencyRatesService = currencyRatesService;
        this.currency = currency;
        this.items = new ArrayList<>();
    }

    public CurrencyRatesService getCurrencyRatesService() {
        return currencyRatesService;
    }

    public void setCurrencyRatesService(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(ItemEntity itemEntity) {
        Item item = new Item(itemEntity);
        for (Item i : items) {
            if (i.getId() == item.getId()) {
                i.setCount(i.getCount() + 1);
                return;
            }
        }
        items.add(item);
    }

    public void removeItemById(long id) {
        for (Item item : items) {
            if (item.getId() == id) {
                item.setCount(item.getCount() - 1);
            }
        }
        items = items.stream().filter(item -> item.count > 0).collect(Collectors.toList());
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.getCurrency().getName().equals("EUR") ?
                        item.getCost().multiply(currencyRatesService.getEUR()).multiply(BigDecimal.valueOf(item.count)) :
                        item.getCurrency().getName().equals("USD") ?
                                item.getCost().multiply(currencyRatesService.getUSD()).multiply(BigDecimal.valueOf(item.count)) :
                                item.getCost().multiply(BigDecimal.valueOf(item.count)))
                .map(cost -> currency.getName().equals("EUR") ?
                        cost.divide(currencyRatesService.getEUR(), 6, RoundingMode.HALF_UP) :
                        currency.getName().equals("USD") ?
                                cost.divide(currencyRatesService.getUSD(), 6, RoundingMode.HALF_UP) :
                                cost)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    public static class Item {
        private long id;
        private String model;
        private String article;
        private BigDecimal cost;
        private CurrencyEntity currency;
        private String description;
        private String url;
        private long count;

        public Item() {
        }

        public Item(ItemEntity item) {
            this.id = item.getId();
            this.model = item.getGroup().getSeries().getName() + " " + item.getModel();
            this.article = item.getOurArticle();
            this.cost = item.getCost();
            this.currency = item.getCurrency();
            this.description = item.getDescription();
            this.url = item.getUrl();
            this.count = 1;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getArticle() {
            return article;
        }

        public void setArticle(String article) {
            this.article = article;
        }

        public BigDecimal getCost() {
            return cost;
        }

        public void setCost(BigDecimal cost) {
            this.cost = cost;
        }

        public CurrencyEntity getCurrency() {
            return currency;
        }

        public void setCurrency(CurrencyEntity currency) {
            this.currency = currency;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }
}
