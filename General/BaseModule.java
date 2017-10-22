package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.LinearOpModePlus;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Base Module 1.0.0", group="Building Blocks")
public class BaseModule extends LinearOpModePlus{
    private double power = 0;
    private double lXAxis, lYAxis;
    private double rXAxis, rYAxis;

    private DcMotor motorLeftBack;
    private DcMotor motorLeftFront;
    private DcMotor motorRightBack;
    private DcMotor motorRightFront;

    public void update(){
        clear(true);
        output += "SPEED CAP: " + multiplier + "\nMLB: " + motorLeftBack + "\nMLF: " + motorLeftFront
                + "\nMRB: " + motorRightBack + "\nMRF: " + motorRightFront
                + "\nLYAXIS: " + lYAxis + "\nLXAXIS: " + lXAxis;
        refreshTelemetry();
        lYAxis = gamepad1.left_stick_y;
        lXAxis = gamepad1.left_stick_x;
    }

    @Override
    public void runOpMode(){
        // Initialization
        motorLeftBack = setMotor("motorLeftBack");
        motorLeftFront = setMotor("motorLeftFront");
        motorRightBack = setMotor("motorRightBack");
        motorRightFront = setMotor("motorRightFront");

        motorLeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            update();

            // changing some variables
            if(gamepad1.dpad_left) decPower();
            if(gamepad1.dpad_right) incPower();

            if(lYAxis <= 1.0 || lYAxis >= -1.0 && lXAxis == 0.0) {
                // moving forward or backward
                motorLeftBack.setPower(-gamepad1.left_stick_y * multiplier);
                motorLeftFront.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightBack.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightFront.setPower(-gamepad1.left_stick_y * multiplier);
            }else if(lXAxis >= -1.0 && lXAxis < 0 && lYAxis == 0){
                // moving left
                motorLeftFront.setPower(-gamepad1.left_stick_x);
                motorRightBack.setPower(-gamepad1.left_stick_x);
            }else if(lXAxis <= 1.0 && lXAxis > 0 && lYAxis == 0){
                // moving right
                motorLeftBack.setPower(-gamepad1.left_stick_x);
                motorRightFront.setPower(-gamepad1.left_stick_x);
            }else{
                // moving diagonally
                
            }

            idle();
        }

    }

}
