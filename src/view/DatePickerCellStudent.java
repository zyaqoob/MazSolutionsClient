/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Student;
import classes.Teacher;
import classes.User;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 *
 * @author Aitor
 */
public class DatePickerCellStudent extends TableCell<Student, Date> {

    private DatePicker datePicker;

    DatePickerCellStudent() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.setOnAction((e) -> {
                //super.commitEdit(datePicker.getValue().toString());
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }
      @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");     
        
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(datePicker);
            } else if(item!=null){
                String date=dateFormatter.format(item);
                setText(date);
                setGraphic(null);
            }
        }
    }
    @Override
    public void cancelEdit() {
        setGraphic(null);
        super.cancelEdit();
    }
}