package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo; // Importation pour tous les servos

@TeleOp
    
public class TeleOp extends LinearOpMode {

    // --- Variables de la Base Mecanum ---
    private DcMotor MotARD0, MotAVD1, MotARG2, MotAVG3;
    
    // --- Variables pour le Canon ---
    private DcMotor MotCanonGauche, MotCanonDroite;
    
    // --- Variable pour le choix de la couleur ---
    private Servo ServoChoixCouleur;
    
    // --- Servo pour envoyer dans le canon ---
    private Servo ServoCompartimentGauche;
    private Servo ServoCompartimentDroit;

    @Override
    public void runOpMode() {

        // Initialisation de tous les composants
        initMoteurs();
        
        telemetry.addData("Statut", "Initialisé (Base + 3 Servos)");
        telemetry.update();

        waitForStart();

        // Boucle principale
        while (opModeIsActive()) {
            
           
            // PARTIE 1 : Faire bouger le robot
            
            double y = -gamepad1.left_stick_y;  
            double x = gamepad1.left_stick_x;   
            double rx = gamepad1.right_stick_x; 
            
            double AVG = y + x + rx;
            double AVD = y - x - rx;
            double ARG = y - x + rx;
            double ARD = y + x - rx;

            MotARD0.setPower(ARD);
            MotAVD1.setPower(AVD);
            MotARG2.setPower(ARG);
            MotAVG3.setPower(AVG);

           
            // PARTIE 2 : Envoyer les balles
            
            if (this.gamepad1.b) { 
                MotCanonDroite.setPower(1.0);
                MotCanonGauche.setPower(1.0); 
            } 
            else if (this.gamepad1.dpad_left) { 
                MotCanonDroite.setPower(0.5);
                MotCanonGauche.setPower(0.5); 
            } 
            else {
                MotCanonDroite.setPower(0.0);
                MotCanonGauche.setPower(0.0);
            }

            
            // PARTIE 3 : Choix des couleurs
            
            if (this.gamepad1.left_bumper) { // L1
                // Le servo bascule complètement à gauche
                ServoChoixCouleur.setPosition(0.0); 
            } 
            else if (this.gamepad1.right_bumper) { // R1
                // Le servo bascule complètement à droite
                ServoChoixCouleur.setPosition(1.0);  
            } 
            else {
                // Quand on relâche, il revient en position d'origine 
                ServoChoixCouleur.setPosition(0.5);  
            }

            
            // PARTIE 4 : Pousser les balles dans le canon
   
            
            // Compartiment Gauche (L2)
            if (this.gamepad1.left_trigger > 0.3) { 
                ServoCompartimentGauche.setPosition(0.8); 
            } 
            else {
                ServoCompartimentGauche.setPosition(0.0);
            }

            // Compartiment Droit (R2)
            if (this.gamepad1.right_trigger > 0.3) { 
                ServoCompartimentDroit.setPosition(0.8); 
            } 
            else {
                ServoCompartimentDroit.setPosition(0.0);
            }
        }
    }

    // --- Initialisation ---
    public void initMoteurs() {
        
        // Configuration des moteurs
        MotARD0 = hardwareMap.get(DcMotor.class, "MotARD0");
        MotAVD1 = hardwareMap.get(DcMotor.class, "MotAVD1");
        MotARG2 = hardwareMap.get(DcMotor.class, "MotARG2");
        MotAVG3 = hardwareMap.get(DcMotor.class, "MotAVG3");
        
        MotCanonGauche = hardwareMap.get(DcMotor.class, "MotCanonGauche");
        MotCanonDroite = hardwareMap.get(DcMotor.class, "MotCanonDroite");
        
        // Configuration Servomoteurs
        ServoChoixCouleur = hardwareMap.get(Servo.class, "ServoChoixCouleur");
        ServoCompartimentGauche = hardwareMap.get(Servo.class, "ServoCompartimentGauche");
        ServoCompartimentDroit  = hardwareMap.get(Servo.class, "ServoCompartimentDroit");
        
        // Sens des moteurs pour le mouvement
        MotARD0.setDirection(DcMotor.Direction.REVERSE);
        MotAVD1.setDirection(DcMotor.Direction.REVERSE);
        MotARG2.setDirection(DcMotor.Direction.FORWARD);
        MotAVG3.setDirection(DcMotor.Direction.FORWARD);
        
        MotCanonGauche.setDirection(DcMotor.Direction.FORWARD);
        MotCanonDroite.setDirection(DcMotor.Direction.FORWARD);

        // Positions initiales des servomoteurs
        ServoChoixCouleur.setPosition(0.5);       // Centré par défaut
        ServoCompartimentGauche.setPosition(0.0); // Fermé par défaut
        ServoCompartimentDroit.setPosition(0.0);  // Fermé par défaut
    }
}

