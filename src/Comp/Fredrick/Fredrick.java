package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name="Fredrick v0.0.9", group="Testing")
public class Fredrick extends LinearOpMode{
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor extendLeft;
    private DcMotor extendRight;
    private DcMotor heightLeft;
    private DcMotor heightRight;
    private final double MAX = 0.75;
    private final double MIN = -1.0;
    private final double STP = 0;

    public void waitSec(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public DcMotor setMotor(String name){
        return hardwareMap.dcMotor.get(name);
    }

    public void setPower(DcMotor motor, double pow){
        motor.setPower(pow);
    }

    public void moveLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(pow);
        frontRight.setPower(pow);
        backRight.setPower(-pow);
    }

    public void moveRight(double pow){
        frontRight.setPower(-pow);
        backRight.setPower(pow);
        frontLeft.setPower(pow);
        backLeft.setPower(-pow);
    }

    public void turnRight(double pow){
        frontLeft.setPower(pow);
        frontRight.setPower(-pow);
        backRight.setPower(-pow);
        backLeft.setPower(pow);
    }

    public void turnLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(-pow);
        backRight.setPower(pow);
        frontRight.setPower(pow);
    }

    public void leftFrontDiagonal(double pow){
        backLeft.setPower(pow);
        frontRight.setPower(pow);
    }

    public void rightFrontDiagonal(double pow){
        frontLeft.setPower(pow);
        backRight.setPower(pow);
    }

    public void leftBackDiagonal(double pow){
        rightFrontDiagonal(-pow);
    }

    public void rightBackDiagonal(double pow){
        leftFrontDiagonal(-pow);
    }

    public void extendArm(double pow){
        extendLeft.setPower(pow);
        extendRight.setPower(pow);
    }
    
    public void retractArm(double pow){
        extendArm(-pow);
    }
    
    public void raiseHeight(double pow){
        heightLeft.setPower(pow);
        heightRight.setPower(pow);
    }
    
    public void lowerHeight(double pow){
        raiseHeight(-pow);
    }
    
    public void cprint(String val){
        telemetry.clear();
        telemetry.addData(">> ", val);
        telemetry.update();
    }

    public void print(String val){
        telemetry.addData(">> ", val);
        telemetry.update();
    }

    public void stopAllMotors(){
        DcMotor motors[] = {frontLeft, backLeft, frontRight, backRight, extendLeft, extendRight, heightLeft, heightRight};
        for(DcMotor motor : motors) setPower(motor, STP);
    }

    public void driveStopAll(){
        DcMotor motors[] = {frontLeft, backLeft, frontRight, backRight};
        for(DcMotor motor : motors) setPower(motor, STP);
    }

    public void driveForward(){
        DcMotor motors[] = {frontLeft, backLeft, frontRight, backRight};
        for(DcMotor motor : motors) setPower(motor, MAX);
    }

    public void driveBack(){
        DcMotor motors[] = {frontLeft, backLeft, frontRight, backRight};
        for(DcMotor motor : motors) setPower(motor, -MAX);
    }

    public void testAll(){
        setPower(frontLeft, MAX);
        waitSec(2);
        setPower(frontLeft, STP);

        setPower(frontRight, MAX);
        setPower(backLeft, MAX);
        setPower(backRight, MAX);

    }

    public void runOpMode() {
        frontLeft = setMotor("frontLeft");
        backLeft = setMotor("backLeft");
        frontRight = setMotor("frontRight");
        backRight = setMotor("backLeft");
        extendLeft = setMotor("extendLeft");
        extendRight = setMotor("extendRight");
        heightLeft = setMotor("heightLeft");
        heightRight = setMotor("heightRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        setPower(frontLeft, MAX);
        cprint("Testing frontLeft");
        waitSec(1);
        stopAllMotors();

        setPower(backLeft, MAX);
        cprint("Testing backLeft");
        waitSec(1);
        stopAllMotors();

        setPower(frontRight, MAX);
        cprint("Testing frontRight");
        waitSec(1);
        stopAllMotors();

        setPower(backRight, MAX);
        cprint("Testing backRight");
        waitSec(1);
        stopAllMotors();

        setPower(extendLeft, MAX);
        setPower(extendRight, MAX);
        cprint("Testing Both Arms");
        waitSec(1);
        stopAllMotors();

        setPower(heightLeft, MAX);
        setPower(heightRight, MAX);
        cprint("Testing height");
        waitSec(1);
        stopAllMotors();

        // Starting the TeleOp
        while(opModeIsActive()){
            /** The Driving Controls **/
            // ^ v < >
            if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x == 0) driveForward();
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x == 0) driveBack();
            
            if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y == 0) moveRight(MAX);
            else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y == 0) moveLeft(MAX);

            // turning left and right
            if(gamepad1.left_trigger > 0) turnRight(MAX);
            else if(gamepad1.right_trigger < 0) turnLeft(MAX);

            // diagonal
            if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0) rightFrontDiagonal(MAX);
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0) leftFrontDiagonal(MAX);
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0) rightBackDiagonal(MAX);
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0) leftBackDiagonal(MAX);

            // stopping all the driving controls
            if(gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0 && gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0) driveStopAll();
        
            /** The Arm Controls **/
            if(gamepad2.right_trigger > 0) extendArm(MAX);
            else if(gamepad2.left_trigger > 0) retractArm(MAX);
            
            if(gamepad2.right_bumper) raiseHeight(MAX);
            else if(gamepad2.left_bumper) lowerHeight(MAX);
            
            if(gamepad2.right_trigger == 0 && gamepad2.left_trigger == 0 && !gamepad2.right_bumper && !gamepad2.left_bumper){
                extendArm(STP);
                raiseHeight(STP);
            }
            
        }
    }

}
