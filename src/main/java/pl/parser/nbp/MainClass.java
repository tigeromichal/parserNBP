package pl.parser.nbp;

/**
 * 
 * @author Michał Piasecki
 */
public class MainClass {
    public static void main(String[] args) {
        Dao dao = new XmlDao();
        try {
            dao.read("http://www.nbp.pl/kursy/xml/c073z070413.xml", "GBP");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
