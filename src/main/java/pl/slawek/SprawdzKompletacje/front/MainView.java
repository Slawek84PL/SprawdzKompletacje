package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import pl.slawek.SprawdzKompletacje.front.user.UserManagerView;
import pl.slawek.SprawdzKompletacje.security.SecurityService;

@CssImport("./themes/mojeStyle/styles.css")
public class MainView extends AppLayout {
    private final Button logout = new Button();
    private final HorizontalLayout header = new HorizontalLayout();

    private final SecurityService securityService;

    MainView(@Autowired SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrower();
        createLogout();
    }

    private void createLogout() {
        logout.addClickListener(event -> securityService.logout());
        if (securityService.isAuthenticated()) {
            logout.setText("Wyloguj się " + securityService.getAuthenticatedUser().getFirsName());
            logout.setVisible(true);
        } else {
            logout.setVisible(false);
        }

        header.add(logout);
    }

    private void createDrower() {
        addToDrawer(new VerticalLayout(getTabs()));
    }

    private void createHeader() {
        H1 appTitle = new H1("Potwierdzenia kompletacji");

        header.add(new DrawerToggle(), appTitle);

        addToNavbar(header);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.HOME, "Strona Główna", HomeView.class),
                createTab(VaadinIcon.CART, " Zamówienia", Orders.class),
                createTab(VaadinIcon.USER_HEART, "Dodaj zamówienie do sprawdzenia", UploadFile.class),
                createTab(VaadinIcon.PACKAGE, "Zakończone zamówienia", OrdersFinished.class),
                createTab(VaadinIcon.RECORDS, "Użytkownicy", UserManagerView.class));
//                createTab(VaadinIcon.LIST, "Tasks"),
//                createTab(VaadinIcon.CHART, "Analytics"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class routerLink) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        link.setRoute(routerLink);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
