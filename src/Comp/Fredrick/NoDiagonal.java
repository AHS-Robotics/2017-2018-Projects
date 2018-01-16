

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="No Diagonal", group="Testing")
public class NoDiagonal{
  private DcMotor frontLeft;
  private DcMotor backLeft;
  private DcMotor frontRight;
  private DcMotor backRight;
  private boolean pause;
  private double range;
  
  public void runOpMode(){
    pause = false;
    range = 0.8;
  
    frontLeft = hardwareMap.dcMotor.get("frontLeft");
    backLeft = hardwareMap.dcMotor.get("backLeft");
    frontRight = hardwareMap.dcMotor.get("frontRight");
    backRight = hardwareMap.dcMotor.get("backRight");
    
    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
  
    while(opModeIsActive()){
      if(gamepad1.start) pause = !pause;
      
      if(pause){
        ; something will happen here
        continue;
      }
      
      
    }
    
  }
}
