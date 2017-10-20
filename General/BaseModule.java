import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

enum MuiltiplierDirection{
  INCREASE, DECREASE
}

@TeleOp(name="Base Module 1.0.0", group="Building Blocks")
public class BaseModule extends LinearOpMode{
  private String output = ""
  
  // Speed control
  private final double INCREMENTER = 0.1;
  private double speedMultiplier = 0.0;
  
  // Hardware
  private DcMotor motorLeftBack;
  private DcMotor motorLeftFront;
  private DcMotor motorRightBack;
  private DcMotor motorRightFront;
  
  // Will be used to check for diagonal input
  private double lXAxis, lYAxis;
  private double rXAxis, rYAxis;
  
  /**
   * Changes the multiplier speed so the driver can control the max speed of the robot
   * @param d the direction in which we want to change the multiplier
  */
  public void changeSpeedMultiplier(MultiplierDirection d){
    boolean multiplierMax = (speedMultiplier > 1.0);
    boolean multiplierMin = (speedMultiplier < -1.0);
    
    if(multiplierMax) output += "\nError: The multiplier is already at its max of 1.0";
    else if(multiplierMin) output += "\nError: The multiplier is already at its min of -1.0";
    else{
      if(d == MultiplierDirection.INCREASE) speedMultiplier += INCREMENTER;
      else speedMultiplier -= INCREMENTER;
    }
  }
  
  public void update(){
    print("> ", output);
    lXAxis = gamepad1.left_stick_x;
    lYAxis = gamepad1.left_stick_y;
    rXAxis = gamepad1.right_stick_x;
    rYAxis = gamepad1.right_stick_y;
  }
  
  public void print(String prompt, String data){
    telemetry.addData(prompt, data);
    telemetry.update();
  }
 
  public void runOpMode(){
    motorLeftBack = hardwareMap.dcMotor.get("motorLeftBack");
    motorLeftFront = hardwareMap.dcMotor.get("motorLeftFront");
    motorRightBack = hardwareMap.dcMotor.get("motorRightBack");
    motorRightFront = hardwareMap.dcMotor.get("motorRightFront");
    
    motorLeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    motorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
    
    waitForStart();
    
    while(opModeIsActive()){
      update();
      
      // This will run if the driver is only pushing in the y axis
      if(lXAxis == 0 || rXAxis == 0){
        motorLeftBack.setPower(lYAxis * speedMultiplier);
        motorLeftFront.setPower(lYAxis * speedMultiplier);
        motorRightBack.setPower(lYAxis * speedMultiplier);
        motorRightFront.setPower(lYAxis * speedMultiplier);
      }
      
      
      
      idle();
    }
  }
}

