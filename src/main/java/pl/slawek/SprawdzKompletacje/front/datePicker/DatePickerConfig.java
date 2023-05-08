package pl.slawek.SprawdzKompletacje.front.datePicker;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.time.LocalDate;

class DatePickerConfig extends DatePicker {

    private DatePickerConfigI18N i18N = new DatePickerConfigI18N();

    DatePickerConfig(String name) {
        this.setLabel(name);
        this.setValue(LocalDate.now());
        this.setI18n(i18N.polandI18n);
    }
}
