// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;

import frc.robot.utils.TalonPIDConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double ROBOT_LOOP_PERIOD = 1.0 / 60.0;
    public static final int BLINKIN_LED_CONTROLLER_PORT = 0;

    public static final String RIO_CAN_BUS = "rio";
    public static final String CANIVORE_CAN_BUS = "canivore";

    public static final double DRIVE_GEAR_RATIO = 6.75;
    public static final double DRIVE_WHEEL_DIAMETER_METERS = 0.1010;

    public static final double TRACK_WIDTH = 0.5969;
    public static final double WHEEL_BASE = 0.5969;

    //Drive motor ports
    public static final int LF_DRIVE_MOTOR_PORT = 0;
    public static final int LF_ROTATE_MOTOR_PORT = 1;
    public static final int LF_ROTATE_ENCODER_PORT = 2;
    public static final int RF_DRIVE_MOTOR_PORT = 3;
    public static final int RF_ROTATE_MOTOR_PORT = 4;
    public static final int RF_ROTATE_ENCODER_PORT = 5;
    public static final int LR_DRIVE_MOTOR_PORT = 6;
    public static final int LR_ROTATE_MOTOR_PORT = 7;
    public static final int LR_ROTATE_ENCODER_PORT = 8;
    public static final int RR_DRIVE_MOTOR_PORT = 9;
    public static final int RR_ROTATE_MOTOR_PORT = 10;
    public static final int RR_ROTATE_ENCODER_PORT = 11;

    //Rotation PID values
    private static final boolean ROTATE_MOTOR_SENSOR_PHASE = false;
    private static final boolean ROTATE_MOTOR_INVERT_MOTOR = false;
    private static final double ROTATE_MOTOR_TICKS_PER_ROTATION = 4096;
    private static final double ROTATE_MOTOR_MAX_RPM = 6380;
    private static final double ROTATE_MOTOR_KP = 0.1;
    private static final double ROTATE_MOTOR_KI = 0.0;
    private static final double ROTATE_MOTOR_KD = 0.0;
    private static final double ROTATE_MOTOR_MECHANICAL_EFFICIENCY = 0.95;
    private static final double ROTATE_MOTOR_TOLERANCE = 0.1;
    private static final double ROTATE_MOTOR_LOWER_LIMIT = 0.0;
    private static final double ROTATE_MOTOR_UPPER_LIMIT = 360.0;
    private static final boolean ROTATE_MOTOR_ENABLE_SOFT_LIMITS = false;
    private static final double ROTATE_MOTOR_VELOCITY_RPM = 6380;
    private static final double ROTATE_MOTOR_ACCELERATION_RPM_PER_SEC = 12760;
    private static final int ROTATE_MOTOR_MOTION_SMOOTHING = 1;

    public static final TalonPIDConfig ROTATE_MOTOR_CONFIG = new TalonPIDConfig(ROTATE_MOTOR_SENSOR_PHASE, 
                                                                                ROTATE_MOTOR_INVERT_MOTOR, 
                                                                                ROTATE_MOTOR_TICKS_PER_ROTATION,
                                                                                ROTATE_MOTOR_MAX_RPM,
                                                                                ROTATE_MOTOR_KP,
                                                                                ROTATE_MOTOR_KI,
                                                                                ROTATE_MOTOR_KD,
                                                                                ROTATE_MOTOR_MECHANICAL_EFFICIENCY,
                                                                                ROTATE_MOTOR_TOLERANCE,
                                                                                ROTATE_MOTOR_LOWER_LIMIT,
                                                                                ROTATE_MOTOR_UPPER_LIMIT,
                                                                                ROTATE_MOTOR_ENABLE_SOFT_LIMITS,
                                                                                ROTATE_MOTOR_VELOCITY_RPM,
                                                                                ROTATE_MOTOR_ACCELERATION_RPM_PER_SEC,
                                                                                ROTATE_MOTOR_MOTION_SMOOTHING);

    //Drive constants
    public static final double DRIVE_KP = 0.1;
    public static final double DRIVE_KD = 0.0;
    public static final double DRIVE_TURNSCALAR = 70.0;
    public static final double DEADBAND = 0.0;
    public static final double DRIVE_LOOKAHEAD = 0.0;

    public static final double DRIVETRAIN_EFFICIENCY = 0.9;

    public static final double DRIVE_METERS_PER_TICK = DRIVE_WHEEL_DIAMETER_METERS * Math.PI / 2048 / DRIVE_GEAR_RATIO;
    public static final double DRIVE_MAX_LINEAR_SPEED = 6380 / 60 * 2048 * DRIVE_METERS_PER_TICK * DRIVETRAIN_EFFICIENCY;

    private static final SplineInterpolator SPLINE_INTERPOLATOR = new SplineInterpolator();

    private static final double[] DRIVE_THROTTLE_INPUT_CURVE_X = { 0.0, 0.5, 1.0 };
    private static final double[] DRIVE_THROTTLE_INPUT_CURVE_Y = { 0.0, 2.243, 4.485 };
    private static final double[] DRIVE_TRACTION_CONTROL_CURVE_X = { 0.0, 2.243, 4.485 };
    private static final double[] DRIVE_TRACTION_CONTROL_CURVE_Y = { 0.0, 0.5, 1.0 };
    private static final double[] DRIVE_TURN_INPUT_CURVE_X = { 0.0, 0.5, 1.0 };
    private static final double[] DRIVE_TURN_INPUT_CURVE_Y = { 0.0, 0.5, 1.0 };

    public static final PolynomialSplineFunction DRIVE_THROTTLE_INPUT_CURVE = SPLINE_INTERPOLATOR.interpolate(DRIVE_THROTTLE_INPUT_CURVE_X, DRIVE_THROTTLE_INPUT_CURVE_Y);
    public static final PolynomialSplineFunction DRIVE_TRACTION_CONTROL_CURVE = SPLINE_INTERPOLATOR.interpolate(DRIVE_TRACTION_CONTROL_CURVE_X, DRIVE_TRACTION_CONTROL_CURVE_Y);
    public static final PolynomialSplineFunction DRIVE_TURN_INPUT_CURVE = SPLINE_INTERPOLATOR.interpolate(DRIVE_TURN_INPUT_CURVE_X, DRIVE_TURN_INPUT_CURVE_Y);

    private static final double CURRENT_LIMIT = 100.0;
    private static final double CURRENT_THRESHOLD = 240.0;
    private static final double CURRENT_THRESHOLD_TIME = 6 * ROBOT_LOOP_PERIOD;

    public static final StatorCurrentLimitConfiguration DRIVE_CURRENT_LIMIT_CONFIGURATION = new StatorCurrentLimitConfiguration(true, CURRENT_LIMIT, CURRENT_THRESHOLD, CURRENT_THRESHOLD_TIME);

}
