Pas de problème chef, le voici tout propre et prêt à servir !

C'est la version finale validée avec tes 5 moteurs (4 pour la base + 1 pour le canon + 1 pour le ramasseur) et tes 3 servomoteurs, incluant le système d'interrupteur sur le bouton A pour l'intake.

Java
package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor; // Importation pour tous les moteurs 
import com.qualcomm.robotcore.hardware.Servo; // Importation pour tous les servosmoteurs

@TeleOp
    
public class Tele_OP extends LinearOpMode {

    // --- Variables de la Base Mecanum ---
    private DcMotor MotARD0, MotAVD1, MotARG2, MotAVG3;
    
    // --- Variable pour le Canon ---
    private DcMotor MotCanon;
    
    // --- Variable pour le système de ramassage de balles ---
    private DcMotor MotRamasseur;
    
    // --- Variables de mémoire pour le système d'interrupteur (Toggle) ---
    private boolean intakeOn = false;
    private boolean lastAState = false;
    
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

           
            
            // PARTIE 2 : Envoyer les balles (Canon)
            
            if (this.gamepad1.b) { 
                MotCanon.setPower(1.0);
            } 
            else if (this.gamepad1.dpad_left) { 
                MotCanon.setPower(0.5); 
            } 
            else {
                MotCanon.setPower(0.0);
            }

            
            
            // PARTIE 3 : Choix des couleurs (Trieur)
            
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
                ServoCompartimentGauche.setPosition(-0.8); 
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
            
            
            // PARTIE 5 : Système de ramassage (Bouton A)
            
            // On détecte le moment exact où le bouton A passe de "relâché" à "appuyé"
            if (this.gamepad1.a && !lastAState) {
                intakeOn = !intakeOn; // On inverse l'état (ON/OFF)
            }
            lastAState = this.gamepad1.a; // Mémorisation de l'état du bouton

            // Application de la puissance au moteur selon l'interrupteur
            if (intakeOn) {
                MotRamasseur.setPower(1.0); // Active le ramassage à pleine puissance
            } else {
                MotRamasseur.setPower(0.0); // Arrête le ramasseur
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
        
        MotCanon = hardwareMap.get(DcMotor.class, "MotCanon");
        MotRamasseur = hardwareMap.get(DcMotor.class, "MotRamasseur");
        
        // Configuration Servomoteurs
        ServoChoixCouleur = hardwareMap.get(Servo.class, "ServoChoixCouleur");
        ServoCompartimentGauche = hardwareMap.get(Servo.class, "ServoCompartimentGauche");
        ServoCompartimentDroit  = hardwareMap.get(Servo.class, "ServoCompartimentDroit");
        
        // Sens des moteurs pour le mouvement
        MotARD0.setDirection(DcMotor.Direction.REVERSE);
        MotAVD1.setDirection(DcMotor.Direction.REVERSE);
        MotARG2.setDirection(DcMotor.Direction.FORWARD);
        MotAVG3.setDirection(DcMotor.Direction.FORWARD);
        
        MotCanon.setDirection(DcMotor.Direction.REVERSE);
        MotRamasseur.setDirection(DcMotor.Direction.FORWARD);

        // Positions initiales des servomoteurs
        ServoChoixCouleur.setPosition(0.5);       // Centré par défaut
        ServoCompartimentGauche.setPosition(0.0); // Fermé par défaut
        ServoCompartimentDroit.setPosition(0.0);  // Fermé par défaut
    }
}
