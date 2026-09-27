package org.firstinspires.ftc.teamcode.OpMode.Drive.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.OpMode.Drive.TankDrive;

import java.util.function.Supplier;

public class DriveCommand extends CommandBase {
    // Declaring useful variables
    TankDrive tankDrive;
    Supplier<Double> leftJoystick;
    Supplier<Double> rightJoystick;

    // Constructor //
    public DriveCommand(TankDrive tankDrive, Supplier<Double> leftJoystick,
                        Supplier<Double> rightJoystick) {
        // Assigning values to class variables
        this.tankDrive = tankDrive;
        this.leftJoystick = leftJoystick;
        this.rightJoystick = rightJoystick;

        // addRequirements() tells the Scheduler that the Tank Subsystem is currently being used
        addRequirements(this.tankDrive);
    }

    // Executing the drive function declared inside the Tank subsystem
    @Override
    public void execute() {
        tankDrive.drive(leftJoystick, rightJoystick);
    }
}

// Emilio Nájera — June 14th, 2025