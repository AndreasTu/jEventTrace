# jEventTrace

Event Tracer for JVM Applications for creating trace files with processes, threads, durations and instants.

The event trace will write
the [Trace Event Format](https://docs.google.com/document/d/1CvAClvFfyA5R-PhYUmn5OOQtYMH4h6I0nSsKchNAySU/preview#).

The event trace file can be viewed with one of the following:

* Open the page in Chrome: `about:tracing`
* Visit <https://ui.perfetto.dev/> in a browser
* Use [Calapult](https://github.com/catapult-project/catapult/tree/master/tracing)

## Runtime

The project requires at least Java 17 to build and run.
Also, the project using the `jEventTrace` library requires at least Java 17.

## Usage

You can use jEventTrace via [jitpack.io](https://jitpack.io/#AndreasTu/jEventTrace) maven repository and the following
coordinates:

* `com.github.AndreasTu.jEventTrace:jEventTrace-api:<version>`
* `com.github.AndreasTu.jEventTrace:jEventTrace-impl:<version>`

You compile your application with `jEventTrace-api` on the compile classpath.

If you want to create traces, you add `jEventTrace-impl` to your runtime classpath and start your application with the
system
property `com.github.jeventtrace.enabled=true`.

If you do not create traces, you do not need to reference/ship `jEventTrace-impl`, it is only needed if tracing is
enabled.

### Code Usage

In your code you use the class `com.github.jeventtrace.EventTrace` to start a duration or fire an instant.

Sample:

```java
class YourClass {
    void yourMethod() {
        EventTrace.eventDuration("My operation", () -> {
            //Do some code
        });

        EventTrace.eventInstant("My Instant event");
    }
}
```

To get a trace file you need to call the following code, e.g. at application shutdown:

```java
class YourClass {
    void yourMethod() {
        EventTrace.getEventTracer().writeEvents(Paths.get("file_to_write.json"));
    }
}
```

After you have written the file, open it with one of the following:

* Open the page in Chrome: `about:tracing`
* Visit <https://ui.perfetto.dev/> in a browser
* Use [Calapult](https://github.com/catapult-project/catapult/tree/master/tracing)

## Contributors

* Creator:
    * Andreas Turban (GitHub: AndreasTu)
