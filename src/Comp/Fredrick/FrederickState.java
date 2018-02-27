package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "Frederick State 0.0.1", group = "State")
public class FrederickState extends LinearOpMode{
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor heightLeft;
    private DcMotor heightRight;
    private Servo leftTop;
    private Servo leftBottom;
    private Servo rightTop;
    private Servo rightBottom;
    private ColorSensor sensor;
    private double servoPosition;
    private double multiplier;
    private double globalPower;
    private boolean holdDownMultiChange;
    private boolean holdDownGrab;
    private boolean isAuto;
    private final double MAX = 1.0;
    private final double MIN = -1.0;
    private final double STP = 0;
    private final double INC = 0.1;
    private final double SERVO_INC = 0.1;

    /**
     * returns an RED, GREEN, or BLUE depending on the main value the color sensor sees
     * @return string value of the color holding the majority
     */
    private String getColorFromSensor(){
        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();

        if(red > green && red > blue) return "RED";
        else if(green > blue) return "GREEN";
        else return "BLUE";
    }

    /**
     * pauses the programs thread for the amount of seconds passed in
     * @param seconds amount of seconds the program needs to wait
     */
    private void waitSec(int seconds){
        try{
            Thread.sleep(seconds* 1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    // Following two methods are short cuts for setting hardware;
    private Servo setServo(String name){return hardwareMap.servo.get(name);}
    private DcMotor setMotor(String name){return hardwareMap.dcMotor.get(name);}
    private ColorSensor setSensor(String name){return hardwareMap.colorSensor.get(name);}
    private void setPower(DcMotor motor, double pow){motor.setPower(pow);}

    /*
    * NOTE
    *   The following methods are ONLY for the 45 degree omniwheels
    *   Mecanum wheels will not work the same
    * */

    /**
     * sets robot with omniwheels to move left
     * @param pow the power level
     */
    private void moveLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(pow);
        frontRight.setPower(-pow);
        backRight.setPower(pow);
    }

    /**
     * sets robot with omniwheels to move right
     * @param pow the power level
     */
    private void moveRight(double pow){
        frontLeft.setPower(pow);
        backLeft.setPower(-pow);
        frontRight.setPower(pow);
        backRight.setPower(-pow);
    }

    /**
     * turns robot with omniwheels left
     * @param pow the power level
     */
    private void turnLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(-pow);
        frontRight.setPower(pow);
        backRight.setPower(pow);
    }

    /**
     * turns robot with omniwheels right
     * @param pow the power level
     */
    private void turnRight(double pow){
        frontLeft.setPower(pow);
        backLeft.setPower(pow);
        frontRight.setPower(-pow);
        backRight.setPower(-pow);
    }

    /**
     * raising the height on the y axis
     * @param pow the power level
     */
    private void raiseHeight(double pow){
        heightLeft.setPower(-pow);
        heightRight.setPower(pow);
    }

    /**
     * lowering the height on the y axis
     * @param pow the power level
     */
    private void lowerHeight(double pow){raiseHeight(-pow);}

    /**
     * printing using telemetry, but clearing the screen first
     * @param val what is to be printed on the screen
     */
    private void cprint(String val){
        telemetry.clear();
        telemetry.addData(">> ", val);
        telemetry.update();
    }

    /**
     * printing using telemetry, but skipping out on clearing the first screen
     * @param val what is to printed on the screen
     */
    private void print(String val){
        telemetry.addData(">> ", val);
        telemetry.update();
    }

    /**
     * set up the wheels to drive forward
     */
    private void driveForward(){
        frontLeft.setPower(-globalPower);
        backLeft.setPower(-globalPower);
        frontRight.setPower(-globalPower);
        backRight.setPower(-globalPower);
    }

    /**
     * set up wheels to drive
     * @param pow the power we want to set the robot to
     */
    private void driveForward(double pow){
        frontLeft.setPower(pow);
        backLeft.setPower(pow);
        frontRight.setPower(pow);
        backRight.setPower(pow);
    }

    /**
     * set the wheels up to drive max speed backwards
     */
    private void driveBack(){
        frontLeft.setPower(globalPower);
        backLeft.setPower(globalPower);
        frontRight.setPower(globalPower);
        backRight.setPower(globalPower);
    }

    /**
     * set up wheels to drive
     * @param pow the power we want to set the robot to
     */
    private void driveBack(double pow){
        driveForward(-pow);
    }

    /**
     * decreases a multiplier speed
     * @param tempMult multiplier we want to decrease
     * @return the new decreased value of the multiplier
     */
    private double decSpeed(double tempMult){
        if(!holdDownMultiChange){
            if(tempMult > 0) tempMult -= INC;
            holdDownMultiChange = true;
        }

        return tempMult;
    }

    /**
     * increases a multiplier speed
     * @param tempMult multiplier we want to increase
     * @return the new increased value of the multiplier
     */
    private double incSpeed(double tempMult){
        if(!holdDownMultiChange){
            if(tempMult < 1) tempMult += INC;
            holdDownMultiChange = true;
        }

        return tempMult;
    }

    /**
     * sets all servos to the global current servo position
     */
    private void setGrab(){
        leftTop.setPosition(servoPosition);
        leftBottom.setPosition(servoPosition);
        rightTop.setPosition(servoPosition);
        rightBottom.setPosition(servoPosition);
    }

    /**
     * main method of the TeleOp
     */
    public void runOpMode(){
        if(isAuto){
            runAutoMode();
            return;
        }

        // initializing all hardware
        frontLeft = setMotor("frontLeft");
        backLeft = setMotor("backLeft");
        frontRight = setMotor("frontRight");
        backRight = setMotor("backRight");
        heightLeft = setMotor("heightLeft");
        heightRight = setMotor("heightRight");
        leftTop = setServo("leftTop");
        leftBottom = setServo("leftBottom");
        rightTop = setServo("rightTop");
        rightBottom = setServo("rightBottom");
        sensor = setSensor("sensor");

        // setting directions of hardware so everything works
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        leftTop.setDirection(Servo.Direction.REVERSE);
        rightTop.setDirection(Servo.Direction.FORWARD);
        rightBottom.setDirection(Servo.Direction.REVERSE);

        multiplier = 1.0;
        holdDownMultiChange = false;
        holdDownGrab = false;
        servoPosition = 0.5;
        globalPower = 0;

        setGrab();

        cprint("Ready to start!");

        waitForStart();

        while(opModeIsActive()){
            // debugging info
            cprint("left stick y: " + gamepad1.left_stick_y +
            "\nright stick x: " + gamepad1.left_stick_x +
            "\nleft trigger: " + gamepad1.left_trigger +
            "\nright trigger: " + gamepad1.right_trigger +
            "\nMAX: " + MAX +
            "\nmultiplier: " + multiplier +
            "\nservo position: " + servoPosition +
            "\nholdDownMultiChange: " + holdDownMultiChange +
            "\nholdDownGrab: " + holdDownGrab +
            "global power: " + globalPower);

            globalPower = MAX * multiplier;

            if(gamepad1.dpad_left) multiplier = decSpeed(multiplier);
            if(gamepad1.dpad_right) multiplier = incSpeed(multiplier);
            if(!gamepad1.dpad_left && !gamepad1.dpad_right) holdDownMultiChange = false;

            /*TODO write driving controls once the final wheel design is completed*/
        }

    }

    public void runAutoMode(){
        ;
    }
}
