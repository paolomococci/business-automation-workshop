package local.example.demo.views.book;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import local.example.demo.data.entity.Book;
import local.example.demo.data.service.BookService;
import local.example.demo.views.MainLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Books Detail")
@Route(value = "books-detail/:bookID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class BooksDetailView extends Div implements BeforeEnterObserver {

    @Autowired
    BookService bookService;

    private final String BOOK_EDIT_ROUTE_TEMPLATE = "books-detail/%d/edit";

    private final Grid<Book> bookGrid = new Grid<>(Book.class, false);

    private TextField title;
    private TextField author;
    private DatePicker publication;
    private TextField pages;
    private TextField isbn;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Book> bookBinder;

    private Book book;

    public BooksDetailView() {
        addClassNames(
            "books-detail-view", 
            "flex", 
            "flex-col", 
            "h-full"
        );

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        this.bookGrid.addColumn(Book::getTitle).setAutoWidth(true);
        this.bookGrid.addColumn(Book::getAuthor).setAutoWidth(true);
        this.bookGrid.addColumn(Book::getPublication).setAutoWidth(true);
        this.bookGrid.addColumn(Book::getPages).setAutoWidth(true);
        this.bookGrid.addColumn(Book::getIsbn).setAutoWidth(true);

        this.bookGrid.setItems(query -> this.bookService.list(
                PageRequest.of(
                    query.getPage(), 
                    query.getPageSize(), 
                    VaadinSpringDataHelpers.toSpringDataSort(query)
                ))
                .stream());

        this.bookGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.bookGrid.setHeightFull();

        this.bookGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(
                    String.format(BOOK_EDIT_ROUTE_TEMPLATE, event.getValue().getId())
                );
            } else {
                clearForm();
                UI.getCurrent().navigate(BooksDetailView.class);
            }
        });

        this.bookBinder = new BeanValidationBinder<>(Book.class);

        this.bookBinder.forField(this.pages).withConverter(
            new StringToIntegerConverter("Only numbers are allowed")
        ).bind("pages");

        this.bookBinder.bindInstanceFields(this);

        this.cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        this.save.addClickListener(e -> {
            try {
                if (this.book == null) {
                    this.book = new Book();
                }
                this.bookBinder.writeBean(this.book);

                this.bookService.update(this.book);
                clearForm();
                refreshGrid();
                Notification.show("Book details stored!");
                UI.getCurrent().navigate(BooksDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the book details!");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String BOOK_ID = "bookID";
        Optional<Integer> bookId = event.getRouteParameters().getInteger(BOOK_ID);
        if (bookId.isPresent()) {
            Optional<Book> bookFromBackend = bookService.get(bookId.get());
            if (bookFromBackend.isPresent()) {
                populateForm(bookFromBackend.get());
            } else {
                Notification.show(String.format(
                    "The requested book was not found, ID = %d", 
                    bookId.get()), 
                    3000, 
                    Notification.Position.BOTTOM_START
                );
                refreshGrid();
                event.forwardTo(BooksDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        this.title = new TextField("Title");
        this.author = new TextField("Author");
        this.publication = new DatePicker("Publication");
        this.pages = new TextField("Pages");
        this.isbn = new TextField("Isbn");
        Component[] fields = new Component[] {
                this.title,
                this.author,
                this.publication,
                this.pages,
                this.isbn
        };

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName(
            "w-full flex-wrap bg-contrast-5 py-s px-l"
        );
        buttonLayout.setSpacing(true);

        this.cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonLayout.add(
                this.save,
                this.cancel
        );
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(this.bookGrid);
    }

    private void refreshGrid() {
        this.bookGrid.select(null);
        this.bookGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Book value) {
        this.book = value;
        this.bookBinder.readBean(this.book);
    }
}
