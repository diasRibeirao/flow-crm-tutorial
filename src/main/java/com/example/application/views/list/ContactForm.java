package com.example.application.views.list;

import com.example.application.I18nProvider;
import com.example.application.data.Company;
import com.example.application.data.Contact;
import com.example.application.data.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.internal.LocaleUtil;
import com.vaadin.flow.shared.Registration;

import java.util.List;
import java.util.Locale;

public class ContactForm extends FormLayout {
  TextField firstName;
  TextField lastName;
  EmailField email;
  ComboBox<Status> status;
  ComboBox<Company> company;

  Button save;
  Button delete;
  Button close;

  Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

  public ContactForm(List<Company> companies, List<Status> statuses) {
    Locale userLocale = null;
    try {
      userLocale = getLocale();
    } catch (Exception e) {
      userLocale = new Locale("pt", "BR");
    }

    configureForm(userLocale);

    addClassName("contact-form");
    binder.bindInstanceFields(this);

    company.setItems(companies);
    company.setItemLabelGenerator(Company::getName);
    status.setItems(statuses);
    status.setItemLabelGenerator(Status::getName);

    add(firstName,
            lastName,
            email,
            company,
            status,
            createButtonsLayout());
  }

  private void configureForm(Locale userLocale) {
    firstName = new TextField(I18nProvider.getTranslation("contact.form.field.firstName", userLocale));
    lastName = new TextField(I18nProvider.getTranslation("contact.form.field.lastName", userLocale));
    email = new EmailField(I18nProvider.getTranslation("contact.form.field.email", userLocale));
    status = new ComboBox<>(I18nProvider.getTranslation("contact.form.field.status", userLocale));
    company = new ComboBox<>(I18nProvider.getTranslation("contact.form.field.company", userLocale));

    save = new Button(I18nProvider.getTranslation("contact.form.button.save", userLocale));
    delete = new Button(I18nProvider.getTranslation("contact.form.button.delete", userLocale));
    close = new Button(I18nProvider.getTranslation("contact.form.button.cancel", userLocale));
  }

  private Component createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
    return new HorizontalLayout(save, delete, close);
  }

  private void validateAndSave() {
    if(binder.isValid()) {
      fireEvent(new SaveEvent(this, binder.getBean()));
    }
  }

  public void setContact(Contact contact) {
    binder.setBean(contact);
  }

  public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
    private Contact contact;

    protected ContactFormEvent(ContactForm source, Contact contact) {
      super(source, false);
      this.contact = contact;
    }

    public Contact getContact() {
      return contact;
    }
  }

  public static class SaveEvent extends ContactFormEvent {
    SaveEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }
  }

  public static class DeleteEvent extends ContactFormEvent {
    DeleteEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }

  }

  public static class CloseEvent extends ContactFormEvent {
    CloseEvent(ContactForm source) {
      super(source, null);
    }
  }

  public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
    return addListener(DeleteEvent.class, listener);
  }

  public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
    return addListener(SaveEvent.class, listener);
  }
  public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
    return addListener(CloseEvent.class, listener);
  }

}
