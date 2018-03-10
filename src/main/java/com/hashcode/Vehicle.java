package com.hashcode;

import java.util.ArrayList;

public class Vehicle {
    public int startTick;
    public int endColumn;
    public int endRow;
    public int endTick;
    public boolean isFree;

    public ArrayList<Ride> rides;

    Vehicle(int row, int column) {
        startTick = 0;
        endTick = 0;
        isFree = true;
        endRow = row;
        endColumn = column;

        rides = new ArrayList<Ride>();
    }

    void setRide(Ride ride) {
        int distX = ride.a - endColumn;
        int distY = ride.b - endRow;
        int timeToStart = Math.abs(distX) + Math.abs(distY);

        startTick = Math.max(ride.s, endTick + timeToStart);
        endTick = startTick + ride.distance;
        isFree = false;
        endRow = ride.y;
        endColumn = ride.x;

        rides.add(ride);
    }
}
