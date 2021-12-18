package local.example.demo.views.customer.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import local.example.demo.data.entity.Book;
import local.example.demo.data.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

public class BookField extends CustomField<Book> {

    @Autowired
    BookService bookService;

    private final ComboBox<Book> book = new ComboBox<>();

    public BookField(String label) {
        setLabel(label);
        this.book.setPlaceholder("Book");
        this.book.setRenderer(new TextRenderer<>(entity -> entity.getTitle()));
        this.book.setItems(this.bookService.list());
        HorizontalLayout horizontalLayout = new HorizontalLayout(this.book);
        horizontalLayout.setFlexGrow(1.0, this.book);
        add(horizontalLayout);
    }

    @Override
    protected Book generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Book book) {

    }
}
