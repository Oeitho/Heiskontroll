package com.oeitho.controller.elevator;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    public static final int TIME_TO_CLIMB_FLOOR = 3;

    private final List<Integer> destinations;

    private int currentFloor;

    public Elevator() {
        destinations = new ArrayList<>();
        currentFloor = 0;
    }

    public void summon(int destination) {
        if (!destinations.contains(destination)) {
            destinations.add(destination);
        }
    }

    public MovingDirection direction() {
        if (!destinations.isEmpty() && currentFloor != destinations.get(0)) {
            if (currentFloor < destinations.get(0)) {
                return MovingDirection.MOVING_UPWARDS;
            }
            return MovingDirection.MOVING_DOWNWARDS;
        }
        return MovingDirection.NOT_MOVING;
    }

    public int getEstimatedTimeToArrival(int destination) {
        return Math.abs(destination - currentFloor) * TIME_TO_CLIMB_FLOOR;
    }

    public void emergencyStop() {
        this.destinations.clear();
    }

    public enum MovingDirection {
        NOT_MOVING,
        MOVING_UPWARDS,
        MOVING_DOWNWARDS
    }

}