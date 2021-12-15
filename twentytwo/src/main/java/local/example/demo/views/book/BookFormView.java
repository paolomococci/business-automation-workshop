package local.example.demo.views.book;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import local.example.demo.data.entity.Book;
import local.example.demo.data.service.BookService;
import local.example.demo.views.MainLayout;

@PageTitle("Book Form")
@Route(value = "book-form", layout = MainLayout.class)
@RolesAllowed("user")
public class BookFormView extends Div {

    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private DatePicker publication = new DatePicker("Publication");
    private TextField pages = new TextField("Pages");
    private TextField isbn = new TextField("Isbn");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Book> bookBinder = new Binder<>(Book.class);

    public BookFormView(BookService bookService) {
        addClassName("book-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        bookBinder.bindInstanceFields(this);

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            bookService.update(bookBinder.getBean());
            Notification.show(bookBinder.getBean().getClass().getSimpleName() + " stored!");
            clearForm();
        });
    }

    private Component createTitle() {
        return new H3("Book");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(title, 2);
        formLayout.add(author, publication, pages, isbn);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private void clearForm() {
        this.bookBinder.setBean(new Book());
    }
}
