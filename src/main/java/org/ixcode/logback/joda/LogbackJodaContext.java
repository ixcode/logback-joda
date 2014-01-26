package org.ixcode.logback.joda;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * If you have control over how your program runs, you can simply call this method and it will set everything up
 * for you. Alterntively you can use the pattern and layout encoders in your logback.xml file
 *
 * You will need to do this, then reconfigure the standard appender for it to work.
 *
 */
public class LogbackJodaContext {

    public static final String OFFSET_TIMEZONE_FORMAT = "[%d{yyyy-MM-DD'T'HH:mm:ss.SSSZZ z}] - %msg%n";

    public static void configure() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.putObject(CoreConstants.PATTERN_RULE_REGISTRY, createJodaPatternRules());
    }

    private static Map<String, String> createJodaPatternRules() {
        Map<String, String> rules = new HashMap<>();

        rules.put("d", JodaTimeDateConverter.class.getName());
        rules.put("date", JodaTimeDateConverter.class.getName());

        return rules;
    }

    private LogbackJodaContext() {}

    public static Logger configureRootLogger(String pattern) {
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