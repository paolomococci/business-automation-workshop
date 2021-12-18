package local.example.demo.views.customer.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import local.example.demo.data.entity.Book;
import local.example.demo.data.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

public class BookField extends CustomField<Book> {

    @Autowired
    BookService bookService;

    private final ComboBox<String> book = new ComboBox<>();

    public BookField(String label) {
        setLabel(label);
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
