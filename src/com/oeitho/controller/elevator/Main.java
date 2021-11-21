package com.oeitho.controller.elevator;

public class Main {

    public static void main(String[] args) {
        ElevatorController elevatorController = new ElevatorController();
        elevatorController.addElevator(new Elevator());
        elevatorController.summon(2);
        elevatorController.travel();
        elevatorController.travel();
        elevatorController.summon(4);
        elevatorController.summon(1);
        elevatorController.summon(3);
        elevatorController.summon(0);
        elevatorController.travel();
        elevatorController.travel();
        elevatorController.travel();
        elevatorController.travel();
        elevatorController.emergencyStop();
    }

}
