package com.github.jeventtrace.impl

import com.google.gson.Gson
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

abstract class AbstractFileSpecification extends Specification {
    @TempDir
    def tempDir

    Path newEventFile() {
        return Files.createTempFile(tempDir, "jeventTrace", ".json")
    }

    List<Object> assertIsValidEventGson(Path file) {
        return file.toFile().withReader { r ->
            return new Gson().fromJson(r, Map.class)
        }.get("traceEvents") as List<Object>
    }
}
