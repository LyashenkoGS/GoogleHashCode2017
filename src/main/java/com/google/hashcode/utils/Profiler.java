package com.google.hashcode.utils;

import java.time.Duration;
import java.time.Instant;

/**
 * Simplest profiler
 *
 * @author Grigoriy Lyashenko (Grog).
 */
public class Profiler {

    private Instant start;

    public Profiler() {
        this.start = Instant.now();
    }

    /**
     * Calculated difference between start and finish time and output its String representation
     *
     * @param message optional message before performance output
     */
    public String measure(String message) {
        Instant end = Instant.now();
        return message + Duration.between(start, end).toMillis() + "ms";
    }

}
