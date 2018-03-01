package com.hashcode;

import java.lang.reflect.Array;
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
            fleet[i] = car;
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

    ArrayList<Car> iterate() {

        ArrayList<Ride> pendingRides = new ArrayList<Ride>();
        ArrayList<Ride> empty = new ArrayList<Ride>();

        for(int tick=0;tick<stepCount;tick++) {
            // Free cars
            for (int j = 0;j<vehicleCount;j++) {
                if(fleet[j].endTick <= tick) {
                    fleet[j].isFree = true;
                }
            }

            // delete impossible rides
            ArrayList<Ride> pendingCopy = new ArrayList<Ride>();
            for(Ride ride: pendingRides) {
                if (ride.f >= tick) {
                    continue;
                }
                pendingCopy.add(ride);
            }
            pendingRides = pendingCopy;

            // traverse rides
            Integer keyTick = new Integer(tick);
            ArrayList<Ride> ridesInTick = ridesInTime.get(keyTick);
            if(ridesInTick == null) {
                ridesInTick = (ArrayList<Ride>) empty.clone();
            }
            ridesInTick.addAll(pendingRides);

            for (Ride ride : ridesInTick) {
                // traverse cars
                Vehicle car = findCarForRide(ride);
                if (car != null) {
                    car.setRide(ride);
                }
                else {
                    pendingRides.add(ride);
                }
            }
        }

        ArrayList<Car> cars = new ArrayList<Car>();
        for(int i=0;i<vehicleCount;i++) {
            Car car = new Car();
            car.rides = fleet[i].rides;
            cars.add(car);
        }

        return cars;
    }

    Vehicle findCarForRide(Ride ride) {
        Vehicle bestCar = null;
        for (int i = 0;i<vehicleCount;i++) {
            Vehicle car = fleet[i];
            if (!car.isFree) {
                continue;
            }

            int distance = Math.abs(ride.a - car.endColumn) + Math.abs(ride.b - car.endRow);
            int startTick = car.endTick + distance;
            if (startTick < ride.s) {
                return car;
            } else {
                if (startTick + ride.distance <= ride.f) {
                    bestCar = car;
                }
            }
        }

        return bestCar;
    }

}
