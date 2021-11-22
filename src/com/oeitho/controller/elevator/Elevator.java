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

    public List<Integer> getDestinations() {
        return this.destinations;
    }

    public void addDestination(int destination) {
        if (!destinations.contains(destination) && destination != currentFloor) {
            MovingDirection direction = direction();
            if (direction == MovingDirection.MOVING_DOWNWARDS) {
                if (addDestinationDuringDownwardsDirection(destination)) return;
            } else if (direction == MovingDirection.MOVING_UPWARDS) {
                if (addDestinationDuringUpwardsDirection(destination)) return;
            }
            destinations.add(destination);
        }
    }

    private boolean addDestinationDuringUpwardsDirection(int destination) {
        if (destination < currentFloor) {
            for (int i = 0; i < destinations.size(); i++) {
                if (destinations.get(i) < currentFloor && destinations.get(i) > destination) {
                    destinations.add(i, destination);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < destinations.size(); i++) {
                if (destinations.get(i) < currentFloor || destinations.get(i) > destination) {
                    destinations.add(i, destination);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean addDestinationDuringDownwardsDirection(int destination) {
        if (destination > currentFloor) {
            for (int i = 0; i < destinations.size(); i++) {
                if (destinations.get(i) > currentFloor && destinations.get(i) < destination) {
                    destinations.add(i, destination);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < destinations.size(); i++) {
                if (destinations.get(i) > currentFloor || destinations.get(i) < destination) {
                    destinations.add(i, destination);
                    return true;
                }
            }
        }
        return false;
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

    public void travel() {
        // Removes and returns first element in destinations list to currentFloor variable
        MovingDirection direction = direction();
        if (direction == MovingDirection.MOVING_DOWNWARDS) {
            currentFloor--;
        }
        else if (direction == MovingDirection.MOVING_UPWARDS) {
            currentFloor++;
        }
        if (destinations.contains(currentFloor)) {
            System.out.println("Arrived at floor " + currentFloor);
            destinations.remove((Integer) currentFloor);
        }
    }

    public int getEstimatedTimeToArrival(int destination) {
        if (destination == currentFloor) {
            return 0;
        }
        MovingDirection direction = direction();
        if (direction == MovingDirection.MOVING_DOWNWARDS) {
            if (destination > currentFloor) {
                int lowestFloor = destinations.stream().min(Integer::compareTo).orElse(0);
                return (currentFloor - lowestFloor) * TIME_TO_CLIMB_FLOOR + // Moving downwards
                        (destination - lowestFloor) * TIME_TO_CLIMB_FLOOR;  // Moving upwards toward the destination
            }
        }
        else if (direction == MovingDirection.MOVING_UPWARDS) {
            if (destination < currentFloor) {
                int highestFloor = destinations.stream().max(Integer::compareTo).orElse(0);
                return (highestFloor - currentFloor) * TIME_TO_CLIMB_FLOOR + // Moving upwards
                        (highestFloor - destination) * TIME_TO_CLIMB_FLOOR;  // Moving downwards toward the destination
            }
        }
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

    @Override
    public String toString() {
        return "Elevator{" +
                "destinations=" + destinations +
                ", currentFloor=" + currentFloor +
                ", direction=" + direction() +
                '}';
    }
}