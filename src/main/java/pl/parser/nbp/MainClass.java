package pl.parser.nbp;

import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * @author Michał Piasecki
 */
public class MainClass {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dateFrom = null;
        DateTime dateTo = null;

        Scanner scanner = new Scanner(System.in);
        String dateString = null;

        do {
            System.out.println("Podaj datę początkową w formacie dd-MM-yyyy");
            dateString = scanner.next();

            try {
                dateFrom = formatter.parseDateTime(dateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (dateFrom.getYear() < 2002);

        System.out.println("Podaj datę końcową w formacie dd-MM-yyyy");
        dateString = scanner.next();
        try {
            dateTo = formatter.parseDateTime(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Podaj symbol waluty (USD, EUR, CHF lub GBP)");
        String currency = scanner.next();
        
        scanner.close();

        Dao<ExchangeRate> dao = new XmlDao();
        ExchangeRatesLoader loader = new ExchangeRatesLoader(dao);
        ExchangeRateContainer container = loader.loadBetween(dateFrom, dateTo, currency);
        //container.printAll();

        System.out.println("Odchylenie standardowe kursów sprzedaży: "
                + Double.toString(container.getSellingRateStandardDeviation()));

        System.out.println("Średni kurs kupna: " + Double.toString(container.getBuyingRateAverage()));

    }
}
