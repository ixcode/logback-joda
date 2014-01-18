package org.ixcode;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DateFormattingIssueTest {

    @Test
    public void format_with_java() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

        String timestamp = df.format(new Date(1390060787128L));

        assertThat(timestamp, is("2014-01-18T15:59:47.128+0000 GMT"));
    }

    /**
     * According to this:
     * http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
     *
     * The pattern XXX should format the time offset as -00:00 but on mymac it does not.
     */
    @Test
    public void format_with_java_properly() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX z");

        String timestamp = df.format(new Date(1390060787128L));

        assertThat(timestamp, is("2014-01-18T15:59:47.128Z GMT"));

    }

    /**
     * Joda time however, formats it properly
     */
    @Test
    public void format_with_joda() {
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

        String timestamp = df.print(1390060787128L);

        assertThat(timestamp, is("2014-01-18T15:59:47.128+00:00 GMT"));
    }
}