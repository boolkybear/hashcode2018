package com.hashcode;

public class Vehicle {
    public int startTick;
    public int endColumn;
    public int endRow;
    public int endTick;
    public boolean isFree;

    Vehicle(int row, int column) {
        startTick = 0;
        endTick = 0;
        isFree = true;
        endRow = row;
        endColumn = column;
    }
}
