package com.hashcode;

import java.util.ArrayList;
import java.util.HashMap;

public class Problem {
    public int rows;
    public int columns;
    public int vehicleCount;
    public int bonus;
    public int stepCount;
    public int rideCount;

    Vehicle fleet[];
    HashMap<Integer, ArrayList<Ride>> ridesInTime;

    Problem(int rows, int columns, int vehicleCount, int bonus, int stepCount, int rideCount, ArrayList<Ride> rides) {
        this.rows = rows;
        this.columns = columns;
        this.vehicleCount = vehicleCount;
        this.bonus = bonus;
        this.stepCount = stepCount;
        this.rideCount = rideCount;

        fleet = new Vehicle[this.vehicleCount];
        for(int i = 0;i<vehicleCount;i++) {
            Vehicle car = new Vehicle(0, 0);
        }

        ridesInTime = new HashMap<Integer, ArrayList<Ride>>();
        for (Ride ride : rides) {
            Integer startPoint = new Integer(ride.s);
            ArrayList<Ride> ridesForTick = ridesInTime.get(startPoint);
            if(ridesForTick == null) {
                ridesForTick = new ArrayList<Ride>();
            }

            ridesForTick.add(ride);
            ridesInTime.put(startPoint, ridesForTick);
        }
    }


}
