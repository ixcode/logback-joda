package org.ixcode.logback.joda;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.CoreConstants;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * If you have control over how your program runs, you can simply call this method and it will set everything up
 * for you. Alterntively you can use the pattern and layout encoders in your logback.xml file
 */
public class LogbackJodaContext {

    public static void configureLoggerContextWithJoda() {
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
}