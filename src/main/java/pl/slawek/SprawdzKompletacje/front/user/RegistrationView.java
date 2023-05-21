package pl.slawek.SprawdzKompletacje.front.user;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
@Route("Zarejestruj")
class RegistrationView extends VerticalLayout {

    private final UserRegisterForm form;
    private final UserRegisterFormBinder formBinder;
    RegistrationView(final UserRegisterForm form, final UserRegisterFormBinder formBinder) {
        this.form = form;
        this.formBinder = formBinder;
        createView();
    }

    private void createView() {
        setHorizontalComponentAlignment(Alignment.CENTER, this.form);

        add(this.form);

        this.formBinder.addBindingAndValidation(this.form);
    }
}
