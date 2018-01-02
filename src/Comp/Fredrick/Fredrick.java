package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name="Fredrick v2.1.3", group="Testing")
public class Fredrick extends LinearOpMode{
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor extendLeft;
    private DcMotor extendRight;
    private DcMotor heightLeft;
    private DcMotor heightRight;
    private double multiplier;
    private double globalPower;
    private boolean holdDownMultiChange;
    private final double MAX = 1.0; // the max speed we want robot to go
    private final double MIN = -1.0; // min speed robot can go
    private final double STP = 0; // stopping position
    private final double INC = 0.1; // how much to change the multiplier by

    /**
     * pauses the thread for seconds passed in
     * @param seconds amount of time to wait in seconds, method does math to convert seconds to milliseconds
     */
    public void waitSec(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * returns motor with reference to hardware map
     * @param name string with the name of the motor
     * @return new DcMotor with reference to hardware map
     * **/
    public DcMotor setMotor(String name){
        return hardwareMap.dcMotor.get(name);
    }

    /**
     * sets the power of a single motor
     * @param motor motor that we are setting the power for
     * @param pow the power level
     */
    public void setPower(DcMotor motor, double pow){
        motor.setPower(pow);
    }


    /**
    * sets motors to move the robot left
     * @param pow the power level
    * */
    public void moveLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(pow);
        frontRight.setPower(pow);
        backRight.setPower(-pow);
    }

    /**
     * sets motors to move the robot right
     * @param pow the power level
     */

    public void moveRight(double pow){
        frontRight.setPower(-pow);
        backRight.setPower(pow);
        frontLeft.setPower(pow);
        backLeft.setPower(-pow);
    }

    /**
     * turns the robot right
     * @param pow the power we set the motor to
     */

    public void turnRight(double pow){
        frontLeft.setPower(pow);
        frontRight.setPower(-pow);
        backRight.setPower(-pow);
        backLeft.setPower(pow);
    }

