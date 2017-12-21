package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Jeff Design New Drive", group = "Trash")
public class ThreeMotorArtifactGrabberNew extends LinearOpMode{
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private DcMotor motorExtendLeft;
    private DcMotor motorExtendRight;
    private DcMotor motorExtend;
    private double multiplier = 1.0;
    private static final double INCREMENTER = 0.1; // how much we'll change the power by
    private static final double MAX_POWER = 1.0; // fixing magic number
    private static final double MIN_POWER = -1.0;

    /**
     *sets the motors in an array to the same power
     *@param motorGroup array of motors that we are changing
     *@param power the power we are setting them to
     */
    private void setMotorGroup(DcMotor[] motorGroup, double power){
        for(DcMotor motor : motorGroup){
            motor.setPower(power);
        }
    }

    /**
     * changes the multiplier levels for the power
     * @param direction tells the method which direction we need to move the motor
     */
    private void changePower(char direction){
        if(multiplier >= -1.0 && multiplier <= 1.0){
            if(direction == 'u' || direction == 'U'){
                multiplier += INCREMENTER;
                return;
            }else if(direction == 'd' || direction == 'D'){
                multiplier -= INCREMENTER;
                return;
            }else{
                telemetry.addData("> ", " Error: the direction " + direction + " is unknown.");
                telemetry.update();
            }
        }else{
            telemetry.addData("> ", "  Error: the power is already at its max or min power -1.0 to 1.0");
            telemetry.update();
        }
    }

    /**
     * sets the motors back to their default wheel position
     */
    private void setDefaultWheelDirection(){
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * reverse the direction of the motor passed in and returns new instance with that direction
     * @param target the motor we are reversing
     */
    private DcMotor reverseMotorDirection(DcMotor target){
        target.setDirection((target.getDirection() == DcMotorSimple.Direction.FORWARD) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        return target;
    }

    /**
     * reverse the direction of all the wheels
     */
    private void reverseWheelDirection(){
        DcMotorSimple.Direction leftDirection = (motorFrontLeft.getDirection() == DcMotorSimple.Direction.FORWARD) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
        DcMotorSimple.Direction rightDirection = (motorFrontRight.getDirection() == DcMotorSimple.Direction.REVERSE) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;

        motorFrontLeft.setDirection(leftDirection);
        motorBackLeft.setDirection(leftDirection);
        motorFrontRight.setDirection(rightDirection);
        motorBackRight.setDirection(rightDirection);
    }

    /**
     * turns the robot in circles
     * @param dir the direction that we are turning the robot
     */

    public void turn(char dir){
        setDefaultWheelDirection();
        double power = MAX_POWER * multiplier;
        if(dir == 'l'){
            motorFrontLeft = reverseMotorDirection(motorFrontLeft);
            motorBackLeft = reverseMotorDirection(motorBackLeft);

            motorBackLeft.setPower(power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(power);
            motorFrontRight.setPower(power);
        }else if(dir == 'r'){
            motorFrontRight = reverseMotorDirection(motorFrontRight);
            motorBackRight = reverseMotorDirection(motorBackRight);
            idle();
            motorBackLeft.setPower(power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(power);
            motorFrontRight.setPower(power);
        }else{
            telemetry.addData("> ", "Error: Unknown direction " + dir);
            telemetry.update();
        }
    }

    /**
     * the "main method" of the opmode
     */

    public void runOpMode(){
        // initialization
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorExtendLeft = hardwareMap.dcMotor.get("motorExtendLeft");
        motorExtendRight = hardwareMap.dcMotor.get("motorExtendRight");
        motorExtend = hardwareMap.dcMotor.get("motorExtend");

        /*
        * The following should be true
        * since the wheels spin clock wise, the right wheels should be
        * reversed
        */
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                reverseWheelDirection();
            }

            if(gamepad1.dpad_left){
                changePower('d');
            }

            if(gamepad1.dpad_right){
                changePower('u');
            }

            if(gamepad1.left_trigger > 0.0){
                turn('r');
            }else if(gamepad1.left_trigger < 0.0){
                turn('l');
            }

            // moving the arm up and down
            if(gamepad1.left_bumper){
                motorExtendLeft.setPower(MIN_POWER * multiplier);
                motorExtendRight.setPower(MIN_POWER * multiplier);
            }else if(gamepad1.right_bumper){
                motorExtendLeft.setPower(MAX_POWER * multiplier);
                motorExtendRight.setPower(MAX_POWER * multiplier);
            }else if(!gamepad1.left_bumper && !gamepad1.right_bumper){
                motorExtendLeft.setPower(0);
                motorExtendRight.setPower(0);
            }

            // extending the arm
            if(gamepad1.x){
                motorExtend.setPower(MIN_POWER * multiplier);
            }else if(gamepad1.b){
                motorExtend.setPower(MAX_POWER * multiplier);
            }


            // Driving ^ v < >
            if((gamepad1.left_stick_y > 0 && gamepad1.left_stick_y <= 1.0) && (gamepad1.left_stick_x >= 0.0 && gamepad1.left_stick_x <= 0.2)){
                /* FORWARD */
                double power = MAX_POWER * multiplier;
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);

            }else if((gamepad1.left_stick_y < 0 && gamepad1.left_stick_y >= -1.0) && (gamepad1.left_stick_x <= 0.0 && gamepad1.left_stick_x >= -0.2)){
                /* BACKWARDS */
                double power = MIN_POWER * multiplier;
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);

            }else if((gamepad1.left_stick_x < 0 && gamepad1.left_stick_x >= -1.0) && (gamepad1.left_stick_y <= 0.0 && gamepad1.left_stick_y >= -0.2)){
                /* LEFT */
                double power = MAX_POWER * multiplier;
                motorFrontLeft = reverseMotorDirection(motorFrontLeft);
                motorBackRight = reverseMotorDirection(motorBackRight);

                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);
            }else if((gamepad1.left_stick_x > 0 && gamepad1.left_stick_x <= 1.0) && (gamepad1.left_stick_y >= 0.0 && gamepad1.left_stick_y <= 0.2)){
                /* RIGHT */
                double power = MAX_POWER * multiplier;
                motorBackLeft = reverseMotorDirection(motorBackLeft);
                motorFrontRight = reverseMotorDirection(motorFrontRight);

                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);
            }else{
                double power = 0;
                setDefaultWheelDirection();
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);
            }

            // stopping all motors if no buttons are being pressed
            if(gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0 && gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0) {
                double power = 0;
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);
                setDefaultWheelDirection();

            }

            idle();
        }
    }
}