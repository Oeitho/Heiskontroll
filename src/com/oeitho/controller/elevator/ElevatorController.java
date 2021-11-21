package com.oeitho.controller.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElevatorController {

    private final List<Elevator> elevators;

    public ElevatorController() {
        this.elevators = new ArrayList<>();
    }

    public void travel() {
        elevators.forEach(Elevator::travel);
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public void emergencyStop() {
        System.out.println("Stopping all elevators!");
        elevators.forEach(Elevator::emergencyStop);
        System.out.println("All elevators have been stopped");
    }

    public void summon(int floor) {
        System.out.println("Attempting to summon elevator to floor " + floor);
        Optional<Elevator> elevatorOptional = this.elevators.stream().min(ElevatorController::leastBusyElevator);
        elevatorOptional.ifPresentOrElse(
                elevator -> summonElevator(elevator, floor),
                () -> System.out.println("No elevators have been installed.")
                );
    }

    private void summonElevator(Elevator elevator, int floor) {
        System.out.println("Summoning elevator to floor " + floor);
        elevator.addDestination(floor);
    }

    private static int leastBusyElevator(Elevator a, Elevator b) {
        return a.getDestinations().size() - b.getDestinations().size();
    }

    @Override
    public String toString() {
        return "ElevatorController{" +
                "elevators=" + elevators +
                '}';
    }
}
