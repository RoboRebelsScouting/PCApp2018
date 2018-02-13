package com.walpolerobotics.scouting.model;

/**
 * Created by 1153s on 2/10/2018.
 */
public class MatchSet {
    public int matchNumber;
    public int[] redNum = new int[3];
    public int[] blueNum = new int[3];

    public MatchSet (int matchNumber) {
        this.matchNumber = matchNumber;
        this.redNum[0] = 0;
        this.redNum[1] = 0;
        this.redNum[2] = 0;
        this.blueNum[0] = 0;
        this.blueNum[1] = 0;
        this.blueNum[2] = 0;

    }

}
