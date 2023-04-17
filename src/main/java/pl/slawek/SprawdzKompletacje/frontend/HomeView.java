package pl.slawek.SprawdzKompletacje.frontend;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;


@Route(value = "Home", layout = MainView.class)
@PageTitle("Strona domowa!")
class HomeView extends VerticalLayout {

    public HomeView() {
        createView();
    }

    private void createView() {
        H1 title = new H1("Strona Domowa");
        Icon icon = new Icon(VaadinIcon.HOME);
        icon.setSize("35%");
        add(title, icon);
    }
}
