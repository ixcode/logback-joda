package org.ixcode.logback.joda;

import ch.qos.logback.classic.PatternLayout;

public class JodaTimePatternLayout extends PatternLayout {

    public JodaTimePatternLayout() {
        defaultConverterMap.put("d", JodaTimeDateConverter.class.getName());
        defaultConverterMap.put("date", JodaTimeDateConverter.class.getName());
    }
}