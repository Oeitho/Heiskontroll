package com.oeitho.controller.elevator;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    public static final int TIME_TO_CLIMB_FLOOR = 3;

    private final List<Integer> destinations;

    private int currentFloor;

    public Elevator() {
        this.destinations = new ArrayList<>();
        this.currentFloor = 0;
    }

    public List<Integer> getDestinations() {
        return this.destinations;
    }

    public void addDestination(int destination) {
        if (!this.destinations.contains(destination) && destination != this.currentFloor) {
            MovingDirection direction = direction();
            if (direction == MovingDirection.MOVING_DOWNWARDS) {
                if (addDestinationDuringDownwardsDirection(destination)) return;
            } else if (direction == MovingDirection.MOVING_UPWARDS) {
                if (addDestinationDuringUpwardsDirection(destination)) return;
            }
            this.destinations.add(destination);
        }
    }

    private boolean addDestinationDuringUpwardsDirection(int destination) {
        if (destination < this.currentFloor) {
            for (int i = 0; i < this.destinations.size(); i++) {
                if (this.destinations.get(i) < this.currentFloor && this.destinations.get(i) > destination) {
                    this.destinations.add(i, destination);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < this.destinations.size(); i++) {
                if (this.destinations.get(i) < this.currentFloor || this.destinations.get(i) > destination) {
                    this.destinations.add(i, destination);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean addDestinationDuringDownwardsDirection(int destination) {
        if (destination > this.currentFloor) {
            for (int i = 0; i < this.destinations.size(); i++) {
                if (this.destinations.get(i) > this.currentFloor && this.destinations.get(i) < destination) {
                    this.destinations.add(i, destination);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < this.destinations.size(); i++) {
                if (this.destinations.get(i) > this.currentFloor || this.destinations.get(i) < destination) {
                    this.destinations.add(i, destination);
                    return true;
                }
            }
        }
        return false;
    }

    public MovingDirection direction() {
        if (!this.destinations.isEmpty() && this.currentFloor != this.destinations.get(0)) {
            if (this.currentFloor < this.destinations.get(0)) {
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
            this.currentFloor--;
        }
        else if (direction == MovingDirection.MOVING_UPWARDS) {
            this.currentFloor++;
        }
        if (this.destinations.contains(this.currentFloor)) {
            System.out.println("Arrived at floor " + this.currentFloor);
            this.destinations.remove((Integer) this.currentFloor);
        }
    }

    public int getEstimatedTimeToArrival(int destination) {
        if (destination == this.currentFloor) {
            return 0;
        }
        MovingDirection direction = direction();
        if (direction == MovingDirection.MOVING_DOWNWARDS) {
            if (destination > this.currentFloor) {
                int lowestFloor = this.destinations.stream().min(Integer::compareTo).orElse(0);
                return (this.currentFloor - lowestFloor) * TIME_TO_CLIMB_FLOOR + // Moving downwards
                        (destination - lowestFloor) * TIME_TO_CLIMB_FLOOR;  // Moving upwards toward the destination
            }
        }
        else if (direction == MovingDirection.MOVING_UPWARDS) {
            if (destination < this.currentFloor) {
                int highestFloor = this.destinations.stream().max(Integer::compareTo).orElse(0);
                return (highestFloor - this.currentFloor) * TIME_TO_CLIMB_FLOOR + // Moving upwards
                        (highestFloor - destination) * TIME_TO_CLIMB_FLOOR;  // Moving downwards toward the destination
            }
        }
        return Math.abs(destination - this.currentFloor) * TIME_TO_CLIMB_FLOOR;
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
                "destinations=" + this.destinations +
                ", currentFloor=" + this.currentFloor +
                ", direction=" + direction() +
                '}';
    }
}