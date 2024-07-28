package com.example.application.views;

import com.example.application.I18nProvider;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login", layout = MainLayout.class)
@PageTitle("Login | Vaadin CRM")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login;

	public LoginView(){
		login = new LoginForm();
		login.setI18n(createLoginI18n());

		addClassName("login-view");
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");
		H1 title = new H1(I18nProvider.getTranslation("login.title"));
		title.addClassName("login-view-title");
		add(title);

		createCredentialsLayout();

		add(login);
	}

	private LoginI18n createLoginI18n() {
		LoginI18n i18n = LoginI18n.createDefault();

		LoginI18n.Form i18nForm = i18n.getForm();
		i18nForm.setTitle(I18nProvider.getTranslation("login.form.title"));
		i18nForm.setUsername(I18nProvider.getTranslation("login.form.user"));
		i18nForm.setPassword(I18nProvider.getTranslation("login.form.password"));
		i18nForm.setSubmit(I18nProvider.getTranslation("login.form.submit"));
		i18nForm.setForgotPassword(I18nProvider.getTranslation("login.form.forgot"));
		i18n.setForm(i18nForm);

		LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
		i18nErrorMessage.setUsername(I18nProvider.getTranslation("login.form.error.required.username"));
		i18nErrorMessage.setPassword(I18nProvider.getTranslation("login.form.error.required.password"));
		i18nErrorMessage.setTitle(I18nProvider.getTranslation("login.form.error.title"));
		i18nErrorMessage.setMessage(I18nProvider.getTranslation("login.form.error.message"));
		i18n.setErrorMessage(i18nErrorMessage);

		return i18n;
	}

	private void createCredentialsLayout() {

		Html title = new Html("<div>" + I18nProvider.getTranslation("login.credentials.message") + "</div>");

		Div ul = new Div();
		ul.getElement().setProperty("innerHTML", "<ul></ul>");

		ul.getElement().executeJs("this.firstElementChild.innerHTML += '<li>" +  I18nProvider.getTranslation("login.credentials.user") + "</li>'");
		ul.getElement().executeJs("this.firstElementChild.innerHTML += '<li>" +  I18nProvider.getTranslation("login.credentials.admin") + "</li>'");

		add(title, ul);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}


}