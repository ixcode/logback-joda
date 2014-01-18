package org.ixcode.logback.joda;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class StandardOutCapture {

    public static String captureStandardOutputFor(Runnable work) {
        PrintStream oldOut = System.out;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream tempOut = new PrintStream(baos);
            System.setOut(tempOut);
            work.run();
            tempOut.flush();
            return baos.toString();
        } finally {
            System.setOut(oldOut);
        }
    }

    private StandardOutCapture() {}
}