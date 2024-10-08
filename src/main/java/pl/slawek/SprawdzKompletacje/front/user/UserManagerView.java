package pl.slawek.SprawdzKompletacje.front.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import pl.slawek.SprawdzKompletacje.entity.user.AppUserService;
import pl.slawek.SprawdzKompletacje.front.MainView;
import pl.slawek.SprawdzKompletacje.entity.user.AppUser;

import java.util.ArrayList;
import java.util.List;

@RolesAllowed("ADMIN")
@Route(value = "Uzytkownicy", layout = MainView.class)
@PageTitle("Użytkownicy aplikacji")
public class UserManagerView extends VerticalLayout {

    @Autowired
    private final AppUserService appUserService;
    private final Grid<AppUser> usersGrid = new Grid<>(AppUser.class, false);
    private List<AppUser> appUserList = new ArrayList<>();
    private Button button;

    public UserManagerView(final AppUserService appUserService) {
        this.appUserService = appUserService;
        createUserList();
        createGrid();
        createButton();
        addComponents();
    }

    private void createButton() {
        button = new Button("baton");
        button.addClickListener(event -> {
            Notification.show("klick", 1000, Notification.Position.BOTTOM_CENTER);
        });
    }

    private void createUserList() {
        appUserList = appUserService.findAllAppUser();
    }

    private void createGrid() {
        usersGrid.addColumn(AppUser::getFirsName).setHeader("Nazwa");
        usersGrid.addColumn(AppUser::getLastName).setHeader("Nazwisko");
        usersGrid.addColumn(AppUser::getUsername).setHeader("Login");
        usersGrid.addColumn(AppUser::getRole).setHeader("Rola");
        usersGrid.addColumn(new LocalDateTimeRenderer<>(AppUser::getCreateUserDate, "yyyy-MM-dd HH:mm:ss")).setHeader("Data utworzenia");
        usersGrid.addColumn(AppUser::getPassword).setHeader("Hasło");
        usersGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        usersGrid.setItems(appUserList);
    }

    private void addComponents() {
        add(usersGrid);
        add(button);
    }
}
