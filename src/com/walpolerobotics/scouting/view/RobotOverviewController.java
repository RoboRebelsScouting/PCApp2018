package com.walpolerobotics.scouting.view;

/**
 * Created by 1153 on 1/27/2018.
 */
import com.walpolerobotics.scouting.model.RobotMatch;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.walpolerobotics.scouting.mainapp;
import com.walpolerobotics.scouting.model.Robot;
//making the table
public class RobotOverviewController {
    @FXML
    private TableView<String> robotTable;
    @FXML
    private TableColumn<String, String> importedFilesColumn;


//(button on the right side of the GUI)
    @FXML
    private Button checkdata;
    @FXML
    private Button importdata;
    @FXML
    private Button exportdata;
    @FXML
    private Button reportmissingdata;

//file import drop down option
    @FXML
    private void checkData(){
        mainApp.checkData();
    }
    @FXML
    private void handleImport(){

        mainApp.importAllRobotMatchData();
    }

    @FXML
    private void exportMysql(){
        mainApp.exportMysql();
    }

    @FXML
    private void reportMissingData(){
        mainApp.reportMissingData();
    }




    private mainapp mainApp;

    public RobotOverviewController(){

    }





    @FXML
    private void initialize(){
        importedFilesColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue()));






    }
    public void setMainApp(mainapp mainApp){
        this.mainApp = mainApp;
        robotTable.setItems(mainApp.getImportedFilesList());

    }
}

