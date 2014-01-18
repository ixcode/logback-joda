package org.ixcode.logback.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CachingJodaDateTimeFormatter {

    private final String pattern;

    private DateTimeFormatter dateTimeFormatter;
    private long lastTimestamp = -1;
    private String cachedStr = null;


    public CachingJodaDateTimeFormatter(String pattern) {
        this.pattern = pattern;
        dateTimeFormatter = DateTimeFormat.forPattern(pattern);
    }

    /**
     * Joda formatters are threadsafe so we don't need the sunchronisation that happens with java version
     *
     * @see http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html
     */
    public String format(long now) {

        if (now != lastTimestamp) {
            lastTimestamp = now;
            cachedStr = dateTimeFormatter.print(new DateTime());
        }
        return cachedStr;
    }

    public void changeTimeZoneTo(DateTimeZone tz) {
        dateTimeFormatter = DateTimeFormat.forPattern(pattern).withZone(tz);
    }
}

