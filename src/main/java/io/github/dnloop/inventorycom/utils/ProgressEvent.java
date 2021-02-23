package io.github.dnloop.inventorycom.utils;

public class ProgressEvent {

    private final int current ;
    private final int max ;

    public ProgressEvent(int current, int max) {
        this.current = current ;
        this.max = max ;
    }

    public int getCurrent() {
        return current ;
    }

    public int getMax() {
        return max ;
    }
}

