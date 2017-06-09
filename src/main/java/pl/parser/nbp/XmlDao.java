package pl.parser.nbp;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Michał Piasecki
 */
public class XmlDao implements Dao<ExchangeRate>, AutoCloseable {

    public ExchangeRate read(final String url, final String currencyCode) throws IOException {
        ExchangeRate exchangeRate = null;
        Document doc = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new URL(url).openStream());
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Node node;
        Element element;
        NodeList setList = doc.getElementsByTagName("pozycja");
        for (int c = 0; c < setList.getLength(); c++) {
            node = setList.item(c);
            element = (Element) node;

            String code = null;
            try {
                code = ((Element) element.getElementsByTagName("kod_waluty").item(0)).getTextContent();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (code != null && code.equals(currencyCode)) {
                String buyingRateString = null;
                String sellingRateString = null;
                try {
                    buyingRateString = ((Element) element.getElementsByTagName("kurs_kupna").item(0)).getTextContent();
                    sellingRateString = ((Element) element.getElementsByTagName("kurs_sprzedazy").item(0))
                            .getTextContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    double buyingRate = Double.parseDouble(buyingRateString.replace(" ", "").replace(",", "."));
                    double sellingRate = Double.parseDouble(sellingRateString.replace(" ", "").replace(",", "."));
                    exchangeRate = new ExchangeRate(currencyCode, buyingRate, sellingRate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return exchangeRate;
    }

    public void write(final ExchangeRate container, final String url) throws IOException {

    }

    public void close() throws Exception {

    }

    @Override
    public void finalize() {
        try {
            this.close();
        } catch (Exception e) {

        }
    }

}
