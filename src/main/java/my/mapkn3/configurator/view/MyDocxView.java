package my.mapkn3.configurator.view;

import my.mapkn3.configurator.CurrencyRates.CurrencyRatesService;
import my.mapkn3.configurator.model.CommercialOffer;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class MyDocxView extends AbstractDocxView {
    @Override
    protected XWPFDocument buildDocxDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CommercialOffer commercialOffer = (CommercialOffer) model.get("commercialOffer");

        XWPFDocument document = new XWPFDocument();
        //document.createParagraph().createRun().addPicture(new FileInputStream("src/main/resources/images/logo.png"), Document.PICTURE_TYPE_PNG, "logo.png", Units.toEMU(4), Units.toEMU(1));
        XWPFTable headerTable = document.createTable(1, 5);
        XWPFTableRow header = headerTable.getRow(0);
        header.getCell(0).addParagraph().createRun().addPicture(new FileInputStream("src/main/resources/images/logo.png"), Document.PICTURE_TYPE_PNG, "logo.png", Units.toEMU(4), Units.toEMU(1));
        header.getCell(1).setText("(Россия-Италия)");
        header.getCell(2).setText("https://www.380v.ru/");
        header.getCell(3).setText("https://www.ибп.рф/");
        header.getCell(4).setText("https://www.стабилизатор.рф/");

        XWPFRun info = document.createParagraph().createRun();
        info.setText("ООО \"Эн-Пауэр\", Россия, Москва, 117513, ул. Островитянова, 4, офис 1; E-Mail: sales@n-power.ru \n" +
                "Москва: +7 (495) 740-30-85, (495) 956-19-19; Н.Новгород: +7 (831) 462-16-41\n" +
                "Казань: +7 (987) 290-64-05; Ростов-на-Дону: +7 (863) 298-11-93\n");

        XWPFTable table = document.createTable(1, 4);
        XWPFTableRow row = table.getRow(0);
        row.getCell(0).setText("Модель");
        row.getCell(1).setText("Цена");
        row.getCell(2).setText("Количество");
        row.getCell(3).setText("Стоимость");
        for (CommercialOffer.Item item : commercialOffer.getItems()) {
            XWPFTableRow newRow = table.createRow();
            newRow.getCell(0).setText(item.getModel() + "\n" +
                    "(артикул: " + item.getArticle() + ")" + "\n" +
                    item.getDescription() + "\n" +
                    item.getUrl()
            );
            newRow.getCell(1).setText(item.getCost().toPlainString());
            newRow.getCell(2).setText(Long.toString(item.getCount()));
            newRow.getCell(3).setText(item.getCost().multiply(BigDecimal.valueOf(item.getCount())).toPlainString());
        }
        table.createRow().getCell(3).setText("Итого: " + " " + commercialOffer.getCurrency().getSymbol() + " " + commercialOffer.getTotalPrice());

        CurrencyRatesService currencyRatesService = commercialOffer.getCurrencyRatesService();
        Date currencyRateTime = currencyRatesService.getTime();
        String currencyRateTimeString = currencyRateTime.getDay() + "." + currencyRateTime.getMonth() + "." + currencyRateTime.getYear();
        document.createParagraph().createRun().setText("ЦБ РФ на " + currencyRateTimeString + ": 1$ = " + currencyRatesService.getUSD() + "₽ / 1€ = " + currencyRatesService.getEUR() + "₽");

        document.createParagraph().createRun().setText("Срок действия предложения 1 месяц. Все цены с учетом НДС. Отгрузка с нашего склада. В стоимость оборудования не входят транспортировка, пуско-наладка, монтажные работы, командировочные расходы.");
        document.createParagraph().createRun().setText("С уважением и надеждой на сотрудничество,\n" +
                "Алена Борзова\n" +
                "alena@n-power.ru\n");
        return document;
    }
}
