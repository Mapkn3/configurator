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
    private BigDecimal discount;
    private List<Item> items;

    public CommercialOffer(CurrencyRatesService currencyRatesService, CurrencyEntity currency) {
        this.currencyRatesService = currencyRatesService;
        this.currency = currency;
        this.discount = BigDecimal.ZERO;
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
        updateCostForCurrentCurrencyForAllItems();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void toggleDiscount() {
        this.discount = this.discount.negate();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(ItemEntity itemEntity) {
        Item item = new Item(itemEntity);
        updateCostForCurrentCurrency(item);
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

    public BigDecimal getCurrencyRateForCurrency(CurrencyEntity currency) {
        BigDecimal currencyRate = BigDecimal.ONE;
        if (currency.getName().equals("EUR")) {
            currencyRate = currencyRatesService.getEUR();
        }
        if (currency.getName().equals("USD")) {
            currencyRate = currencyRatesService.getUSD();
        }
        return currencyRate;
    }

    public BigDecimal getCostForCurrency(CurrencyEntity oldCurrency, CurrencyEntity newCurrency, BigDecimal cost) {
        BigDecimal multiplier = getCurrencyRateForCurrency(oldCurrency);
        BigDecimal divider = getCurrencyRateForCurrency(newCurrency);
        return cost.multiply(multiplier).divide(divider, 6, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
    }

    public void updateCostForCurrentCurrency(Item item) {
        item.setCostForCurrentCurrency(getCostForCurrency(item.getCurrency(), currency, item.getCost().setScale(2, RoundingMode.HALF_UP)));
    }

    public void updateCostForCurrentCurrencyForAllItems() {
        for (Item item : items) {
            updateCostForCurrentCurrency(item);
        }
    }

    public BigDecimal calculateWithDiscount(BigDecimal price) {
        BigDecimal discountNormalize = discount.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);
        BigDecimal discountValue = price.multiply(discountNormalize);
        BigDecimal totalPrice = price.add(discountValue);
        return totalPrice;
    }

    public BigDecimal getTotalPrice() {
        updateCostForCurrentCurrencyForAllItems();
        BigDecimal price = items.stream()
                .map(item -> item.getCostForCurrentCurrency().multiply(BigDecimal.valueOf(item.count)))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal totalPrice = calculateWithDiscount(price);
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
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
        private BigDecimal costForCurrentCurrency;

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
            this.costForCurrentCurrency = item.getCost();
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

        public BigDecimal getCostForCurrentCurrency() {
            return costForCurrentCurrency;
        }

        public void setCostForCurrentCurrency(BigDecimal costForCurrentCurrency) {
            this.costForCurrentCurrency = costForCurrentCurrency;
        }
    }
}
