package org.ixcode.logback.joda;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class LogbackJodaContextTest {


    @Test
    public void formats_properly_timezone() {
        LogbackJodaContext.configureLoggerContextWithJoda();

        Logger logger = getRootLogger("[%d{yyyy-MM-DD'T'HH:mm:ss.SSSZZ z}] - %msg%n");

        logger.info("Hello Bob");

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