package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Wire Test 2.0", group = "Testing")
public class WireTest20 extends LinearOpMode{
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor heightLeft;
    private DcMotor heightRight;
    private DcMotor clamp;
    private final double TEST_POWER = 0.5;
    private final double STOP = 0;

    public void runOpMode(){
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        heightLeft = hardwareMap.dcMotor.get("heightLeft");
        heightRight = hardwareMap.dcMotor.get("heightRight");
        clamp = hardwareMap.dcMotor.get("clamp");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.x) frontLeft.setPower(TEST_POWER);
            else frontLeft.setPower(STOP);

            if(gamepad1.a) backLeft.setPower(TEST_POWER);
            else backLeft.setPower(STOP);

            if(gamepad1.y) frontRight.setPower(TEST_POWER);
            else frontRight.setPower(STOP);

            if(gamepad1.b) backRight.setPower(TEST_POWER);
            else backRight.setPower(STOP);

            if(gamepad1.dpad_up){
                heightLeft.setPower(TEST_POWER);
                heightRight.setPower(TEST_POWER);
            }else if(gamepad1.dpad_down){
                heightLeft.setPower(-TEST_POWER);
                heightRight.setPower(-TEST_POWER);
            }else{
                heightLeft.setPower(STOP);
                heightRight.setPower(STOP);
            }

            // new clamp
            if(gamepad1.left_trigger > 0) clamp.setPower(-TEST_POWER);
            if(gamepad1.right_trigger > 0) clamp.setPower(TEST_POWER);

            if(gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0) clamp.setPower(STOP);

            // emergency stop
            if(gamepad1.back){
                frontLeft.setPower(STOP);
                backLeft.setPower(STOP);
                frontRight.setPower(STOP);
                backRight.setPower(STOP);
                heightLeft.setPower(STOP);
                heightRight.setPower(STOP);
                clamp.setPower(STOP);
            }

            idle();

        }
    }
}
