package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name="Fredrick v0.4.2", group="Testing")
public class Fredrick extends LinearOpMode{
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor extendLeft;
    private DcMotor extendRight;
    private DcMotor heightLeft;
    private DcMotor heightRight;
    private final double MAX = 1.0/4.0;
    private final double MIN = -1.0;
    private final double STP = 0;

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
        frontLeft.setPower(MAX);
        frontRight.setPower(MAX);
        backLeft.setPower(MAX);
        backRight.setPower(MAX);
    }

    /**
     * set wheels up to drive max speed backwards
     */
    public void driveBack(){
        frontLeft.setPower(-MAX);
        frontRight.setPower(-MAX);
        backLeft.setPower(-MAX);
        backRight.setPower(-MAX);
    }

    public void runOpMode() {
        frontLeft = setMotor("frontLeft");
        backLeft = setMotor("backLeft");
        frontRight = setMotor("frontRight");
        backRight = setMotor("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        cprint("Ready to start!");

        waitForStart();

        while(opModeIsActive()){
            // debugging info
            cprint("left stick y: " + gamepad1.left_stick_y + "\nleft stick x: " + gamepad1.left_stick_x + "\nleft trigger: " + gamepad1.left_trigger + "\nright trigger: " + gamepad1.right_trigger + "\nMAX: " + MAX);

            /** The Driving Controls **/
            if(gamepad1.left_stick_y > 0) driveBack();
            if(gamepad1.left_stick_y < 0) driveForward();
            if(gamepad1.left_stick_x > 0) moveLeft(-MAX);
            if(gamepad1.left_stick_x < 0) moveRight(-MAX);
            if(gamepad1.left_stick_y > 0.85 && gamepad1.left_stick_x > 0.85) leftFrontDiagonal(-MAX);
            if(gamepad1.left_stick_y > 0.85 && gamepad1.right_stick_x < -0.85) rightFrontDiagonal(-MAX);
            if(gamepad1.left_stick_y < -0.85 && gamepad1.right_stick_x > 0.85) leftBackDiagonal(-MAX);
            if(gamepad1.left_stick_y < -0.85 && gamepad1.right_stick_x < -0.85) rightBackDiagonal(-MAX);

            /** Turning on Axis **/
            if(gamepad1.right_trigger > 0){
                turnRight(MAX * gamepad1.right_trigger);
            }if(gamepad1.left_trigger > 0){
                turnLeft(MAX * gamepad1.left_trigger);
            }

            /** Stopping all driving motors if there is no driving input **/
            if(gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0) stopAllMotors();
            idle();

        }
    }

}
