package pl.parser.nbp;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * @author Michał Piasecki
 */
public class ExchangeRatesLoader {

    public ExchangeRatesLoader(final Dao<ExchangeRate> dao) {
        this.dao = dao;
    }

    public ExchangeRateContainer loadBetween(final DateTime dateFrom, final DateTime dateTo,
                                             final String currencyCode) {
        ExchangeRateContainer container = new ExchangeRateContainer();

        DecimalFormat df = new DecimalFormat("00");
        int currentYear = new DateTime().getYear();

        String sourceNamesUrl;
        String sourceUrl;
        String sourceUrlPart;

        HashMap<String, String> sourceNames = null;

        int year = 0;
        int days = Days.daysBetween(dateFrom, dateTo).getDays() + 1;
        DateTime d;
        for (int i = 0; i < days; i++) {
            d = dateFrom.withFieldAdded(DurationFieldType.days(), i);
            if (year != d.getYear()) {
                year = d.getYear();
                if (year == currentYear) {
                    sourceNamesUrl = "http://www.nbp.pl/kursy/xml/dir.txt";
                } else {
                    sourceNamesUrl = "http://www.nbp.pl/kursy/xml/dir" + Integer.toString(year) + ".txt";
                }

                try {
                    sourceNames = loadTextFromUrl(sourceNamesUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String datePart = Integer.toString(year % 1000) + df.format(d.getMonthOfYear())
                    + df.format(d.getDayOfMonth());

            if (sourceNames.containsKey(datePart)) {
                sourceUrlPart = sourceNames.get(datePart) + datePart;
                try {
                    sourceUrl = "http://www.nbp.pl/kursy/xml/" + sourceUrlPart + ".xml";
                    container.add(dao.read(sourceUrl, currencyCode));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return container;
    }

    private HashMap<String, String> loadTextFromUrl(final String source) throws IOException {
        HashMap<String, String> result = new HashMap<String, String>();

        URL url = new URL(source);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            if (inputLine.startsWith("c")) {
                result.put(inputLine.substring(inputLine.length() - 6), inputLine.substring(0, inputLine.length() - 6));
            }
        }
        reader.close();

        return result;
    }

    private Dao<ExchangeRate> dao;

}
