package pl.slawek.SprawdzKompletacje.front.user;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.slawek.SprawdzKompletacje.backend.entity.user.AppUser;
import pl.slawek.SprawdzKompletacje.backend.entity.user.AppUserService;

@Data
@Component
public class UserRegisterFormBinder {

    @Autowired
    private AppUserService userService;

    private UserRegisterForm form;

    private boolean enablePasswordValidation;

    public void addBindingAndValidation(UserRegisterForm form) {
        this.form = form;
        BeanValidationBinder<AppUser> binder = new BeanValidationBinder<>(AppUser.class);
        binder.bindInstanceFields(form);

        binder.forField(form.getPassword()).withValidator(this::passwordValidator).bind("password");
        form.getConfirmPassword().addValidationStatusChangeListener(event -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(form.getErrorMessageFields());

        form.getSubmitButton().addClickListener(event -> {
           try {
               AppUser userBean = new AppUser();
               binder.writeBean(userBean);
               userService.saveNewUser(userBean);
               showSuccess(userBean);
           } catch (ValidationException e) {

           }
        });
    }

    private void showSuccess(final AppUser userBean) {
        Notification notification = Notification.show("Dane zapisane dla nowego użytkownika "
        + userBean.getFirsName());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }


    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Hasło ma mniej niż 8 znaków");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = form.getConfirmPassword().getValue();

        if (pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Hasła są różne");
    }
}
