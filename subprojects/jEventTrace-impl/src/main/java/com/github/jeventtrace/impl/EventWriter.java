// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl;

import com.github.jeventtrace.impl.data.ITraceEvent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

final class EventWriter {

    EventWriter() {

    }

    void write(Path file, EventLog events) {
        try (var writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            writer.write("{\"traceEvents\":[\n");
            var it = events.getEvents().iterator();
            while (it.hasNext()) {
                ITraceEvent event = it.next();
                event.write(writer);
                if (it.hasNext()) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]}");
            writer.flush();
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to write jEventTrace data to file '" + file.toAbsolutePath() + "'.", ex);
        }
    }
}
