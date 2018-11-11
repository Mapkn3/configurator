package my.mapkn3.configurator.CurrencyRates;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class CurrencyRatesService {
    private String usd = null;
    private String eur = null;

    public String getUSD() {
        if (this.usd == null) {
            updateCurrencyRates();
        }
        return this.usd;
    }

    public String getEUR() {
        if (this.eur == null) {
            updateCurrencyRates();
        }
        return this.eur;
    }

    public void updateCurrencyRates() {
        String addr = "http://www.cbr.ru/scripts/XML_daily.asp";
        try {
            URL url = new URL(addr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream in = conn.getInputStream();

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(in);

            doc.getDocumentElement().normalize();
            NodeList listOfCurrencyRates = doc.getElementsByTagName("Valute");
            for (int s = 0; s < listOfCurrencyRates.getLength(); s++) {
                Node firstPersonNode = listOfCurrencyRates.item(s);
                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) firstPersonNode;
                    if (getElementValue(element, "CharCode").equals("USD")) {
                        this.usd = getElementValue(element, "Value");
                    }
                    if (getElementValue(element, "CharCode").equals("EUR")) {
                        this.eur = getElementValue(element, "Value");
                    }
                    /*
                    System.out.println(getElementValue(element, "NumCode") + ", "
                            + getElementValue(element, "CharCode") + ", "
                            + getElementValue(element, "Name") + ", "
                            + getElementValue(element, "Value"));
                    */
                }
            }
            conn.disconnect();
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    private String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        Element elmnt = (Element) nodeList.item(0);
        NodeList ndlst = elmnt.getChildNodes();
        return ndlst.item(0).getNodeValue().trim();
    }
}
