package ru.bona.shortener.domain.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * URLEntry
 *
 * @author Kontsur Alex (bona)
 * @since 23.11.14
 */
public class URLEntry implements Serializable {

    private static final long serialVersionUID = 499864100276130433L;

    private long id;

    @NotNull
    private String sourceURL;

    private String shortedURL;


    protected URLEntry() {
    }

    public URLEntry(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public long getId() {
        return id;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setShortedURL(String shortedURL) {
        this.shortedURL = shortedURL;
    }

    public String getShortedURL() {
        return shortedURL;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("URLEntry");
        sb.append("{id=").append(id);
        sb.append(", sourceURL='").append(sourceURL).append('\'');
        sb.append(", shortedURL='").append(shortedURL).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        URLEntry entry = (URLEntry) obj;
        if (id != entry.id) {
            return false;
        }
        if (!sourceURL.equals(entry.sourceURL)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + sourceURL.hashCode();
        return result;
    }
}
