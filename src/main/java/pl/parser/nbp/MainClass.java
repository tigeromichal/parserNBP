package pl.parser.nbp;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * 
 * @author Michał Piasecki
 */
public class MainClass {
    public static void main(String[] args) {
        Dao<ExchangeRate> dao = new XmlDao();
        ExchangeRatesLoader loader = new ExchangeRatesLoader(dao);
        ExchangeRateContainer container = loader.loadBetween(new DateTime(2016, 12, 20, 0, 0),
                new DateTime(2017, 1, 10, 0, 0), "EUR");
        container.printAll();

    }
}
