// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace;


import javax.annotation.concurrent.ThreadSafe;
import javax.annotation.processing.Generated;

/**
 * {@link EventTraceProperties} defines the set of System Properties used to control the JEventTrace behavior.
 */
@ThreadSafe
public final class EventTraceProperties {
    private static final String PREFIX = "com.github.jeventtrace.";
    /**
     * Enables the tracer for the process.
     */
    public static final String ENABLED = PREFIX + "enabled";

    @Generated("jacoco exclude")
    private EventTraceProperties() {

    }
}
