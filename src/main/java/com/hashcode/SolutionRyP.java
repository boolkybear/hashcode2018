package com.hashcode;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;

public class SolutionRyP {
    final static short unassigned = -1;

    short assignations[];
    int upperIndex;

    ArrayList<Ride> rides;
    int carCount;
    int stepCount;
    int rideCount;
    int bonus;

    int cachedValue;
    int estimatedValue;
    boolean isValid;
    boolean isAllBooked;

    int nodeNumber;

    ArrayList<Vehicle> vehicles;

    SolutionRyP(short assignations[], int upperIndex, ArrayList<Ride> rides, int carCount, int stepCount, int rideCount, int bonus, int nodeNumber) {
        this.assignations = assignations;
        this.upperIndex = upperIndex;

        this.rides = rides;
        this.carCount = carCount;
        this.stepCount = stepCount;
        this.rideCount = rideCount;
        this.bonus = bonus;

        this.cachedValue = 0;
        this.estimatedValue = 0;
        this.isValid = true;

        this.nodeNumber = nodeNumber;

        vehicles = new ArrayList<Vehicle>();
        for (int i = 0;i<carCount;i++) {
            vehicles.add(new Vehicle(0, 0));
        }

        isAllBooked = true;
        for(int i=0;i<upperIndex;i++) {
            if(assignations[i] == unassigned) {
                isAllBooked = false;
                break;
            }
        }
        replayMovements();
    }

    boolean isValid() {
        return isValid;
    }

    boolean isSolution() {
        return upperIndex >= rideCount;
    }

    int rideValue() {
        return cachedValue;
    }

    int estimatedValue() {
        if (estimatedValue != 0) {
            return estimatedValue;
        }

        int accumulated = rideValue();
        for (int i=upperIndex;i<rideCount;i++) {
            accumulated += rides.get(i).distance;
        }
        int pending = rideCount - upperIndex;
        accumulated += (pending * bonus);

        estimatedValue = accumulated;
        return accumulated;
    }

    void replayMovements() {
        ArrayList<Pair<Ride,Vehicle>> filteredRides = new ArrayList<Pair<Ride, Vehicle>>();
        for (int i = 0;i<upperIndex;i++) {
            if (assignations[i] == unassigned) {
                continue;
            }

            filteredRides.add(new Pair<Ride, Vehicle>(rides.get(i), vehicles.get(assignations[i])));
        }
        filteredRides.sort(new Comparator<Pair<Ride, Vehicle>>() {
            public int compare(Pair<Ride, Vehicle> o1, Pair<Ride, Vehicle> o2) {
                return o1.getKey().s - o2.getKey().s;
            }
        });

        int totalScore = 0;
        for (Pair<Ride, Vehicle> pair : filteredRides) {

            Ride ride = pair.getKey();
            Vehicle coche = pair.getValue();

            int distanciaAlOrigen = Math.abs(coche.endColumn - ride.a) + Math.abs(coche.endRow - ride.b);
            int tickToReach = coche.endTick + distanciaAlOrigen;
            int tickToReachEnd = tickToReach + ride.distance;
            if(tickToReachEnd > ride.f) {
                isValid = false;
                break;
            } else {
                if (tickToReach <= ride.s) {
                    totalScore += bonus;
                }
                totalScore += ride.distance;
                coche.setRide(ride);
            }
        }

        if (isValid) {
            cachedValue = totalScore;
            int tmp = estimatedValue();
        }
    }
}
