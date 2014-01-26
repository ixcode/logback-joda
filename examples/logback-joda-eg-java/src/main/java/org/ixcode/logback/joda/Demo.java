package org.ixcode.logback.joda;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import static org.ixcode.logback.joda.LogbackJodaContext.*;


public class Demo {

    public static final Logger log = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {

        configureLoggerContextWithJoda();

        configureRootLogger(OFFSET_TIMEZONE_FORMAT);

        log.info("Hello Logback and Joda!");

    }

    private Demo() {}
}