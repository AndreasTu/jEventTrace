// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl.data;

public enum ETraceType {
    METADATA("M"),
    DURATION_START("B"),
    DURATION_END("E"),
    INSTANT("I"),
    
    ;

    private final String tag;

    ETraceType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
