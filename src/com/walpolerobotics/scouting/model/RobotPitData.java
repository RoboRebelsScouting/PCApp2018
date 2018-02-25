package com.walpolerobotics.scouting.model;

/**
 * Created by 1153 on 1/24/2018.
 */
public class RobotPitData {
    public String scout;
    public String competition;
    public Integer team;
    public Integer robotWeight;
    public Integer robotHeight;
    public String frame;
    public String feet;
    public String climbWay;
    public boolean vault;
    public String codeLanguage;
    public String sideAuto;

    public enum Frame {
        kitbot,
        steel,
        wood,
        aluminum,
        other,
    }

    public enum climb {
        noClimb,
        yesClimb,
        climbCarry,
        climbSupport,
    }
    public enum side{
        startLeft,
        startMiddle,
        startRight,
    }
    public enum feet{
        noScale,
        fourFeet,
        fiveFeet,
        sixFeet,
    }
    public enum cLanguage{
        codeJava,
        codeC,
        codeLab,
        codeOther;
    }


    public void clear() {
        this.scout = "";
        this.competition = "";
        this.team = 0;
        this.robotWeight = 0;
        this.robotHeight =0;
        this.frame = "";
        this.feet = "";
        this.climbWay = "";
        this.vault = false;
        this.codeLanguage = "";
        this.sideAuto = "";
}}
