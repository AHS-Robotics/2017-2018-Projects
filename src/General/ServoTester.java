package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public class ServoTester extends LinearOpMode {
    private Servo servo;

    public void runOpMode() {
        servo = hardwareMap.servo.get("servo");

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.left_bumper) servo.setPosition(0);
            if(gamepad1.right_bumper) servo.setPosition(1);
        }
    }
}
