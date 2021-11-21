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

    public List<Elevator> getElevators() {
        return this.elevators;
    }

    public void emergencyStop() {
        System.out.println("Stopping all elevators!");
        elevators.forEach(Elevator::emergencyStop);
        System.out.println("All elevators have been stopped");
    }

    public void summon(int floor) {
        System.out.println("Attempting to summon elevator to floor " + floor);
        Optional<Elevator> elevatorOptional = this.elevators.stream().min((a, b) -> compareFastestElevator(a, b, floor));
        elevatorOptional.ifPresentOrElse(
                elevator -> summonElevator(elevator, floor),
                () -> System.out.println("No elevators have been installed.")
                );
    }

    private void summonElevator(Elevator elevator, int floor) {
        System.out.println("Summoning elevator to floor " + floor);
        elevator.addDestination(floor);
    }

    private static int compareFastestElevator(Elevator a, Elevator b, int floor) {
        return a.getEstimatedTimeToArrival(floor) - b.getEstimatedTimeToArrival(floor);
    }

    @Override
    public String toString() {
        return "ElevatorController{" +
                "elevators=" + elevators +
                '}';
    }
}
