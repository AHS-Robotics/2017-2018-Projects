package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.LinearOpModePlus;

enum Diagonal{
    LEFT_FWD, LEFT_BCK, RIGHT_FWD, RIGHT_BCK, STOP
    // if stop is used that means all motor powers should be set to 0
}

@TeleOp(name="Base Module 2.4.b0", group="Building Block")
public class BaseModule extends LinearOpModePlus {
    private DcMotor motorLeftFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightFront;
    private DcMotor motorRightBack;
    private DcMotor motorLeftShaft;
    private DcMotor motorRightShaft;

    private String status; // tells user what the roboto is doing

    private double multiplierStorage = 0;
    private float lYAxis = 0, lXAxis = 0;

    private boolean leftRev = true; // if the value changes during updates different wheels will be reversed
    private boolean suspendAllMotors = false;


    /**
     * At the start of every run, variable information will be updated
     */
    public void update() {
        lYAxis = gamepad1.left_stick_y;
        lXAxis = gamepad1.left_stick_x;

        if (leftRev) {
            motorLeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
            motorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            motorRightBack.setDirection(DcMotorSimple.Direction.FORWARD);
            motorRightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            motorLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
            motorLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
            motorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);
            motorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        // This is experimental so tests are needed
        if (suspendAllMotors && multiplierStorage <= 0) {
            multiplierStorage = multiplier;
            multiplier = 0;
        } else if (!suspendAllMotors && multiplierStorage > 0) {
            multiplier = multiplierStorage;
            multiplierStorage = 0;
        }

        // Print status
        telemetry.addData("> ", "Status: " + status);
        telemetry.addData("> ", "Multiplier: " + multiplier);
        telemetry.addData("> ", "Power Cap: " + multiplier * 1.0);

        // Print motor data
        telemetry.addData(">", "motorLeftFront: " + motorLeftFront.getPower() + "\t" + ((motorLeftFront.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse"));
        telemetry.addData(">", "motorLeftBack: " + motorLeftBack.getPower() + "\t" + ((motorLeftBack.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse"));
        telemetry.addData(">", "motorRightFront: " + motorRightFront.getPower() + "\t" + ((motorRightFront.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse"));
        telemetry.addData(">", "motorRightBack: " + motorRightBack.getPower() + "\t" + ((motorRightBack.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse"));
        telemetry.addData(">", "motorLeftShaft: " + motorLeftShaft.getPower() + "\t" + ((motorLeftShaft.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse"));
        telemetry.addData(">", "motorRightShaft: " + motorRightShaft.getPower() + "\t" + ((motorRightShaft.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse"));

        telemetry.update();
    }

    private void turn(char dir) {
        status = "Turning " + ((dir == 'L') ? "Left" : (dir == 'R') ? "Right" : "Unknown");
        dir = Character.toUpperCase(dir);
        DcMotor motorsWithChange[] = new DcMotor[2]; // this are the ones we'll have to reverse
        DcMotorSimple.Direction rev, fwd;

        if (dir == 'L') {
            motorsWithChange[0] = motorLeftBack;
            motorsWithChange[1] = motorLeftFront;

            // if left turning is a little funky play around with these directions
            rev = (leftRev) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
            fwd = (leftRev) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;
        } else if (dir == 'R') {
            motorsWithChange[0] = motorRightBack;
            motorsWithChange[1] = motorLeftFront;
            rev = (leftRev) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
            fwd = (leftRev) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;
        } else{
            return;
        }

        for (DcMotor m : motorsWithChange) m.setDirection(rev); // setting the reverse directions of motors
        motorLeftFront.setDirection((motorLeftFront.getDirection() == DcMotorSimple.Direction.REVERSE) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);

        while (((dir == 'L' && dir != 'R') ? gamepad1.left_trigger : gamepad1.right_trigger) > 0) {
            motorLeftFront.setPower(MAX_CAP * multiplier);
            motorLeftBack.setPower(MAX_CAP * multiplier);
            motorRightFront.setPower(MAX_CAP * multiplier);
            motorRightBack.setPower(MAX_CAP * multiplier);
        }

        for (DcMotor m : motorsWithChange) m.setDirection(fwd); // setting directions back to normal and we should be good

    }

    public void moveForward(double power) {
        status = "Moving forward";
        DcMotor motors[] = {motorLeftFront, motorLeftBack, motorRightFront, motorRightBack};
        DcMotorSimple.Direction leftDir = (leftRev) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
        DcMotorSimple.Direction rightDir = (leftRev) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;

        motorLeftFront.setDirection(leftDir);
        motorLeftBack.setDirection(leftDir);
        motorRightFront.setDirection(rightDir);
        motorRightBack.setDirection(rightDir);

        for (DcMotor m : motors) m.setPower(power * multiplier);
    }

    public void moveBackward(double power) {
        status = "Moving backwards";
        moveForward(power);
    }

    public void moveLeft(double power) {
        status = "Moving left";
        motorLeftBack.setDirection((motorLeftBack.getDirection() == DcMotorSimple.Direction.REVERSE) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motorLeftFront.setPower(power);
        motorLeftBack.setPower(power);

        motorRightBack.setDirection((motorRightBack.getDirection() == DcMotorSimple.Direction.REVERSE) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motorRightFront.setPower(power);
        motorRightBack.setPower(power);
    }

    public void moveRight(double power) {
        status = "Moving right";
        motorLeftFront.setPower(power);
        motorLeftBack.setPower(power);
        motorLeftFront.setDirection((motorLeftFront.getDirection() == DcMotorSimple.Direction.REVERSE) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);


        motorRightFront.setDirection((motorRightFront.getDirection() == DcMotor.Direction.FORWARD) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.REVERSE);
        motorRightFront.setPower(power);
        motorRightBack.setPower(power);
    }

    public void diagonal(Diagonal d, double power){
        status = "Moving diagonal";
        switch(d){
            case LEFT_BCK:
                motorLeftFront.setPower(power);
                motorRightBack.setPower(power);
                break;
            case LEFT_FWD:
                motorLeftBack.setPower(power);
                motorRightFront.setPower(power);
                break;
            case RIGHT_BCK:
                motorRightFront.setPower(power);
                motorLeftBack.setPower(power);
                break;
            case RIGHT_FWD:
                motorLeftFront.setPower(power);
                motorRightBack.setPower(power);
                break;
            default:
                moveForward(0);
                break;
        }
    }

    public void moveShaft(char dir){
        status = "Moving shaft " + ((dir == 'U') ? "Up" : (dir == 'D') ? "Down": "Unknown");
        if(dir == 'S'){
            motorLeftShaft.setPower(0);
            motorRightShaft.setPower(0);
        }else if(dir == 'U'){
            motorLeftShaft.setPower(MAX_CAP * multiplier);
            motorRightShaft.setPower(MAX_CAP * multiplier);
        }else if(dir == 'D'){
            motorLeftShaft.setPower((MAX_CAP * multiplier) * -1.0);
            motorRightShaft.setPower((MAX_CAP * multiplier) * -1.0);
        }else{
            return;
        }
    }

    @Override
    public void runOpMode(){
        status = "Initializing";
        motorLeftBack = setMotor("motorLeftBack");
        motorLeftFront = setMotor("motorLeftFront");
        motorRightBack = setMotor("motorRightBack");
        motorRightFront = setMotor("motorRightFront");
        motorLeftShaft = setMotor("motorLeftShaft");
        motorRightShaft = setMotor("motorRightShaft");

        motorRightShaft.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("> ", "Ready to go!");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()){
            update(); // keeping data up to data
            // here are some control options by reversing booleans
            if(gamepad1.a) leftRev = (leftRev) ? false:true;
            if(gamepad1.back) suspendAllMotors = (suspendAllMotors) ? false:true;

            if(gamepad1.left_trigger > 0) turn('L');
            else if(gamepad1.right_trigger > 0) turn('R');
            else{
                if(lYAxis > 0 && lXAxis == 0) moveForward(lYAxis * multiplier);
                else if(lYAxis < 0 && lXAxis == 0) moveBackward(lYAxis * multiplier);
                else if(lYAxis > 0 && lXAxis < 0) diagonal(Diagonal.LEFT_FWD, MAX_CAP * multiplier);
                else if(lYAxis > 0 && lXAxis > 0) diagonal(Diagonal.RIGHT_FWD, MAX_CAP * multiplier);
                else if(lYAxis < 0 && lXAxis < 0) diagonal(Diagonal.LEFT_BCK, MAX_CAP * multiplier);
                else if(lYAxis < 0 && lXAxis > 0) diagonal(Diagonal.RIGHT_BCK, MAX_CAP * multiplier);
                else if(lXAxis < 0 && lYAxis == 0) moveLeft(MAX_CAP * multiplier);
                else if(lXAxis > 0 && lYAxis == 0) moveRight(MAX_CAP * multiplier);
                else{
                    status = "Idle";
                    moveForward(0);
                }

            }

            if(gamepad2.dpad_up) moveShaft('U');
            else if(gamepad2.dpad_down) moveShaft('D');
            else if(!gamepad2.dpad_down || !gamepad2.dpad_up) moveShaft('S');

            if(gamepad1.dpad_left)decPower();
            if(gamepad1.dpad_right)incPower();

            if(!gamepad2.dpad_up && !gamepad2.dpad_up && gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0 && gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0){
                motorLeftFront.setPower(0);
                motorLeftBack.setPower(0);
                motorRightFront.setPower(0);
                motorRightBack.setPower(0);
                motorLeftShaft.setPower(0);
                motorRightShaft.setPower(0);
            }
            idle();
        }
    }
}

/* *
* Change Log
*
* - on line 99 added a line that is suppose to fix one of the problems that we have with turning
* as of now that part of the program is untested
*
* - in the moveLeft and moveRight methods, we changed all wheels to move
*
* - on lines 233 and 234 added in conditional that will check if driver pressed dpad left or
* right and than increment and decrement based on what is pressed
*   - left -> decPower()
*   - right -> incPower()
*/
