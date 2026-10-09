package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class MecanumFlower {
    private Servo servo1;//left
    private Servo servo2;//right

    public void init(HardwareMap hwMap){

        servo1 = hwMap.get(Servo.class, "left_flower");
        servo2 = hwMap.get(Servo.class, "right_flower");

        servo2.setDirection(Servo.Direction.REVERSE);
    }

    public void setServoPosition(double position){
        servo1.setPosition(position);
        servo2.setPosition(position);

    }
}
