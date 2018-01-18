package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public class DcMotorTester extends LinearOpMode {
    private DcMotor motor;

    public void runOpMode() {
        motor = hardwareMap.dcMotor.get("motor");

        waitForStart();

        while(opModeIsActive()){
            motor.setPower(-gamepad1.left_stick_y);
            idle();
        }
    }
}
