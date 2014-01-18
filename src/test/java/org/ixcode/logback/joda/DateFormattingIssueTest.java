package org.ixcode.logback.joda;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DateFormattingIssueTest {

    @Test
    public void format_with_java() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

        String timestamp = df.format(new Date(1390060787128L));

        System.out.println("Timestamp with ZZ in java: " + timestamp);

        assertThat(timestamp, is("2014-01-18T15:59:47.128+0000 GMT"));
    }

    @Test
    public void format_in_different_timezone() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX z");
        df.setTimeZone(TimeZone.getTimeZone("CST"));

        String timestamp = df.format(new Date(1390060787128L));

        System.out.println("Timestamp with XXX in java but in CST: " + timestamp);

        assertThat(timestamp, is("2014-01-18T09:59:47.128-06:00 CST"));
    }

    /**
     * According to this:
     * http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
     *
     * However, see http://stackoverflow.com/questions/2201925/converting-iso8601-compliant-string-to-java-util-date
     *
     * http://queforum.com/programming-languages-basics/531855-java-generic-support-iso-8601-format-java-6-a.html
     *
     * Doesn't seem to work at all
     *
     * The pattern XXX should format the time offset as -00:00 but on mymac it does not.
     * Have also tried it on ubuntu with both the oracle and open jdks
     */
    @Test
    public void format_with_java_properly() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX z");

        String timestamp = df.format(new Date(1390060787128L));

        System.out.println("Timestamp with XXX in java: " + timestamp);

        assertThat(timestamp, is("2014-01-18T15:59:47.128Z GMT"));

    }

    @Test
    public void only_format_the_offset() {
        DateFormat df = new SimpleDateFormat("XXX");

        String timestamp = df.format(new Date(1390060787128L));

        System.out.println("Timestamp with only XXX in java: " + timestamp);

        assertThat(timestamp, is("Z"));
    }

    /**
     * Joda time however, formats it properly
     */
    @Test
    public void format_with_joda() {
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

        String timestamp = df.print(1390060787128L);

        System.out.println("Timestamp with ZZ in joda: " + timestamp);

        assertThat(timestamp, is("2014-01-18T15:59:47.128+00:00 GMT"));
    }

    /**
     * Joda time however, formats it properly
     */
    @Test
    public void format_with_joda_short() {
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-DD'T'HH:mm:ss.SSSZ z");

        String timestamp = df.print(1390060787128L);

        System.out.println("Timestamp with Z in joda: " + timestamp);

        assertThat(timestamp, is("2014-01-18T15:59:47.128+0000 GMT"));
    }
    /**
     * Joda time however, formats it properly
     */
    @Test
    public void format_with_joda_UTC() {
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-DD'T'HH:mm:ss.SSS'Z' z").withZoneUTC();

        String timestamp = df.print(1390060787128L);

        System.out.println("Timestamp with UTC in joda: " + timestamp);

        assertThat(timestamp, is("2014-01-18T15:59:47.128Z UTC"));
    }
}