package my.mapkn3.configurator.view;

import my.mapkn3.configurator.model.CommercialOffer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

public class MyDocxView extends AbstractDocxView {
    @Override
    protected XWPFDocument buildDocxDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CommercialOffer commercialOffer = (CommercialOffer) model.get("commercialOffer");

        XWPFDocument document = new XWPFDocument();
        XWPFTable table = document.createTable(1, 4);
        XWPFTableRow row = table.getRow(0);
        row.getCell(0).setText("Модель");
        row.getCell(1).setText("Цена");
        row.getCell(2).setText("Количество");
        row.getCell(3).setText("Стоимость");
        for (CommercialOffer.Item item : commercialOffer.getItems()) {
            XWPFTableRow newRow = table.createRow();
            newRow.getCell(0).setText(item.getModel());
            newRow.getCell(1).setText(item.getCost().toPlainString());
            newRow.getCell(2).setText(Long.toString(item.getCount()));
            newRow.getCell(3).setText(item.getCost().multiply(BigDecimal.valueOf(item.getCount())).toPlainString());
        }
        return document;
    }
}
