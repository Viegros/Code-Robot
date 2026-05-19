package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous

public class Automatique extends LinearOpMode {

  private DcMotor MotARD0, MotAVD1, MotARG2, MotAVG3;

  public void runOpMode() {

        initMoteurs();
        

        telemetry.addData("Statut", "Initialisé");
        telemetry.update();
        stop = true;


        waitForStart();

        while (opModeIsActive() && stop == false) {

              

        
    
        }
  }
  public void MCI(double VxRob, double alphaRob) {

      if (opModeIsActive()) {

        double R = 38.185;
        double l = 414;
        double leftPhi;
        double rightPhi;

        leftPhi = VxRob/R - l*alphaRob/(2*R);
        rightPhi = VxRob/R + l*alphaRob/(2*R);

        MotAVD1.setVelocity(rightPhi);
        MotARD0.setVelocity(rightPhi);
        MotAVG3.setVelocity(leftPhi);
        MotARD2.setVelocity(leftPhi);



  public void initMoteurs() {
      
  
      MotARD0 = hardwareMap.get(DcMotor.class, "MotARD0");
      MotAVD1 = hardwareMap.get(DcMotor.class, "MotAVD1");
      MotARG2 = hardwareMap.get(DcMotor.class, "MotARG2");
      MotAVG3 = hardwareMap.get(DcMotor.class, "MotAVG3");
      
      MotARD0.setDirection(DcMotor.Direction.REVERSE);
      MotAVD1.setDirection(DcMotor.Direction.REVERSE);
      MotARG2.setDirection(DcMotor.Direction.FORWARD);
      MotAVG3.setDirection(DcMotor.Direction.FORWARD);
  
 }
  
