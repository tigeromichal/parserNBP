package pl.parser.nbp;

import java.util.Arrays;
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
        String inputString = null;

        boolean exceptionFlag = false;
        do {
            System.out.println("Podaj datę początkową w formacie dd-MM-yyyy");
            inputString = scanner.next();

            try {
                dateFrom = formatter.parseDateTime(inputString);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionFlag = true;
            }
        } while (dateFrom.getYear() < 2002 || exceptionFlag);

        exceptionFlag = false;
        do {
            System.out.println("Podaj datę końcową w formacie dd-MM-yyyy");
            inputString = scanner.next();
            try {
                dateTo = formatter.parseDateTime(inputString);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionFlag = true;
            }
        } while (dateTo.compareTo(dateFrom) < 0 || exceptionFlag);

        exceptionFlag = false;
        String[] currencies = { "USD", "EUR", "CHF", "GBP" };
        do {
            System.out.println("Podaj symbol waluty (USD, EUR, CHF lub GBP)");
            inputString = scanner.next();
        } while (!Arrays.asList(currencies).contains(inputString));

        scanner.close();

        Dao<ExchangeRate> dao = new XmlDao();
        ExchangeRatesLoader loader = new ExchangeRatesLoader(dao);
        ExchangeRateContainer container = loader.loadBetween(dateFrom, dateTo, inputString);
        // container.printAll();

        System.out.println("Odchylenie standardowe kursów sprzedaży: "
                + Double.toString(container.getSellingRateStandardDeviation()));

        System.out.println("Średni kurs kupna: " + Double.toString(container.getBuyingRateAverage()));

    }
}
