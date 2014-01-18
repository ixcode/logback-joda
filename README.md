# logback-joda

The standard formatters for logback (and infact log4j) use the standard java date time formatters. This is simple and effective. However, there is an issue:

    yyyy-MM-DD'T'HH:mm:ss.SSSZZ z

    DateFormat df = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

    String timestamp = df.format(new Date(1390060787128L));

    assertThat(timestamp, is("2014-01-18T15:59:47.128+0000 GMT"));
