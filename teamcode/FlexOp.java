package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
 * the idea of this is popping and appending DcMotors when needed
 * hopefully this makes the process simple when changes are made
 * to the bot
 *
 * NOTE: ALL HARDWARE CLASSES EXTEND HardwareDevice
 */
public class FlexOp extends LinearOpMode{
    private DcMotor allMotors[];
    private Servo allServos[];
    private ColorSensor allColorSensors[];
    private AccelerationSensor allAccelerationSensor[];
    private CompassSensor allCompassSensor[];
    private Gyroscope allGyroscope[];
    private GyroSensor allGyroSensor[];
    private TouchSensor allTouchSensor[];


    public void runOpMode(){

    }
}
