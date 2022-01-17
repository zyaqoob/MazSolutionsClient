/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Teacher;
import classes.User;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 *
 * @author Aitor
 */
public class DatePickerCell extends TableCell<Teacher, String> {

    private DatePicker datePicker;

    DatePickerCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.setOnAction((e) -> {
                super.commitEdit(datePicker.getValue().toString());
                //commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }
    public void updateItem(){
        
    }
    
    @Override
    public void cancelEdit() {
        setGraphic(null);
        super.cancelEdit();
    }
}
