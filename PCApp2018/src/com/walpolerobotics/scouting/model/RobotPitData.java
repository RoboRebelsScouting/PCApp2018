package com.walpolerobotics.scouting.model;

/**
 * Created by 1153 on 1/24/2018.
 */
public class RobotPitData {
    public String scouterName = "";
    public String competition;
    public String team;
    public Integer weight;
    public String footPrint;
    public String frame;
    public String code;
    public String startLocation;
    public String autoScore;
    public String pickup;
    public String bbyScale;
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
        this.code = "";
        this.startLocation = "";
        this.autoScore = "";
        this.pickup = "";
        this.vault = "";
        this.switch = "";
        this.scale = "";
        this.climb = "";
}}
