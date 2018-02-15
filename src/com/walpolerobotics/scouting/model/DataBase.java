package com.walpolerobotics.scouting.model;

/**
 * Created by 1153 on 1/24/2018.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DataBase {
    Connection con = null;


    String url = "jdbc:mysql://localhost:3306/roborebels";
    String user = "root";
    String password = "1153";

    public DataBase() {


        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DataBase.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);


        }
    }









    public void writePitDataToDB (RobotPitData rpd) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String ss = new String();
            //ss = "INSERT INTO pitInfo(Scout)\nVALUES\n(";
            ss = "INSERT INTO pitinfo(competition, team, scouterName, weight, footPrint, frame, codeLanguage, startLocation, autoScore, pickup, allianceSwitch,  vault, scale, climb)\nVALUES\n(" + "\"" + rpd.competition +"\"";
            ss += ", \"" + rpd.team + "\"";
            ss += ", \"" + rpd.scouterName + "\"";
            ss += ", \"" + rpd.weight + "\"";
            ss += ", \"" + rpd.footPrint + "\"";
            ss += ", \"" + rpd.frame + "\"";
            ss += ", \"" + rpd.codeLanguage + "\"";
            ss += ", \"" + rpd.startLocation + "\"";
            ss += ", \"" + rpd.autoScore + "\"";
            ss += ", \"" + rpd.pickup + "\"";
            ss += ", \"" + rpd.allianceSwitch + "\"";
            ss += ", \"" + rpd.vault + "\"";
            ss += ", \"" + rpd.scale + "\"";
            ss += ", \"" + rpd.climb + "\")";


            stmt.execute(ss);


        }catch(SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());

            }



    }



    public void writeRobotToDB(Robot r) {
        Statement stmt = null;
        ResultSet rs = null;

        for (RobotMatch rm : r.robotMatch) {
            try {
                stmt = con.createStatement();
                String ss = "SELECT * FROM matchdata;";

                rs = stmt.executeQuery(ss);
                Boolean dataExists = false;


                while (rs.next()) {
                    Integer RobotNumber = rs.getInt("robotNumber");
                    String MatchNumber = rs.getString("matchNumber");
                    String ScouterName = rs.getString("scouterName");
                    String FirstCompetition = rs.getString("firstCompetition");
                    String AllianceColor = rs.getString("allianceColor");
                    String AlliancePosition = rs.getString("alliancePosition");


                    if ((RobotNumber == rm.robotNumber.intValue()) && (MatchNumber.equals(rm.matchNumber.getValue())) && (ScouterName.equals(rm.scouterName.getValue())) && (FirstCompetition.equals(rm.firstCompetition.getValue()))) {
                        dataExists = true;
                    }
                }



                if (!dataExists) {
                    for(RobotMatchData rmd : rm.getEventList()){
                        try{

                                stmt = con.createStatement();
                                ss = "INSERT INTO matchdata (robotNumber, matchNumber, gameEvent, timeStamp, scouterName, firstCompetition, allianceColor, alliancePosition)\nVALUES\n(" + rm.robotNumber.intValue();
                                ss += ", \"" + rm.matchNumber.get() + "\"";
                                ss += ", \"" + rmd.gameEvent + "\"";
                                ss += ", \"" + rmd.timeStamp + "\"";
                                ss += ", \"" + rm.scouterName.get() + "\"";
                                ss += ", \"" + rm.firstCompetition.get() + "\"";
                                ss += ", \"" + rm.allianceColor.get() + "\"";
                                ss += ", \"" + rm.alliancePosition.get() + "\"";
                                ss += ");";
                                    stmt.execute(ss);
                                System.out.println(ss);

                        }catch (SQLException ex) {
                            System.out.println("SQLException: " + ex.getMessage());

                        }

                    }
                }


                //System.out.println(ss);

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        }


    }
}





