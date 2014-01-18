# logback-joda

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