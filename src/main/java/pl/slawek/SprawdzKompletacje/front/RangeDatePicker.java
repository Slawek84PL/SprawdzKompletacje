package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

@Component
class RangeDatePicker extends VerticalLayout {

    private DatePicker startDate = new DatePicker("Początek");
    private DatePicker finishDate = new DatePicker("Koniec");

    public RangeDatePicker() {
        createStartDate();
        createFinishDate();
        addComponents();
    }

    private void addComponents() {
        add(startDate);
        add(finishDate);
    }

    private void createFinishDate() {
        finishDate.addValueChangeListener(event -> {
            startDate.setMin(event.getValue().minusMonths(1));
        });
    }

    private void createStartDate() {
        startDate.addValueChangeListener(event -> {
            finishDate.setMax(event.getValue().plusMonths(1));
        });
    }
}
