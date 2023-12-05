package com.birthdaylist.view;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AlertValidator extends Application {

    @Override
    public void start(Stage outputStage) throws Exception {
        showInformationAlert();
    }

    public static void showInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Phone is invalid");
        alert.setHeaderText(null);
        alert.setContentText("Enter the phone number correctly.");

        alert.getDialogPane().setStyle(
                "-fx-background-color: #D3D3D3; " +
                        "-fx-text-fill: Black; " +
                        "-fx-control-inner-background: #8B0000;"
        );

        alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0)).setStyle(
                "-fx-base:#8B0000; " +
                        "-fx-text-fill: Black;"
        );

        alert.getDialogPane().lookup(".content.label").setStyle("-fx-text-fill: Black;");

        alert.showAndWait();
    }
}
