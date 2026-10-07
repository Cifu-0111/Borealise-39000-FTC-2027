package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class DcMotors extends OpMode {
    MecanumIntake intake = new MecanumIntake();
    boolean intakeOn = false;
    boolean lastA = false;

    @Override
    public void init(){
        intake.init(hardwareMap);
    }

    @Override
    public void loop(){
        if (gamepad1.a && !lastA){ //si on a clique sur A: A = true & lastA = false, !lastA = true (true & true = compatible)
            intakeOn = !intakeOn; //intake = off, si on a clique intake = on
        }
        lastA = gamepad1.a;
        if (intakeOn) {
        intake.setMotorSpeed(1.0);//on
        }
        else {
        intake.setMotorSpeed(0.0);//off
        }
    }
}
