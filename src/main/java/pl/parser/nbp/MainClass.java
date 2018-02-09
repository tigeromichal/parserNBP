package pl.parser.nbp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Michał Piasecki
 */
public class MainClass {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dateToday = new DateTime();
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
                exceptionFlag = true;
            }
        } while (exceptionFlag || dateFrom.getYear() < 2002);

        exceptionFlag = false;
        do {
            System.out.println("Podaj datę końcową w formacie dd-MM-yyyy");
            inputString = scanner.next();
            try {
                dateTo = formatter.parseDateTime(inputString);
            } catch (Exception e) {
                exceptionFlag = true;
            }
        } while (exceptionFlag || dateTo.compareTo(dateFrom) < 0);

        if (dateTo.compareTo(dateToday) > 0) {
            dateTo = dateToday;
        }

        exceptionFlag = false;
        String[] currencies = {"USD", "EUR", "CHF", "GBP"};
        do {
            System.out.println("Podaj symbol waluty (USD, EUR, CHF lub GBP)");
            inputString = scanner.next();
        } while (!Arrays.asList(currencies).contains(inputString));

        scanner.close();

        Dao<ExchangeRate> dao = null;
        try {
            dao = new XmlDao();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        ExchangeRatesLoader loader = new ExchangeRatesLoader(dao);
        ExchangeRateContainer container = loader.loadBetween(dateFrom, dateTo, inputString);

        System.out.println("Odchylenie standardowe kursów sprzedaży: "
                + Double.toString(Math.round(container.getSellingRateStandardDeviation() * 1000) / 1000D));

        System.out.println(
                "Średni kurs kupna: " + Double.toString(Math.round(container.getBuyingRateAverage() * 1000) / 1000D));

    }
}
