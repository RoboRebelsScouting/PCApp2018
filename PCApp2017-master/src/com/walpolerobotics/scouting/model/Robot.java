package com.walpolerobotics.scouting.model;

/**
 * Created by 1153 on 1/30/2016.
 */
import javafx.beans.property.*;

import java.util.ArrayList;


public class Robot {
    private final IntegerProperty robotNumber;


    public ArrayList<RobotMatch> robotMatch;



    public Robot() {
        this(0);
        robotMatch = new ArrayList<RobotMatch>();

    }
//ordering the variables *****don't forget to add to the int line)
    public Robot(int robotNumber ) {

        this.robotNumber = new SimpleIntegerProperty(robotNumber);


    }
//getting and setting (letting people access the private information)
    public int getRobotNumber() {
        return robotNumber.get();
    }
    public void setRobotNumber(int robotNumber) {
        this.robotNumber.set(robotNumber);
    }
    public IntegerProperty robotNumberProperty() {
        return robotNumber;
    }










}

