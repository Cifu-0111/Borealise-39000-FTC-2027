package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class OdometrySubsystem {
    private GoBildaPinpointDriver pinpoint;

    public  OdometrySubsystem(HardwareMap hardwareMap) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        //Type de pods
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        //Direction (if valeur petite quand on avance "REVERSE")
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        //calibrer restart au debut
        pinpoint.resetPosAndIMU();
    }

    public void update(){
        //refresh donnes a chaque loop
        pinpoint.update();
    }

    public double getX(){
        // X position in mm
        return pinpoint.getPosX(DistanceUnit.MM);
    }
    public double getY(){
        // Y position in mm
        return pinpoint.getPosY(DistanceUnit.MM);
    }
    public double getHeading(){
        // Angle of orientation in radians
        return pinpoint.getHeading(AngleUnit.RADIANS);
    }
}
