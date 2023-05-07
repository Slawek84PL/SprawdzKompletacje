package pl.slawek.SprawdzKompletacje.front.datePicker;

import com.vaadin.flow.component.datepicker.DatePicker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class DatePickerConfigI18N extends DatePicker.DatePickerI18n {

    DatePicker.DatePickerI18n polandI18n = new DatePicker.DatePickerI18n();
    DatePicker.DatePickerI18n germanI18n = new DatePicker.DatePickerI18n();


    DatePickerConfigI18N () {
        configureGerman();
        configurePoland();
    }

    private void configurePoland() {
        polandI18n.setDateFormat("yyyy-MM-dd");
        polandI18n.setMonthNames(List.of("Styczeń", "Luty", "Marzec", "Kwiecien",
                "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Pażdziernik",
                "Listopad", "Grudzień"));
        polandI18n.setWeekdays(List.of("Niedziela", "Poniedziałek", "Wtorek",
                "Środa", "Czwartek", "Piątek", "Sobota"));
        polandI18n.setWeekdaysShort(
                List.of("Ne", "Pn", "Wt", "Śr", "Czw", "Pt", "So"));
        polandI18n.setToday("Dziś");
        polandI18n.setCancel("Anuluj");
    }

    private void configureGerman() {
        polandI18n.setDateFormat("dd.MM.yy");
        germanI18n.setMonthNames(List.of("Januar", "Februar", "März", "April",
                "Mai", "Juni", "Juli", "August", "September", "Oktober",
                "November", "Dezember"));
        germanI18n.setWeekdays(List.of("Sonntag", "Montag", "Dienstag",
                "Mittwoch", "Donnerstag", "Freitag", "Samstag"));
        germanI18n.setWeekdaysShort(
                List.of("So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"));
        germanI18n.setToday("Heute");
        germanI18n.setCancel("Abbrechen");
    }
}
