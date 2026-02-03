package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class TeleOp extends LinearOpMode {

    private DcMotor MotHaD;                                         // A rajouter, moteurs pour envoyer la balle

    while (opModeIsActive()) {

        if (this.gamepad1.left_trigger) {                           // Ce serait confortable de faire en sorte que tant que l'autre touche n'est pas pressée

            MotHaD.setpower()                                       // Mettre la puissnace voulue entre -1 et 1 avec la puissance max  Position 1
        } 

        if (this.gamepad.right_trigger) {

            MotHaD.setpower()                                       // Mettre la puiissance du moteur pour la position 2
        }

// Par la suite, on voudra faire en sorte que la boule parte dans le canon.



        
        


    
}
