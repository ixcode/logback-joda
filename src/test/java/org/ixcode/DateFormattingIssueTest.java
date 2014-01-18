package org.ixcode;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DateFormattingIssueTest {

    @Test
    public void format_with_java() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSSZZ z");

        String timestamp = df.format(new Date(1390060787128L));

        assertThat(timestamp, is("2014-01-18T15:59:47.128+0000 GMT"));
    }
}