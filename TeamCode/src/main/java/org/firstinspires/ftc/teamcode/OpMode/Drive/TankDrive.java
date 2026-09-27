package org.firstinspires.ftc.teamcode.OpMode.Drive;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;

import org.firstinspires.ftc.teamcode.OpMode.Drive.misc.TankConstants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public class TankDrive extends SubsystemBase {
    // Useful variables //
    Telemetry telemetry;
    double leftSideOutput;
    double rightSideOutput;

    // Motor declaration //
    MotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    MotorGroup leftSideMotors, rightSideMotors;


    // Constructor //
    public TankDrive(HardwareMap hardwareMap, Telemetry telemetry) {
        // Assigning values to useful variables from parameters' values
        this.telemetry = telemetry;

        // Setting up motors
        frontLeftMotor = new MotorEx(hardwareMap, TankConstants.Setup.frontLeftMotorId);
        frontRightMotor = new MotorEx(hardwareMap, TankConstants.Setup.frontRightMotorId);
        backLeftMotor = new MotorEx(hardwareMap, TankConstants.Setup.backLeftMotorId);
        backRightMotor = new MotorEx(hardwareMap, TankConstants.Setup.backRightMotorId);

        // The idea behind using motor groups is to take advantage of their built-in follower
        // motor functionality, allowing me to control the whole tank easily by simply having to
        // pass values to a single object instead of multiple, reducing the error margin
        leftSideMotors = new MotorGroup(frontLeftMotor, backLeftMotor);
        rightSideMotors = new MotorGroup(frontRightMotor, backRightMotor);

        motorSetup();
    }

    @Override
    public void periodic() {
        // This method will be called every time the Scheduler is ran

        // Basically, here I am only adding telemetry to verify whether passed values are correct
        telemetry.addData("Left side motor output", leftSideOutput);
        telemetry.addData("Right side motor output", rightSideOutput);
    }

    // Functional code //
    public void drive(Supplier<Double> leftJoystick, Supplier<Double> rightJoystick) {
        /* A very basic drive method using differential drive logic
         *
         * It is easy to comprehend: both leading motors get the same translational power,
         * meaning they'll drive in the same direction, and get inverted rotational power so
         * they rotate to the same direction
         *
         * These values are gotten from the controller's left and right joysticks respectively * */

        // Getting values from passed parameters
        double translationalPower = leftJoystick.get();
        double rotationalPower = rightJoystick.get();

        // Interpreting the values
        leftSideOutput = translationalPower + rotationalPower;
        rightSideOutput = translationalPower - rotationalPower;

        // Normalizing the above values to ensure we are passing valid values to the motors
        double max = Math.max(Math.abs(leftSideOutput), Math.abs(rightSideOutput));
        if (max > 1.0) {
            leftSideOutput /= max;
            rightSideOutput /= max;
        }

        // Finally, simply passing those velocities to the motors, which will take them
        leftSideMotors.set(leftSideOutput);
        rightSideMotors.set(rightSideOutput);
    }


    // Setup Code //
    private void motorSetup() {
        // Setting motors dynamically through a motor group to make it more readable
        leftSideMotors.resetEncoder();
        rightSideMotors.resetEncoder();

        leftSideMotors.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        rightSideMotors.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

        // Execution mode using PIDs
        leftSideMotors.setRunMode(MotorEx.RunMode.VelocityControl);
        leftSideMotors.setVeloCoefficients(
                TankConstants.Control.p,
                TankConstants.Control.i,
                TankConstants.Control.d
        );

        rightSideMotors.setRunMode(MotorEx.RunMode.VelocityControl);
        rightSideMotors.setVeloCoefficients(
                TankConstants.Control.p,
                TankConstants.Control.i,
                TankConstants.Control.d
        );

        // Inverted motors
        leftSideMotors.setInverted(true);
    }
}

// Emilio Nájera — June 14th, 2025