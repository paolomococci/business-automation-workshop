package local.example.demo.views.customer.field;

import com.vaadin.flow.component.customfield.CustomField;
import local.example.demo.data.entity.Book;
import local.example.demo.data.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

public class BookField extends CustomField<Book> {

    @Autowired
    BookService bookService;

    public BookField(String label) {
        setLabel(label);
    }

    @Override
    protected Book generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Book book) {

    }
}
