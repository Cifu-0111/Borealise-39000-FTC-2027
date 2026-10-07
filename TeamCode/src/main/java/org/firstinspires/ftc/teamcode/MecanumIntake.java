package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumIntake {
private DcMotor motor1;//left
private DcMotor motor2;//right

public void init(HardwareMap hwMap){
    //touch sensor code

    //DC motor
      motor1 = hwMap.get(DcMotor.class, "left_intake");
      motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

      motor2 = hwMap.get(DcMotor.class, "right_intake");
      motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
}
public void setMotorSpeed(double speed){
    motor1.setPower(speed);
    motor2.setPower(-speed);
}


}
