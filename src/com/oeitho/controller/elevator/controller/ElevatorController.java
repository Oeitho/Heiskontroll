package com.oeitho.controller.elevator.controller;

import com.oeitho.controller.elevator.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElevatorController {

    private final List<Elevator> elevators;

    public ElevatorController() {
        this.elevators = new ArrayList<>();
    }

    public void travel() {
        this.elevators.forEach(Elevator::travel);
    }

    public void addElevator(Elevator elevator) {
        this.elevators.add(elevator);
    }

    public List<Elevator> getElevators() {
        return this.elevators;
    }

    public void emergencyStop() {
        System.out.println("Stopping all elevators!");
        this.elevators.forEach(Elevator::emergencyStop);
        System.out.println("All elevators have been stopped");
    }

    public int estimatedTimeToArrival(int floor) {
        return this.elevators.stream()
                .min((a, b) -> compareFastestElevator(a, b, floor))
                .map(elevator -> elevator.getEstimatedTimeToArrival(floor))
                .orElse(-1);
    }

    public void summon(int floor) {
        System.out.println("Attempting to summon elevator to floor " + floor);
        Optional<Elevator> elevatorOptional = this.elevators.stream().min((a, b) -> compareFastestElevator(a, b, floor));
        elevatorOptional.ifPresentOrElse(
                elevator -> summonElevator(elevator, floor),
                () -> System.out.println("No elevators have been installed.")
                );
    }

    private static int compareFastestElevator(Elevator a, Elevator b, int floor) {
        return a.getEstimatedTimeToArrival(floor) - b.getEstimatedTimeToArrival(floor);
    }

    private void summonElevator(Elevator elevator, int floor) {
        System.out.println("Summoning elevator to floor " + floor);
        elevator.addDestination(floor);
    }

    @Override
    public String toString() {
        return "ElevatorController{" +
                "elevators=" + this.elevators +
                '}';
    }
}
