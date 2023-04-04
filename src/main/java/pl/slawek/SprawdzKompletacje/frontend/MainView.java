package pl.slawek.SprawdzKompletacje.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;


class MainView extends AppLayout {

    MainView() {
        createHeader();
        createDrower();

    }

    private void createDrower() {

        addToDrawer(new VerticalLayout(
                getTabs()
        ));
    }

    private void createHeader() {
        H1 appTitle = new H1("Potwierdzenia kompletacji");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), appTitle);
        header.addClassName("header");

        addToNavbar(header);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.HOME, "Strona Główna", HomeView.class),
                createTab(VaadinIcon.CART, " Zamówienia", Orders.class));
//                createTab(VaadinIcon.USER_HEART, "Customers"),
//                createTab(VaadinIcon.PACKAGE, "Products"),
//                createTab(VaadinIcon.RECORDS, "Documents"),
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
