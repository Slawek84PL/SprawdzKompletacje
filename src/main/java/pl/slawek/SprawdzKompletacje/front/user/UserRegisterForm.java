package pl.slawek.SprawdzKompletacje.front.user;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Getter
@Component
public class UserRegisterForm extends FormLayout {

    private H3 title;

    private TextField firsName;
    private TextField lastName;

    private TextField username;

    private PasswordField password;
    private PasswordField confirmPassword;

    private Span errorMessageFields;

    private Button submitButton;

    public UserRegisterForm() {
        title = new H3("Zarejesrteuj użytkownika");

        firsName = new TextField("Imię");
        lastName = new TextField("Nazwisko");

        username = new TextField("Login");

        password = new PasswordField("Hasło");
        confirmPassword = new PasswordField("Potwierdź hasło");

        setRequiredIndicatorVisible(firsName, lastName,
                username, password, confirmPassword);

        errorMessageFields = new Span();

        submitButton = new Button("Zarejestruj");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, firsName, lastName, username, password,
                confirmPassword, errorMessageFields, submitButton);

        setMaxWidth("500px");

        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

        setColspan(title, 2);
        setColspan(firsName, 2);
        setColspan(username, 2);
        setColspan(errorMessageFields, 2);
        setColspan(submitButton, 2);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components)
                .forEach(component -> component.setRequiredIndicatorVisible(true));
    }

}
