package org.ixcode.logback.joda;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.util.StatusPrinter;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

import static java.lang.System.out;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.ixcode.logback.joda.LogbackJodaContext.configureLoggerContextWithJoda;
import static org.ixcode.logback.joda.StandardOutCapture.captureStandardOutputFor;

public class LogbackJodaContextTest {

    private TimeZone defaultTimeZone;

    @Before
    public void before_each_test() {
        defaultTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    @After
    public void after_each_test() {
        thaw_time();
        TimeZone.setDefault(defaultTimeZone);
    }


    @Test
    public void formats_properly_timezone() {
        configureLoggerContextWithJoda();

        freezeTimeAt(1390060787128L);

        final Logger logger = getRootLogger("[%d{yyyy-MM-DD'T'HH:mm:ss.SSSZZ z}] - %msg%n");

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

    private static Logger getRootLogger(String pattern) {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        ConsoleAppender a = (ConsoleAppender) logger.getAppender("console");

        a.stop();

        PatternLayoutEncoder le = (PatternLayoutEncoder) a.getEncoder();
        le.stop();

        le.setPattern(pattern);
        le.start();

        a.start();

        StatusPrinter.printInCaseOfErrorsOrWarnings(logger.getLoggerContext());
        return logger;
    }

}