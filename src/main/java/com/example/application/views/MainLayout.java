package com.example.application.views;

import com.example.application.I18nProvider;
import com.example.application.security.SecurityService;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Locale;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();

        if (securityService.getAuthenticatedUser() != null) {
            createDrawer();
        }
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        HorizontalLayout header = null;
        if (securityService.getAuthenticatedUser() == null) {
            header = new HorizontalLayout(logo, createLocaleSelector());
        } else {
            String u = securityService.getAuthenticatedUser().getUsername();
            Button logout = new Button(I18nProvider.getTranslation("main.logout") + " " + u, e -> securityService.logout()); // <2>
            header = new HorizontalLayout(new DrawerToggle(), logo, createLocaleSelector(), logout);
        }

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        header.addClassName("header-view");
        addToNavbar(header);
    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink(I18nProvider.getTranslation("main.contatos"), ListView.class),
                new RouterLink(I18nProvider.getTranslation("main.dashboard"), DashboardView.class)
        ));
    }

    private ComboBox<Locale> createLocaleSelector() {
        ComboBox<Locale> localeComboBox = new ComboBox<>();
        localeComboBox.setItems(Locale.ENGLISH, new Locale("pt", "BR"));
        localeComboBox.setValue(I18nProvider.getCurrentLocale());
        localeComboBox.setItemLabelGenerator(Locale::getDisplayLanguage);
        localeComboBox.addValueChangeListener(event -> {
            Locale selectedLocale = event.getValue();
            if (selectedLocale != null) {
                VaadinSession.getCurrent().setLocale(selectedLocale);
                UI.getCurrent().setLocale(selectedLocale);
                UI.getCurrent().getPage().reload();
                I18nProvider.saveLocaleToCookie(selectedLocale);
            }
        });

        return localeComboBox;
    }


}