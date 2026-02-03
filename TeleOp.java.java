package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;



public class TeleOp extends LinearOpMode {

    private DcMotor MotARD0, MotAVD1, MotARG2, MotAVG3;

    public void runOpMode() {

        initMoteurs();
        

        telemetry.addData("Statut", "Initialisé");
        telemetry.update();


        waitForStart();


        while (opModeIsActive()) {
                //boutons de la manette inutilisés: Tous saud joysticks
            
                //récupération des joysticks de la manette

                double y = -gamepad1.left_stick_y;  // forward/back
                double x = gamepad1.left_stick_x;   // strafe
                double rx = gamepad1.right_stick_x; // rotate
                
                //formule mouvement Mecanum 

                double AVG = y + x + rx;
                double AVD = y - x - rx;
                double ARG = y - x + rx;
                double ARD = y + x - rx;

                //contrôle moteurs mouvement
            
                MotARD0.setPower(ARD);
                MotAVD1.setPower(AVD);
                MotARG2.setPower(ARG);
                MotAVG3.setPower(AVG);
        }
    }


    public void initMoteurs() {
        
        //attribution des moteurs à leur objet physiques à contrôler + détermination du comportement du moteur


        MotARD0 = hardwareMap.get(DcMotor.class, "MotARD0");
        MotAVD1 = hardwareMap.get(DcMotor.class, "MotAVD1");
        MotARG2 = hardwareMap.get(DcMotor.class, "MotARG2");
        MotAVG3 = hardwareMap.get(DcMotor.class, "MotAVG3");
        
        //déclaration des sens de rotation des moteurs liés à chaque roue
        MotARD0.setDirection(DcMotor.Direction.REVERSE);
        MotAVD1.setDirection(DcMotor.Direction.REVERSE);
        MotARG2.setDirection(DcMotor.Direction.FORWARD);
        MotAVG3.setDirection(DcMotor.Direction.FORWARD);

    }

}
