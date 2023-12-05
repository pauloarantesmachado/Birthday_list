package com.birthdaylist.utils;

import com.birthdaylist.model.Guest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Set;

public class ConvertList {
    public static ObservableList<Guest> converterForObservableList(Set<Guest> listGuest){
        ObservableList<Guest> observableSet = FXCollections.observableArrayList();
        observableSet.addAll(listGuest);
        return observableSet;
    }
}
