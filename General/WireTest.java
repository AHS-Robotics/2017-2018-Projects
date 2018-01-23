package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Wire Test v0.0.1", group = "Testing")
public class WireTest extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor heightRight;
    private DcMotor heightLeft;
    private DcMotor extendLeft;
    private DcMotor extendRight;
    private Servo claw;

    public void runOpMode(){
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        heightLeft = hardwareMap.dcMotor.get("heightLeft");
        heightRight = hardwareMap.dcMotor.get("heightRight");
        extendLeft = hardwareMap.dcMotor.get("extendLeft");
        extendRight = hardwareMap.dcMotor.get("extendRight");
        claw = hardwareMap.servo.get("claw"); // port 0;

        while(opModeIsActive()){
            if(gamepad1.x) frontLeft.setPower(1.0);
            if(!gamepad1.x) frontLeft.setPower(0);
            if(gamepad1.a) backLeft.setPower(1.0);
            if(!gamepad1.a) backLeft.setPower(0);
            if(gamepad1.b) backRight.setPower(1.0);
            if(!gamepad1.b) backRight.setPower(0);
            if(gamepad1.y) frontRight.setPower(1.0);
            if(!gamepad1.y) frontRight.setPower(0);
            if(gamepad1.dpad_up) heightLeft.setPower(1.0);
            if(!gamepad1.dpad_up) heightLeft.setPower(0);
            if(gamepad1.dpad_right) heightRight.setPower(1.0);
            if(!gamepad1.dpad_right) heightRight.setPower(0);
            if(gamepad1.dpad_down) extendLeft.setPower(1.0);
            if(!gamepad1.dpad_down) extendLeft.setPower(0);
            if(gamepad1.dpad_left) extendRight.setPower(1.0);
            if(!gamepad1.dpad_left) extendRight.setPower(0);
            if(gamepad1.start) claw.setPosition(0);
            if(gamepad1.back) claw.setPosition(1);
        }
    }

}
