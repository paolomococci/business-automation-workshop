package local.example.demo.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import local.example.demo.data.AbstractEntity;

@Entity
public class Book extends AbstractEntity {

    private String title;
    private String author;
    private LocalDate publication;
    private Integer pages;
    private String isbn;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public LocalDate getPublication() {
        return publication;
    }
    public void setPublication(LocalDate publication) {
        this.publication = publication;
    }
    public Integer getPages() {
        return pages;
    }
    public void setPages(Integer pages) {
        this.pages = pages;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
