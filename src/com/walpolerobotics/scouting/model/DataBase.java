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
            ss = "INSERT INTO pitinfo(scouterName, competition, team, weight, footPrint, frame, code, startLocation, autoScore, pickup, allianceSwitch, opponentSwitch, vault, scale, climb)\nVALUES\n(" + "\"" + rpd.competition +"\"";
            ss += ", \"" + rpd.scouterName + "\"";
            ss += ", \"" + rpd.competition + "\"";
            ss += ", \"" + rpd.team + "\"";
            ss += ", \"" + rpd.weight + "\"";
            ss += ", \"" + rpd.footPrint + "\"";
            ss += ", \"" + rpd.frame + "\"";
            ss += ", \"" + rpd.code + "\"";
            ss += ", \"" + rpd.startLocation + "\"";
            ss += ", \"" + rpd.autoScore + "\"";
            ss += ", \"" + rpd.pickup + "\"";
            ss += ", \"" + rpd.allianceSwitch + "\"";
            ss += ", \"" + rpd.opponentSwitch + "\")";
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


                    if ((RobotNumber == rm.robotNumber.intValue()) && (MatchNumber.equals(rm.matchNumber.getValue())) && (ScouterName.equals(rm.scouterName.getValue())) && (FirstCompetition.equals(rm.firstCompetition.getValue()))) {
                        dataExists = true;
                    }
                }



                if (!dataExists) {
                    for(RobotMatchData rmd : rm.getEventList()){
                        try{

                                stmt = con.createStatement();
                                ss = "INSERT INTO matchdata (robotNumber, matchNumber, gameEvent, subEvent, timeStamp, scouterName, firstCompetition, allianceColor)\nVALUES\n(" + rm.robotNumber.intValue();
                                ss += ", \"" + rm.matchNumber.get() + "\"";
                                ss += ", \"" + rmd.gameEvent + "\"";
                                ss += ", \"" + rmd.subEvent + "\"";
                                ss += ", \"" + rmd.timeStamp + "\"";
                                ss += ", \"" + rm.scouterName.get() + "\"";
                                ss += ", \"" + rm.firstCompetition.get() + "\"";
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

    public void constructAllianceData (AllianceData ad) {
        Statement stmt = null;
        ResultSet rs = null;



            try {
                stmt = con.createStatement();
                String ss = new String();


                ss = "insert into alliances set\n" +
                        "allianceNumber = "+ ad.allianceNumber +",\n" +
                        "Robot1 = "+ ad.robot1 +",\n" +
                        "Robot2 = "+ ad.robot2 +",\n" +
                        "Robot3 = "+ ad.robot3 +",\n" +

                        //climbed
                        "avgClimbed = (select\n" +
                        "\t\t\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'climbed',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END)) +\n" +
                        "    (SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'climbed',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END)) +\n" +
                        "    (SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'climbed',1,null)))'/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END)) as 'AvgClimbs'\n" +
                        "    from matchdata),\n" +

                        //parked
                        "avgParked = (select\n" +
                        "\t\t\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'parked',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END)) +\n" +
                        "    (SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'parked',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END)) +\n" +
                        "    (SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'parked',1,null)))'/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END)) as 'AvgParked'\n" +
                        "    from matchdata),\n" +

                        //avgAutoAllianceSwitch
                        "avgAutoAllianceSwitch = (select\n" +
                        "\t((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'autoAllianceSwitch',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'autoAllianceSwitch',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'autoAllianceSwitch',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) AS 'AvgAutoAllianceSwitch'\n" +
                        "\tfrom matchdata),\n" +

                        //avgTeleopAllianceSwitch
                        "avgTeleopAllianceSwitch = (select\n" +
                        "\t((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'teleopAllianceSwitch',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'teleopAllianceSwitch',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'teleopAllianceSwitch',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) AS 'AvgTeleopAllianceSwitch'\n" +
                        "\tfrom matchdata),\n" +

                        //avgVault
                        "avgVault = (select\n" +
                        "\t((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'vault',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'vault',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'vault',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) AS 'AvgVault'\n" +
                        "\tfrom matchdata),\n" +

                        //avgAutoScale
                        "avgAutoScale = (select\n" +
                        "\t((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'autoScale',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'autoScale',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'autoScale',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) AS 'AvgAutoScale'\n" +
                        "\tfrom matchdata),\n" +

                        //avgTeleopScale
                        "avgTeleopScale = (select\n" +
                        "\t((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'teleopScale',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'teleopScale',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'teleopScale',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) AS 'AvgTeleopScale'\n" +
                        "\tfrom matchdata),\n";

                System.out.println(ss);
                stmt.execute(ss);


            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        }
    }





