package pl.parser.nbp;

import java.io.IOException;

/**
 * 
 * @author Michał Piasecki
 */
public abstract interface Dao<T> extends AutoCloseable {

    public T read(final String url, final String currencyCode) throws IOException;

    public void write(final T obj, final String url) throws IOException;

}
