package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "login", layout = MainView.class)
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    H1 heder = new H1();
    LoginForm login = new LoginForm();
    public LoginView() {
        buildView();
        buildTitle();
        addComponents();
        login.setAction("login");
    }

    private void addComponents() {
        add(heder);
        add(login);
    }

    private void buildTitle() {
        heder.setText("Weryfikacja zamówień");
    }

    private void buildView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
