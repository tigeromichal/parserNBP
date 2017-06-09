package pl.parser.nbp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;

/**
 * 
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

        String sourceNamesUrl = null;
        String sourceUrl = null;
        String sourceUrlPart = null;

        ArrayList<String> sourceNames = null;

        int year = 0;
        int days = Days.daysBetween(dateFrom, dateTo).getDays() + 1;
        DateTime d = null;
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

            boolean found = false;

            for (int j = 0; j < sourceNames.size(); j++) {
                if (sourceNames.get(j).contains(datePart) && sourceNames.get(j).startsWith("c")) {
                    sourceUrlPart = sourceNames.get(j);
                    found = true;
                    break;
                }
            }

            if (found) {
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

    private ArrayList<String> loadTextFromUrl(final String source) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        URL url = new URL(source);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine = null;
        while ((inputLine = reader.readLine()) != null) {
            result.add(inputLine);
        }
        reader.close();

        return result;
    }

    private Dao<ExchangeRate> dao;

}
