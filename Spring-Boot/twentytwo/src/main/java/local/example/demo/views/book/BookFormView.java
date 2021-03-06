package local.example.demo.views.book;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import local.example.demo.data.entity.Book;
import local.example.demo.data.service.BookService;
import local.example.demo.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Book Form")
@Route(value = "book-form", layout = MainLayout.class)
@RolesAllowed("user")
public class BookFormView extends Div {

    private final TextField title = new TextField("Title");
    private final TextField author = new TextField("Author");
    private final DatePicker publication = new DatePicker("Publication");
    private final IntegerField pages = new IntegerField("Pages");
    private final TextField isbn = new TextField("Isbn");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final Binder<Book> bookBinder = new Binder<>(Book.class);

    public BookFormView(@Autowired BookService bookService) {
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
        save.addClickShortcut(Key.ENTER);
    }

    private Component createTitle() {
        return new H3("Book information fields");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(title, 2);
        formLayout.add(
            author, 
            publication, 
            pages, 
            isbn
        );
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
