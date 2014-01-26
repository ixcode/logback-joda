# logback-joda

## Usage

Put this in your pom

```xml
<repositories>
  <repository>
      <id>ixcode</id>
      <url>http://repo.ixcode.org/public/</url>
      <snapshots>
          <enabled>true</enabled>
      </snapshots>
      <releases>
          <enabled>true</enabled>
      </releases>
  </repository>
</repositories>

<dependency>
    <groupId>org.ixcode</groupId>
    <artifactId>logback-joda</artifactId>
    <version>1.0</version>
</dependency>

```

Or if you prefer to dine on clojure

```clojure
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"
                 "ixcode-repo" "http://repo.ixcode.org/public"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.ixcode/logback-joda "1.0"]
                 ]
```
You can then do either this (Java)

```java
LogbackJodaContext.configure();

LogbackJodaContext.configureRootLogger(LogbackJodaContext.OFFSET_TIMEZONE_FORMAT);

log.info("Hello Logback and Joda!");
```

or in clojure

```clojure
(LogbackJodaContext/configure)
```

Note for both of these, you have to then re-configure the logging before it will work - existing and started appenders will still be using the old context.

OR you can just whack it in your logback.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">

        <encoder class="org.ixcode.logback.joda.JodaTimePatternLayoutEncoder">
            <pattern>[%d{yyyy-MM-DD'T'HH:mm:ss.SSSZZ z}]  %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.some.package" level="INFO"/>


    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

We've put some examples in the <a href="https://github.com/ixcode/logback-joda/tree/master/examples">examples</a> directory in both languages. The clojure one uses https://github.com/ixcode/clj-logging-config - ixcodes' fork of https://github.com/malcolmsparks/clj-logging-config so you will need to clone and install that too.

## WHY?

The standard formatters for logback (and infact log4j) use the standard java date time formatters. This is simple and effective. However, there is an issue:

  yyyy-MM-DD'T'HH:mm:ss.SSSZZ z

```java
DateFormat df = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

String timestamp = df.format(new Date(1390060787128L));

assertThat(timestamp, is("2014-01-18T15:59:47.128+0000 GMT"));
```

Notice the time format at the end `+0000`. <a href="http://tools.ietf.org/search/rfc3339#section-4.2">RFC 3339</a> says that the time offset should be like:

  +00:00

Although the documentation of Java 7 says that it should work by doing this `"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"`, it doesn't on my mac. But Joda time does

```java
DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

String timestamp = df.print(1390060787128L);

assertThat(timestamp, is("2014-01-18T15:59:47.128+00:00 GMT"));
```

This library allows you to set a format in logback using Joda Time instead.

Accorring to this <a href="http://stackoverflow.com/questions/2201925/converting-iso8601-compliant-string-to-java-util-date">stack overflow</a> question. This should all work.

The issue is to do with being in GMT. Here is the source code from SimpleDateFormat that is responsible for formatting the ISO8601 timezone:

```java
case PATTERN_ISO_ZONE:   // 'X'
  value = calendar.get(Calendar.ZONE_OFFSET)
          + calendar.get(Calendar.DST_OFFSET);

  if (value == 0) {
      buffer.append('Z');
      break;
  }

  value /=  60000;
  if (value >= 0) {
      buffer.append('+');
  } else {
      buffer.append('-');
      value = -value;
  }

  CalendarUtils.sprintf0d(buffer, value / 60, 2);
  if (count == 1) {
      break;
  }

  if (count == 3) {
      buffer.append(':');
  }
  CalendarUtils.sprintf0d(buffer, value % 60, 2);
  break;
```

The thing is, if you are in GMT, the `value` will be `0` and hence it puts a `Z` for UTC timezone, instead of `+00:00` which you would expect.

You can see this, if you change the timezone to be somewhere else:

```java
DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX z");
df.setTimeZone(TimeZone.getTimeZone("CST"));

String timestamp = df.format(new Date(1390060787128L));

System.out.println("Timestamp with XXX in java: " + timestamp);

assertThat(timestamp, is("2014-01-18T09:59:47.128-06:00 CST"));
```

So, we could re-implement SimpleDateFormat, or just use JodaTime which does it the way we want.

TODO:

https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide?__utma=246996102.492455761.1390084270.1390084270.1390084270.1&__utmb=246996102.10.10.1390084270&__utmc=246996102&__utmx=-&__utmz=246996102.1390084270.1.1.utmcsr=maven.apache.org|utmccn=(referral)|utmcmd=referral|utmcct=/guides/mini/guide-central-repository-upload.html&__utmv=-&__utmk=120621451#SonatypeOSSMavenRepositoryUsageGuide-11.WhatDoPeopleThinkAboutOSSRH

Publish on maven

Or follow this:

https://code.google.com/p/peter-lavalle/wiki/MavenOnDropBox



