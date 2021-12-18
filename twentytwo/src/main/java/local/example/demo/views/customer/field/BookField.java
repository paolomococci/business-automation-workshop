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

    public BookField(String label) {
        setLabel(label);
        ComboBox<Book> book = new ComboBox<>();
        book.setPlaceholder("Book");
        book.setRenderer(new TextRenderer<>(Book::getTitle));
        book.setItems(this.bookService.list());
        HorizontalLayout horizontalLayout = new HorizontalLayout(book);
        horizontalLayout.setFlexGrow(1.0, book);
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
