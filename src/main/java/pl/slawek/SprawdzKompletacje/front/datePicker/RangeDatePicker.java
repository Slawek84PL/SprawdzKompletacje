package pl.slawek.SprawdzKompletacje.front.datePicker;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class RangeDatePicker extends VerticalLayout {

    private final DatePicker startDate = new DatePickerConfig("Początek");
    private final DatePicker finishDate = new DatePickerConfig("Koniec");

    public RangeDatePicker() {
        createStartDate();
        createFinishDate();
        addComponents();
    }

    private void addComponents() {
        HorizontalLayout hr = new HorizontalLayout();
        hr.add(startDate, finishDate);
        add(hr);
    }

    private void createStartDate() {
        startDate.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                finishDate.setMax(event.getValue().plusMonths(1));
                if (finishDate.getValue().isBefore(startDate.getValue())) {
                    finishDate.setValue(startDate.getValue());
                }
            }
        });
    }

    private void createFinishDate() {
        finishDate.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                startDate.setMin(event.getValue().minusMonths(1));
                if (startDate.getValue().isAfter(finishDate.getValue())) {
                    startDate.setValue(finishDate.getValue());
                }
            }
        });
    }
}