    /**
     * turns the robot left
     * @param pow the power we set the motor to
     */
    public void turnLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(-pow);
        backRight.setPower(pow);
        frontRight.setPower(pow);
    }

    /**
     * moves the robot in a left front diagonal direction
     * @param pow the power we set the motor to
     */
    public void leftFrontDiagonal(double pow){
        backLeft.setPower(pow);
        frontRight.setPower(pow);
    }

    /**
     * move the robot in a right front diagonal direction
     * @param pow the power we set the motor to
     */
    public void rightFrontDiagonal(double pow){
        frontLeft.setPower(pow);
        backRight.setPower(pow);
    }

    /**
     * move the robot in a left back diagonal direction
     * @param pow the power we set the motor to
     */
    public void leftBackDiagonal(double pow){
        rightFrontDiagonal(-pow);
    }

    /**
     * move the robot in a right back diagonal direction
     * @param pow the power we set the motor to
     */
    public void rightBackDiagonal(double pow){
        leftFrontDiagonal(-pow);
    }

    /**
     * extending the arm on the z axis
     * @param pow the power we set the motor to
     */
    public void extendArm(double pow){
        extendLeft.setPower(pow);
    }

    /**
     * retracting the arm on the z axis
     * @param pow the power we set the motor to
     */
    public void retractArm(double pow){
        extendArm(-pow);
    }

    /**
     * raising the height on the y axis
     * @param pow the power we set the motor to
     */
    public void raiseHeight(double pow){
        heightLeft.setPower(-pow);
        heightRight.setPower(pow);
    }

    /**
     * lowering the height on the z axis
     * @param pow the power we set the motor to
     */
    public void lowerHeight(double pow){
        raiseHeight(-pow);
    }

    /**
     * printing using telemetry, but clearing the screen first
     * @param val what is to be printed on the screen
     */

    public void cprint(String val){
        telemetry.clear();
        telemetry.addData(">> ", val);
        telemetry.update();
    }

    /**
     * printing using telemetry, but skipping out on clearing the screen first
     * @param val what is to be printed on the screen
     */
    public void print(String val){
        telemetry.addData(">> ", val);
        telemetry.update();
    }

    /**
     * stopping all motors in the array local to the method add all motors you want stopped into the array when needed
     */
    public void stopAllMotors(){
        DcMotor motors[] = {frontLeft, backLeft, frontRight, backRight}; //, extendLeft, extendRight, heightLeft, heightRight};
        for(DcMotor motor : motors) setPower(motor, STP);
    }

    /**
     * only stops the motors that involve driving
     */
    public void driveStopAll(){
        DcMotor motors[] = {frontLeft, backLeft, frontRight, backRight};
        for(DcMotor motor : motors) setPower(motor, STP);
    }

    /**
     * set wheels up to drive max speed forward
     */
    public void driveForward(){
        frontLeft.setPower(globalPower);
        frontRight.setPower(globalPower);
        backLeft.setPower(globalPower);
        backRight.setPower(globalPower);
    }

    public void driveForward(double pow){
        frontLeft.setPower(pow);
        frontRight.setPower(pow);
        backLeft.setPower(pow);
        backRight.setPower(pow);
    }

    /**
     * set wheels up to drive max speed backwards
     */
    public void driveBack(){
        frontLeft.setPower(-globalPower);
        frontRight.setPower(-globalPower);
        backLeft.setPower(-globalPower);
        backRight.setPower(-globalPower);
    }

    public void driveBack(double pow){
        frontLeft.setPower(-pow);
        frontRight.setPower(-pow);
        backLeft.setPower(-pow);
        backRight.setPower(-pow);
    }

    public void decSpeed(){
        if(!holdDownMultiChange){
            if(multiplier > 0) multiplier -= INC;
            holdDownMultiChange = true;
        }
    }
    public void incSpeed(){
        if(!holdDownMultiChange){
            if(multiplier < 1) multiplier += INC;
            holdDownMultiChange = true;
        }
    }

    public void runOpMode() {
        frontLeft = setMotor("frontLeft");
        backLeft = setMotor("backLeft");
        frontRight = setMotor("frontRight");
        backRight = setMotor("backRight");
        extendLeft = setMotor("extendLeft");
        extendRight = setMotor("extendRight");
        heightLeft = setMotor("heightLeft");
        heightRight = setMotor("heightRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        multiplier = 1.0;
        holdDownMultiChange = false;

        cprint("Ready to start!");

        waitForStart();

        while(opModeIsActive()){
            // debugging info
            cprint("left stick y: " + gamepad1.left_stick_y + "\nleft stick x: " + gamepad1.left_stick_x +
                    "\nleft trigger: " + gamepad1.left_trigger +
                    "\nright trigger: " + gamepad1.right_trigger +
                    "\nMAX: " + MAX +
                    "\nMultiplier: " + multiplier +
                    "\nholdDownMultiChange: " + holdDownMultiChange);
            globalPower = MAX * multiplier;

            if(gamepad1.dpad_left) decSpeed();
            if(gamepad1.dpad_right) incSpeed();
            if(!gamepad1.dpad_left && !gamepad1.dpad_right) holdDownMultiChange = false;

            /** The Driving Controls **/
            if(gamepad1.left_stick_y > 0) driveBack();
            if(gamepad1.left_stick_y < 0) driveForward();
            if(gamepad1.left_stick_x > 0) moveLeft(-globalPower);
            if(gamepad1.left_stick_x < 0) moveRight(-globalPower);
            if(gamepad1.left_stick_y > 0.85 && gamepad1.left_stick_x > 0.85) leftFrontDiagonal(-globalPower);
            if(gamepad1.left_stick_y > 0.85 && gamepad1.right_stick_x < -0.85) rightFrontDiagonal(-globalPower);
            if(gamepad1.left_stick_y < -0.85 && gamepad1.right_stick_x > 0.85) leftBackDiagonal(-globalPower);
            if(gamepad1.left_stick_y < -0.85 && gamepad1.right_stick_x < -0.85) rightBackDiagonal(-globalPower);

            /** Turning on Axis **/
            if(gamepad1.right_trigger > 0){
                turnRight(globalPower * gamepad1.right_trigger);
            }if(gamepad1.left_trigger > 0){
                turnLeft(globalPower * gamepad1.left_trigger);
            }

            /** Stopping all driving motors if there is no driving input **/
            if(gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0){
                stopAllMotors();
            }

            /** Arm Controls **/

            // up down on y axis
            if(gamepad2.dpad_up){
                heightLeft.setPower(-globalPower);
                heightRight.setPower(globalPower);
            }if(gamepad2.dpad_down){
                heightLeft.setPower(globalPower);
                heightRight.setPower(-globalPower);
            }

            if(!gamepad2.dpad_up && !gamepad2.dpad_down){
                heightLeft.setPower(STP);
                heightRight.setPower(STP);
            }

            // Forward and back on z axis
            if(gamepad2.left_trigger > 0) {
                extendLeft.setPower(globalPower);
            }if(gamepad2.right_trigger > 0){
                extendLeft.setPower(-globalPower);
            }if(gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0){
                extendLeft.setPower(STP);
            }

            if(gamepad2.left_bumper){
                extendRight.setPower(-globalPower);
            }if(gamepad2.right_bumper){
                extendRight.setPower(globalPower);
            }if(!gamepad2.left_bumper && !gamepad2.right_bumper){
                extendRight.setPower(STP);
            }

            idle();

        }
    }

}
