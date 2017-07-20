// Package goes here

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
* For a robot that contains only two motors and is only meant to drive
* or if you need to steal this for another TeleOp
* */

@TeleOp(name="Basic Drive v1.0.0", group="General")
public class BasicDrive extends LinearOpMode{
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private boolean debugInfo = false;

    public void runOpMode(){
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            motorLeft.setPower(-gamepad1.left_stick_y);
            motorRight.setPower(-gamepad1.right_stick_y);

            if(debugInfo){
                print("Left Motor Power: ", Double.toString(motorLeft.getPower()));
                print("Right Motor Power: ", Double.toString(motorRight.getPower()));
            }

            idle();
        }
    }

    public void print(String caption, String message){
        telemetry.addData(caption, message);
        telemetry.update();
    }
}
