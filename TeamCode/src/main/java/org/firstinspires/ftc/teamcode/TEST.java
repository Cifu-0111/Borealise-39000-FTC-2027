package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
public class TEST extends OpMode{
    @Override
    public void init() {
        telemetry.addData("Hellooo", "Team");
    }

    @Override
    public void loop() {
    }
}
