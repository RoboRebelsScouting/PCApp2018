package com.walpolerobotics.scouting;/**
 * Created by 1153 on 1/24/2018.
 */

import com.walpolerobotics.scouting.model.*;
import com.walpolerobotics.scouting.model.Robot;
import com.walpolerobotics.scouting.view.RobotOverviewController;
import com.walpolerobotics.scouting.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class mainapp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Robot> robotData = FXCollections.observableArrayList();
    private ObservableList<RobotMatch> robotMatchInfo = FXCollections.observableArrayList();
    private ObservableList<RobotPitData> robotPitDataList = FXCollections.observableArrayList();
    private ObservableList<String> importedFilesList = FXCollections.observableArrayList();
    private List<MatchSet> matchSetList = new ArrayList <MatchSet>();

    public DataBase db;



    public ObservableList<Robot> getRobotData() {
        return robotData;
    }
    public ObservableList<String> getImportedFilesList() {
        return importedFilesList;
    }
    public ObservableList<RobotMatch> getRobotMatchInfo() {
        return robotMatchInfo;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Robo Rebels Scouting");

        this.db = new DataBase();

        initRootLayout();

        showRobotOverview();
    }

    public void importAllRobotMatchData() {
        File folder = new File("C:/Users/1153s/Documents/NewMatchFiles");

        String pathName = folder.getAbsolutePath();
        String[] listOfFiles = folder.list();
        if (listOfFiles != null){
            for (int c = 0; c < listOfFiles.length; c++) {
                String fullPathName = pathName + "/" + listOfFiles[c];
                File newFile = new File(fullPathName);
                if (fullPathName.endsWith(".csv")) {
                    if (fullPathName.endsWith("-pit.csv")) {
                        importRobotPitData(newFile);
                    } else {
                            importRobotMatchData(newFile);
                        }
                    }
                }
            }
        }

    public void importRobotMatchData(File file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            Robot r = new Robot();
            RobotMatch rm = new RobotMatch();
            int lineCount = 0;
            int robotNumber = 0;
            String firstCompetition = "";
            String matchNumber = "";
            String scouterName = "";
            System.out.println("reading file: " + file.getName());




            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] lineList = line.split(",");
                if (lineCount == 0) {
                    robotNumber = Integer.parseInt(lineList[2]);
                    matchNumber = lineList[1];
                    scouterName = lineList[3];
                    firstCompetition = lineList[0];}
                else{
                    RobotMatchData rmd = new RobotMatchData();
                    rmd.robotNumber = robotNumber;
                    rmd.firstCompetition = firstCompetition;
                    rmd.matchNumber = matchNumber;
                    rmd.timeStamp = Integer.parseInt(lineList[0]);
                    rmd.gameEvent = lineList[1];
                    rm.setRobotNumber(robotNumber);
                    rm.setFirstCompetition(firstCompetition);
                    rm.setMatchNumber(matchNumber);
                    rm.setScouterName(scouterName);


                    if(lineList[1].equals("allianceColor")&& lineList[2].equals("Blue")){
                        rmd.subEvent = 0;
                    }else{
                        if(lineList[1].equals("allianceColor")&& lineList[2].equals("Red")) {
                            rmd.subEvent = 1;
                        }else{
                            rmd.subEvent = Integer.parseInt(lineList[2]);
                        }
                    }



                    rm.getEventList().add(rmd);
                }






                lineCount++;
                }

            r.robotMatch.add(rm);
            importedFilesList.add(file.getName());
            //robotData.add(new Robot(8976,7,3,72,7,3,5,"shooting",0));
            r.setRobotNumber(robotNumber);
            System.out.println("the robot number was set to" + r.getRobotNumber());


            robotData.add(r);
            robotMatchInfo.add(rm);
            br.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }

        try {
            String newPath = "C:\\Users\\1153s\\Documents\\ImportedMatchFiles";
            String newName = newPath+File.separator+file.getName();

            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(newName));
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void importRobotPitData(File file) {
        File folder = new File("C:/Users/1153s/Documents/NewPitFiles");
        try {
            System.out.println("Reading first file " + file.getName());

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            RobotPitData rpd = new RobotPitData();

            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                String[] lineList = line.split(",");
                if(lineList.length != 11){
                    System.out.println("didn't get all columns");
                    for(int c= 0;c<lineList.length;c++){
                        System.out.println("column: " + c + " ;value: "  + lineList[c]);
                    }
                }
                if (lineCount == 0) {

                    rpd.scouterName = lineList[0];
                    rpd.competition = lineList[1];
                    rpd.team = lineList[2];
                    rpd.weight = Integer.parseInt(lineList[3]);
                    rpd.footPrint = lineList[4];
                    rpd.frame = lineList[5];
                    rpd.codeLanguage = lineList[6];
                    rpd.startLocation = lineList[7];
                    rpd.autoScore = lineList[8];
                    rpd.pickup = lineList[9];
                    rpd.vault = lineList[10];
                    rpd.allianceSwitch = lineList[11];
                    rpd.scale = lineList[13];
                    rpd.climb = lineList[14];



                    lineCount++;

                    // add this robot pit data to the list
                    robotPitDataList.add(rpd);
                    importedFilesList.add(file.getName());
                }
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            String newPath = "C:\\Users\\1153s\\Documents\\ImportedPitFiles";
            String newName = newPath+File.separator+file.getName();
            if(Files.exists(Paths.get(newName))){
                newName += System.currentTimeMillis();
            }
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(newName));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainapp.class.getResource("view/root layout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRobotOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainapp.class.getResource("view/Robot Overview.fxml"));
            AnchorPane robotOverview = loader.load();
            rootLayout.setCenter(robotOverview);

            RobotOverviewController controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void exportMysql(){
        db = new DataBase();

        for (Robot r : this.getRobotData()){
            db.writeRobotToDB(r);
        }

        // also save pit scout data to pit table
        for (RobotPitData rpd : this.robotPitDataList) {
            db.writePitDataToDB(rpd);
        }

    }

    public MatchSet findMatchSet (int matchNumber) {

        // loop through all existing match sets in the match set list
        // if we have one for the match number we are looking for, return it
        for (MatchSet c_matchSet : matchSetList) {
            if (c_matchSet.matchNumber == matchNumber) {
                return c_matchSet;
            }
        }

// if we get here, it means we don’t have a match set for the match number
// create one, then add it to the match list and return the match set we just created
        MatchSet t_matchSet = new MatchSet(matchNumber);
        matchSetList.add(t_matchSet);
        return t_matchSet;
    }

    public void reportMissingData() {
        // get all the records from the database
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/roborebels", "root", "1153");
            Statement stmt;
            ResultSet rs;
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from matchdata");

            // iterate through the records
            while (rs.next()) {

                // get match number, robot number, alliance color, alliance position from the record
                // subtract one from the alliancePosition since the array in MatchSet goes from 0-2 not 1-3
                int matchNumber = rs.getInt("Match Number");
                int robotNumber = rs.getInt("Robot Number");
                int alliancePosition = rs.getInt("Alliance Position");
                String allianceColor = rs.getString("Alliance Color");

                alliancePosition -= 1;

                MatchSet t_matchSet = findMatchSet(matchNumber);
                if (allianceColor == "red") {
                    t_matchSet.redNum[alliancePosition] = robotNumber;
                } else {
                    t_matchSet.blueNum[alliancePosition] = robotNumber;
                }
            }

        }catch(java.sql.SQLException e){
            System.out.println("Error opening SQL Database.");
        }
    }

    public void checkData(){
        File folder = new File("C:/Users/1153s/Documents/NewMatchFiles");

        String pathName = folder.getAbsolutePath();
        String[] listOfFiles = folder.list();
        int fileLength = listOfFiles.length;
        int matchFiles = 6;
        if (fileLength < matchFiles){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Files");
            alert.setContentText("Files are missing!");
            alert.showAndWait();
        } else if(fileLength > matchFiles) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Extra Files");
            alert.setContentText("There are more than 6 files.");
            alert.showAndWait();
        } else if(fileLength == matchFiles) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Correct Number Of Files");
            alert.setContentText("There are 6 files.");
            alert.showAndWait();
        }
    }



    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
