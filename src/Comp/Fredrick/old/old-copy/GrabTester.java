package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
* This program test one part of a design, the mechanism used to grab
* blocks.
* */

@TeleOp(name="Grab Mechanism Tester", group="Hardware Tester")
public class GrabTester extends LinearOpMode{
    private Servo leftTop;
    private Servo leftBottom;
    private Servo rightTop;
    private Servo rightBottom;

    public void runOpMode(){
        leftTop = hardwareMap.servo.get("leftTop");
        leftBottom = hardwareMap.servo.get("leftBottom");
        rightTop = hardwareMap.servo.get("rightTop");
        rightBottom = hardwareMap.servo.get("rightBottom");

        leftTop.setDirection(Servo.Direction.REVERSE);
        rightTop.setDirection(Servo.Direction.FORWARD);
        rightBottom.setDirection(Servo.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.y) return;

            if(gamepad1.a){
                leftTop.setPosition(1.0);
                leftBottom.setPosition(1.0);
                rightTop.setPosition(1.0);
                rightBottom.setPosition(1.0);
            }else if(gamepad1.b){
                leftTop.setPosition(0);
                leftBottom.setPosition(0);
                rightTop.setPosition(0);
                rightBottom.setPosition(0);
            }

            idle();
        }
    }
}
