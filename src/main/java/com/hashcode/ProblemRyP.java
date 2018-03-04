package com.hashcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ProblemRyP {
    int rows;
    int columns;
    int bonus;
    int stepCount;

    int carCount;
    int rideCount;

    int minimum;

    ArrayList<Ride> rides;

    ProblemRyP(int rows, int columns, int carCount, int bonus, int stepCount, int rideCount, int minimum, ArrayList<Ride> rides) {
        this.rows = rows;
        this.columns = columns;
        this.carCount = carCount;
        this.bonus = bonus;
        this.stepCount = stepCount;
        this.rideCount = rideCount;
        this.minimum = minimum;

        this.rides = rides;
    }

    ArrayList<Car> solve() {

        ArrayList<SolutionRyP> solutions = new ArrayList<SolutionRyP>();
        int assignations[] = new int[rideCount];
        for(int i = 0;i<rideCount;i++) {
            assignations[i] = SolutionRyP.unassigned;
        }
        SolutionRyP firstNode = new SolutionRyP(assignations, 0, rides, carCount, stepCount, rideCount, bonus);
        solutions.add(firstNode);

        int maxExpanded = -1;

        int iteration = 0;

        int bestSolutionValue = 0;
        SolutionRyP bestSolution = null;
        while(!solutions.isEmpty()) {
            iteration++;
            solutions.sort(new Comparator<SolutionRyP>() {
                public int compare(SolutionRyP o1, SolutionRyP o2) {
                    int distance = o2.estimatedValue - o1.estimatedValue;
                    int distance2 = o2.cachedValue - o1.cachedValue;
                    //int depth = o2.upperIndex - o1.upperIndex;
                    if(distance != 0) {
                        return distance;
                    } else {
                        return distance2;
                    }
                }
            });

            SolutionRyP first = solutions.get(0);
            solutions.remove(0);

            if((iteration % 5000) == 0) {
                System.out.println("Evaluating candidate " + arrayAsString(first.assignations) + "(" + first.upperIndex + "), estimated: " + first.estimatedValue + ", real: " + first.cachedValue);
            }

            if (first.isSolution()) {
                if (first.rideValue() > bestSolutionValue) {
                    System.out.println("Found solution: " + first.rideValue() + "( > " + bestSolutionValue + ")");
                    bestSolution = first;
                    bestSolutionValue = first.rideValue();

                    ArrayList<SolutionRyP> lowNodes = new ArrayList<SolutionRyP>();
                    for (SolutionRyP sol : solutions) {
                        if (sol.estimatedValue() <= bestSolutionValue) {
                            lowNodes.add(sol);
                        }
                    }
                    solutions.removeAll(lowNodes);
                    System.out.println("Removed " + lowNodes.size() + " nodes");
                }
            }

            // expand nodes
            if (first.upperIndex > maxExpanded) {   // so we don't repeat solutions
                if (first.upperIndex < rideCount) {
                    int index = first.upperIndex;
                    for (int nextCarNumber = SolutionRyP.unassigned; nextCarNumber < carCount; nextCarNumber++) {
                        int nextAssignations[] = first.assignations.clone();
                        nextAssignations[index] = nextCarNumber;

                        SolutionRyP widthSolution = new SolutionRyP(nextAssignations, first.upperIndex + 1, rides, carCount, stepCount, rideCount, bonus);
                        if (widthSolution.isValid() && widthSolution.estimatedValue > bestSolutionValue && widthSolution.estimatedValue > minimum) {
                            solutions.add(0, widthSolution);
                        }
                    }
                }
                maxExpanded = first.upperIndex;
            }
        }

        ArrayList<Car> cars = new ArrayList<Car>();
        for(int i=0;i<carCount;i++) {
            Car car = new Car();
            car.rides = bestSolution.vehicles.get(i).rides;
            cars.add(car);
        }

        return cars;
    }

    String arrayAsString(int array[]) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        int size = array.length;
        if (size > 0) {
            buffer.append(array[0]);
        }
        for (int i=1;i<size;i++) {
            buffer.append(",");
            buffer.append(array[i]);
        }
        buffer.append("]");

        return buffer.toString();
    }
}
