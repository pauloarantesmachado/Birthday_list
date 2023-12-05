package com.birthdaylist.controller;

import com.birthdaylist.model.Guest;
import com.birthdaylist.utils.ButtonComponent;
import com.birthdaylist.utils.ConvertList;
import com.birthdaylist.view.AlertValidator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    @FXML
    private Button buttonGo;
    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TableView<Guest> table;

    @FXML
    private TableColumn<Guest, Boolean> delete;

    @FXML
    private TableColumn<Guest, String> nameGuest;

    @FXML
    private TableColumn<Guest, String> phoneGuest;

    @FXML
    private TableColumn<Guest, String> kinship;

    @FXML
    private ChoiceBox<String> typeKinship;

    @FXML
    void rowClicked(MouseEvent event){
        Guest clickGuest = table.getSelectionModel().getSelectedItem();
        name.setText(String.valueOf(clickGuest.getName()));
        phone.setText(String.valueOf(clickGuest.getPhone()));
        typeKinship.setValue(String.valueOf(clickGuest.getKinship()));
    }

    private final String[] modelKinship = {"Family", "Friend"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setItems(ConvertList.converterForObservableList(Guest.getDataInDataBase()));
        typeKinship.getItems().addAll(modelKinship);
        buttonGo.setDisable(true);
        name.textProperty().addListener((observable, oldValue, newValue) -> checkCampos());
        phone.textProperty().addListener((observable, oldValue, newValue) -> checkCampos());
        typeKinship.valueProperty().addListener((observable, oldValue, newValue) -> checkCampos());
        nameGuest.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        phoneGuest.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));
        kinship.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKinship()));
        delete.setCellValueFactory(param -> new SimpleBooleanProperty(true));
        delete.setCellFactory(col -> createButtonCell("Delete"));
    }

    private TableCell<Guest, Boolean> createButtonCell(String buttonLabel) {
        ButtonComponent button = new ButtonComponent(buttonLabel, table);
        if( buttonLabel.equals("Delete")) {
            button.DeleteItem();
        }
        return button;
    }
    private void checkCampos() {
        String nameGuestField = nameGuest.getText();
        String phoneGuestField = phoneGuest.getText();
        String kinshipField = kinship.getText();

        boolean fieldIsValid = nameGuestField != null && !nameGuestField.isEmpty() &&
                phoneGuestField != null &&
                kinshipField != null;
        buttonGo.setDisable(!fieldIsValid);
    }

    @FXML
    public void validator(ActionEvent event) {
        if (fieldFull()) {
            String nameGuestText = name.getText();
            String phoneGuestText = phone.getText();
            String typeRelations = typeKinship.getValue();
            if(phoneGuestText.length() > 10){
                Guest.validatorEndSetDb(nameGuestText, phoneGuestText, typeRelations);
                table.setItems(ConvertList.converterForObservableList(Guest.getDataInDataBase()));
                name.clear();
                phone.clear();
                typeKinship.getSelectionModel().clearSelection();
            }else {
                AlertValidator.showInformationAlert();
            }
        }
    }
    private boolean fieldFull() {
        return name.getText() != null && !name.getText().isEmpty() &&
               !phone.getText().isEmpty() &&
               typeKinship.getValue() != null;
    }

}
