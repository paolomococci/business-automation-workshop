package local.example.demo.data.entity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import local.example.demo.data.AbstractEntity;

@Entity
public class Book extends AbstractEntity {

    private String title;
    private String author;
    private LocalDate publication;
    private Integer pages;
    private String isbn;
    private Cost cost;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_customer",
        joinColumns = { @JoinColumn(name = "book_id") },
        inverseJoinColumns = { @JoinColumn(name = "customer_id") }
    )
    private List<Customer> bookCustomer = new LinkedList<>();

    public List<Customer> getCustomers() {
        return bookCustomer;
    }

    public void setCustomers(List<Customer> bookCustomer) {
        this.bookCustomer = bookCustomer;
    }

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

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public List<Customer> getBookCustomer() {
        return bookCustomer;
    }

    public void setBookCustomer(List<Customer> bookCustomer) {
        this.bookCustomer = bookCustomer;
    }
}
