// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl;

import javax.annotation.processing.Generated;
import java.io.IOException;
import java.io.Writer;

public final class EUtil {

    @Generated("jacoco exclude")
    private EUtil() {

    }

    public static String checkNotNullOrEmpty(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return str;
    }

    public static long systemNanoTime() {
        return System.nanoTime();
    }

    public static void writeLong(Writer w, long value) throws IOException {
        w.write(Long.toString(value));
    }

    public static void writeStringEscaped(Writer w, String value) throws IOException {
        if (value.contains("\"")) {
            w.write(value.replace("\"", "\\\""));
        } else {
            w.write(value);
        }
    }
}
