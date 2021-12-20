package local.example.demo.data.service;

import local.example.demo.data.entity.Book;
import local.example.demo.data.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Stream<Book> stream() {
        try {
            return bookRepository.findAll().stream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }

    public Page<Book> pageable(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public int count() {
        return (int) bookRepository.count();
    }
}
