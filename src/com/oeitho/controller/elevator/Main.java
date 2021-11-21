package com.oeitho.controller.elevator;

public class Main {

    public static void main(String[] args) {
        ElevatorController elevatorController = new ElevatorController();
        elevatorController.addElevator(new Elevator());
        elevatorController.summon(2);
    }

}
