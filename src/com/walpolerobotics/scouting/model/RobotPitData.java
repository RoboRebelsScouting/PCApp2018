package com.walpolerobotics.scouting.model;

/**
 * Created by 1153 on 1/24/2018.
 */
public class RobotPitData {
    public String scouterName;
    public String competition;
    public String team;
    public Integer weight;
    public String footPrint;
    public String frame;
    public String codeLanguage;
    public String startLocation;
    public String autoScore;
    public String pickup;
    public String allianceSwitch;
    public String vault;
    public String scale;
    public String climb;



    public void clear() {
        this.scouterName = "";
        this.competition = "";
        this.team = "";
        this.weight = 0;
        this.footPrint = "";
        this.frame = "";
        this.codeLanguage = "";
        this.startLocation = "";
        this.autoScore = "";
        this.pickup = "";
        this.vault = "";
        this.allianceSwitch = "";
        this.scale = "";
        this.climb = "";
}}
