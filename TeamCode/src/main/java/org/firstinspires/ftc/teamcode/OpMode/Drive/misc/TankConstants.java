package org.firstinspires.ftc.teamcode.OpMode.Drive.misc;

public class TankConstants {
    // Setup variables, such as IDs
    public static final class Setup {
        public static final String frontLeftMotorId = "tankFrontLeftMotor";
        public static final String frontRightMotorId = "tankFrontRightMotor";
        public static final String backLeftMotorId = "tankBackLeftMotor";
        public static final String backRightMotorId = "tankBackRightMotor";
    }

    // Variables related to the tank drive's control mode, like PID coefficients
    public static final class Control {
        public static final double p = 1.0;
        public static final double i = 0.0;
        public static final double d = 0.0;
    }
}

// Emilio Nájera — June 14th, 2025