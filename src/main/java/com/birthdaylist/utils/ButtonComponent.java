package com.birthdaylist.utils;

import com.birthdaylist.model.Guest;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

public class ButtonComponent extends TableCell<Guest, Boolean> {
    private  final Button button;
    private final TableView<Guest> table;
    public ButtonComponent(String buttonLabel, TableView<Guest> table) {
        this.button = new javafx.scene.control.Button(buttonLabel);
        this.table = table;
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || !item) {
            setGraphic(null);
        } else {
            setAlignment(Pos.CENTER);
            setGraphic(button);
        }
    }
    public void DeleteItem(){
        button.setOnAction(event -> {
            Guest guest = getTableView().getItems().get(getIndex());
            int guestId = guest.getId();
            Guest.DeleteInDataBase(guestId);
            this.table.setItems(ConvertList.converterForObservableList(Guest.getDataInDataBase()));
        });

    }

}