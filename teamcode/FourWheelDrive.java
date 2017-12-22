package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Four Wheel Drive 0.0.5b", group = "Trash")
public class FourWheelDrive extends LinearOpMode{
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private double multiplier = 1.0;
    private double lXAxis;
    private double lYAxis;
    private static final double INCREMENTER = 0.1; // how much we'll change the power by
    private static final double MAX_POWER = 1.0; // fixing magic number
    private static final double MIN_POWER = -1.0;

    private void update(){
        lXAxis = gamepad1.left_stick_x;
        lYAxis = gamepad1.left_stick_y;

        telemetry.addData("> ", lXAxis + " X Axis\n> " + lYAxis + " Y Axis");
        telemetry.addData("> ", "Multiplier: " + multiplier);
        telemetry.addData("> ", "Motor Front Left: " + motorFrontLeft.getPower());
        telemetry.addData("> ", "Motor Back Left: " + motorBackLeft.getPower());
        telemetry.addData("> ", "Motor Front Right: " + motorFrontLeft.getPower());
        telemetry.addData("> ", "Motor Back Right: " + motorBackRight.getPower());

        telemetry.update();
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

                //the next two  elses can just be combined w/o else
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
            motorBackLeft.setPower(-power);
            motorFrontLeft.setPower(-power);
            motorBackRight.setPower(power);
            motorFrontRight.setPower(power);
        }else if(dir == 'r'){
            motorBackLeft.setPower(power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(-power);
            motorFrontRight.setPower(-power);
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
            update();
            // just to test wheel
            if(gamepad1.b){
                try{
                    motorBackRight.setPower(MAX_POWER);
                    Thread.sleep(2000);
                    motorBackRight.setPower(0);
                }catch(InterruptedException e){
                    telemetry.addData("> ", "Error in running wheel");
                }
            }

            if(gamepad1.dpad_left){
                changePower('d');
            }

            if(gamepad1.dpad_right){
                changePower('u');
            }

            if(gamepad1.right_trigger > 0.0){
                turn('r');
            }else if(gamepad1.left_trigger > 0.0) {
                turn('l');
            }
            // Driving ^ v < >
            if(lYAxis > 0 && lXAxis == 0){
                /* FORWARD */
                double power = MAX_POWER * multiplier;
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);

            }else if(lYAxis < 0 && lXAxis == 0){
                /* BACKWARDS */
                double power = MIN_POWER * multiplier;
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);

            }else if(lXAxis < 0 && lYAxis == 0){
                /* LEFT */
                double power = MAX_POWER * multiplier;
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(-power);
                motorBackRight.setPower(-power);
                motorFrontRight.setPower(power);
            }else if(lXAxis > 0 && lYAxis == 0){
                /* RIGHT */
                double power = MAX_POWER * multiplier;
                motorBackLeft.setPower(-power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(-power);
                motorFrontRight.setPower(power);
            }else if(lXAxis < 0 && lYAxis > 0){
				/* LEFT FRONT DIAGONAL */
                double power = MAX_POWER * multiplier;
                motorBackLeft.setPower(power);
                motorFrontRight.setPower(power);
            }else if(lXAxis < 0 && lYAxis < 0){
				/* LEFT BACK DIAGONAL */
                double power = MAX_POWER * multiplier;
                motorFrontLeft.setPower(-power);
                motorBackRight.setPower(-power);
            }else if(lXAxis > 0 && lYAxis > 0){
				/* RIGHT FRONT DIAGONAL */
                double power = MAX_POWER * multiplier;
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
            }else if(lXAxis < 0 && lYAxis < 0){
				/* RIGHT BACK DIAGONAL */
                double power = MAX_POWER * multiplier;
                motorFrontRight.setPower(-power);
                motorBackLeft.setPower(-power);
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