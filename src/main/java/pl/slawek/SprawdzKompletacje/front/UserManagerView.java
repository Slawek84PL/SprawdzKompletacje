package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import pl.slawek.SprawdzKompletacje.security.user.AppUser;
import pl.slawek.SprawdzKompletacje.security.user.AppUserService;

import java.util.ArrayList;
import java.util.List;

@RolesAllowed("ADMIN")
@Route(value = "Uzytkownicy", layout = MainView.class)
@PageTitle("Użytkownicy aplikacji")
public class UserManagerView extends VerticalLayout {

    @Autowired
    private final AppUserService appUserService;
    Grid<AppUser> usersGrid = new Grid<>(AppUser.class, false);
    private List<AppUser> appUserList = new ArrayList<>();

    public UserManagerView(final AppUserService appUserService) {
        this.appUserService = appUserService;
        createUserList();
        createGrid();
        addComponents();
    }

    private void createUserList() {
        appUserList = appUserService.findAllAppUser();
    }

    private void createGrid() {
        usersGrid.addColumn(AppUser::getName).setHeader("Nazwa");
        usersGrid.addColumn(AppUser::getUsername).setHeader("Login");
        usersGrid.addColumn(AppUser::getRole).setHeader("Rola");
        usersGrid.addColumn(new LocalDateTimeRenderer<>(AppUser::getCreateUserDate, "yyyy-MM-dd HH:mm.ss")).setHeader("Data utworzenia");
        usersGrid.addColumn(AppUser::getPassword).setHeader("Hasło");
        usersGrid.addThemeVariants(GridVariant.LUMO_COMPACT);

        usersGrid.setItems(appUserList);
    }

    private void addComponents() {
        add(usersGrid);
    }
}
