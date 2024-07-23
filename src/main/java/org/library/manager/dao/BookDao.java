package org.library.manager.dao;

import org.library.manager.entity.Book;
import org.library.manager.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, description, genre, publisher, language, year) VALUES (?,?,?,?,?,?,?)",
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getGenre(),
                book.getPublisher(),
                book.getLanguage(),
                book.getYear()
        );
    }

    public void removeBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book where id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)
        ).stream().findFirst().orElse(null);
    }

    public Book getBook(String title) {
        return jdbcTemplate.query("SELECT * FROM book where title = ?",
                new Object[]{title},
                new BeanPropertyRowMapper<>(Book.class)
        ).stream().findFirst().orElse(null);
    }
    public void editBookData(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET title=?, year=?, author=?, publisher=?, genre=?, description=? WHERE id=?",
                updatedBook.getTitle(),
                updatedBook.getYear(),
                updatedBook.getAuthor(),
                updatedBook.getPublisher(),
                updatedBook.getGenre(),
                updatedBook.getDescription(),
                id);
    }

}
