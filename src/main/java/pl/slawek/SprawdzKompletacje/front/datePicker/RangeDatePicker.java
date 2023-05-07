package pl.slawek.SprawdzKompletacje.front.datePicker;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RangeDatePicker extends VerticalLayout {

    private final DatePicker startDate = new DatePickerConfig("Początek");
    private final DatePicker finishDate = new DatePickerConfig("Koniec");

    public RangeDatePicker() {
        createStartDate();
        createFinishDate();
        addComponents();
    }

    private void addComponents() {
        add(startDate);
        add(finishDate);
    }

    private void createStartDate() {
        startDate.addValueChangeListener(event -> {
            finishDate.setMax(event.getValue().plusMonths(1));
        });
    }

    private void createFinishDate() {
        finishDate.addValueChangeListener(event -> {
            startDate.setMin(event.getValue().minusMonths(1));
        });
    }
}
