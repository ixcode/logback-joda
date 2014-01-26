package org.ixcode.logback.joda;

import ch.qos.logback.classic.Logger;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.TimeZone;

import static java.lang.System.out;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.ixcode.logback.joda.LogbackJodaContext.configure;
import static org.ixcode.logback.joda.StandardOutCapture.captureStandardOutputFor;

public class LogbackJodaContextTest {

    private TimeZone defaultJavaTimeZone;
    private DateTimeZone defaultJodaTimeZone;

    @Before
    public void before_each_test() {
        defaultJavaTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

        defaultJodaTimeZone = DateTimeZone.getDefault();
        DateTimeZone.setDefault(DateTimeZone.forID("Europe/London"));
    }

    @After
    public void after_each_test() {
        TimeZone.setDefault(defaultJavaTimeZone);
        DateTimeZone.setDefault(defaultJodaTimeZone);
    }


    @Test
    public void formats_properly_timezone() {
        configure();

        freezeTimeAt(1390060787128L);

        final Logger logger = LogbackJodaContext.configureRootLogger(LogbackJodaContext.OFFSET_TIMEZONE_FORMAT);

        String output = captureStandardOutputFor(new Runnable() {
            @Override public void run() {
                logger.info("Think very carefully about time zones you should!");
            }
        });

        out.println("Logging output with joda configured: " + output);
        assertThat(output, is("[2014-01-18T15:59:47.128+00:00 GMT] - Think very carefully about time zones you should!\n"));

    }

    private static void freezeTimeAt(long milliseconds) {
        DateTimeUtils.setCurrentMillisFixed(milliseconds);
    }

    private void thaw_time() {
        DateTimeUtils.setCurrentMillisSystem();
    }

}