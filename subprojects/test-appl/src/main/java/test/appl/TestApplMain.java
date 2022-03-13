// SPDX-License-Identifier: Apache-2.0
package test.appl;

import com.github.jeventtrace.EventTrace;

import java.io.IOException;
import java.nio.file.Paths;

public class TestApplMain {

    public static void main(String[] args) throws IOException {
        System.out.println("Started Test-Appl.");
        try {
            try {
                EventTrace.eventDuration("Test-Appl", TestApplMain::run);
            } finally {
                EventTrace.getEventTracer().writeEvents(Paths.get("./testEvents.json").toAbsolutePath());
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            System.err.println("Test-Appl failed.");
        }
        System.err.println("Test-Appl shutdown.");
    }

    private static void run() {
        try {
            Thread.sleep(1000);
            EventTrace.eventInstant("SleepWakeup");
            EventTrace.eventDurationEx("RunInner", () -> {
                Thread.sleep(1000);
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
