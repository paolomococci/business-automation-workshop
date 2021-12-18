package local.example.demo.data.service;

import java.util.List;
import java.util.Optional;
import local.example.demo.data.entity.Book;
import local.example.demo.data.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Optional<Book> get(Integer id) {
        return bookRepository.findById(id);
    }

    public Book update(Book entity) {
        return bookRepository.save(entity);
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public Page<Book> list(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public int count() {
        return (int) bookRepository.count();
    }
}
