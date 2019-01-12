package my.mapkn3.configurator.view;

import my.mapkn3.configurator.CurrencyRates.CurrencyRatesService;
import my.mapkn3.configurator.model.CommercialOffer;
import word.utils.Utils;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.elements.TableV2;
import word.w2004.elements.tableElements.TableCell;
import word.w2004.elements.tableElements.TableRow;
import word.w2004.style.ParagraphStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyDocView extends AbstractDocView {
    @Override
    protected String buildDocDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, Date time) {
        CommercialOffer commercialOffer = (CommercialOffer) model.get("commercialOffer");
        CurrencyRatesService currencyRatesService = commercialOffer.getCurrencyRatesService();
        Date currencyRateTime = currencyRatesService.getTime();
        String number = new SimpleDateFormat("ddMMyy - HHmmss").format(time);
        String currencyRateTimeString = new SimpleDateFormat("dd.MM.yyyy").format(currencyRateTime);
        BigDecimal usd = currencyRatesService.getUSD();
        BigDecimal eur = currencyRatesService.getEUR();

        TableV2 tableV2 = new TableV2();
        tableV2.addRow(TableRow.with(
                TableCell.with(Paragraph.with("Модель").withStyle().align(ParagraphStyle.Align.CENTER).create()).withStyle().bgColor("DDDDDD").create(),
                TableCell.with(Paragraph.with("Цена").withStyle().align(ParagraphStyle.Align.CENTER).create()).withStyle().bgColor("DDDDDD").create(),
                TableCell.with(Paragraph.with("Количество").withStyle().align(ParagraphStyle.Align.CENTER).create()).withStyle().bgColor("DDDDDD").create(),
                TableCell.with(Paragraph.with("Стоимость").withStyle().align(ParagraphStyle.Align.CENTER).create()).withStyle().bgColor("DDDDDD").create())
                .withStyle().bold().create());
        List<Paragraph> info;
        for (CommercialOffer.Item item : commercialOffer.getItems()) {
            info = new ArrayList<>();
            info.add(Paragraph.withPieces(
                    ParagraphPiece.with(item.getModel()).withStyle().bold().create(),
                    ParagraphPiece.with(" (артикул: " + item.getArticle() + ")\n").withStyle().italic().create()
            ).create());
            info.add(Paragraph.withPieces(ParagraphPiece.with(item.getDescription()+"\n").withStyle().italic().create()).create());
            info.add(Paragraph.withPieces(ParagraphPiece.with(item.getUrl()).withStyle().italic().create()).create());
            tableV2.addRow(TableRow.with(
                    TableCell.with(info).withStyle().vAlign(true).create(),
                    TableCell.with(Paragraph.with(item.getCurrency().getSymbol() + "\u00A0" + commercialOffer.calculateWithDiscount(item.getCost()).setScale(2, RoundingMode.HALF_UP)).withStyle().align(ParagraphStyle.Align.CENTER).create()).withStyle().vAlign(true).create(),
                    TableCell.with(Paragraph.with(Long.toString(item.getCount())).withStyle().align(ParagraphStyle.Align.CENTER).create()).withStyle().vAlign(true).create(),
                    TableCell.with(Paragraph.with(item.getCurrency().getSymbol() + "\u00A0" + commercialOffer.calculateWithDiscount(item.getCost()).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(item.getCount()))).withStyle().align(ParagraphStyle.Align.CENTER).create()).withStyle().vAlign(true).create()
            ));
        }
        tableV2.addRow(
                TableRow.with(
                        TableCell.with(
                                Paragraph.withPieces(
                                        ParagraphPiece.with("Итого: ").create(),
                                        ParagraphPiece.with(commercialOffer.getCurrency().getSymbol() + "\u00A0" + commercialOffer.getTotalPrice()).withStyle().bold().create()
                                ).withStyle().align(ParagraphStyle.Align.RIGHT).create()
                        ).withStyle().gridSpan(4).create()
                )
        );

        String xmlTemplate = Utils.readFile("template.xml");

        xmlTemplate = xmlTemplate.replace("number", number);
        xmlTemplate = xmlTemplate.replace("date", currencyRateTimeString);
        xmlTemplate = xmlTemplate.replace("usd", usd.toPlainString());
        xmlTemplate = xmlTemplate.replace("eur", eur.toPlainString());
        xmlTemplate = xmlTemplate.replace("commercialOffer", tableV2.getContent());

        return xmlTemplate;
    }
}
