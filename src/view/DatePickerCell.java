/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Teacher;
import classes.User;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 *
 * @author Aitor
 */
public class DatePickerCell extends TableCell<Teacher, Date> {

    private DatePicker datePicker;

    DatePickerCell() {
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

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (datePicker != null) {
                   //     datePicker.setValue();
                    }
                    setText(null);
                    setGraphic(datePicker);
                } else {
                   // setText(item.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
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
