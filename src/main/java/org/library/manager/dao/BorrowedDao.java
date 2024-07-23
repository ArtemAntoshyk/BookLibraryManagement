package org.library.manager.dao;
import org.library.manager.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BorrowedDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BorrowedDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void borrowBook(int readerId, int bookId) {
        jdbcTemplate.update("INSERT INTO readerAndTheirTable(readerid, bookid) VALUES(?,?)", readerId, bookId);
    }

    public void returnBook(int bookId) {
        jdbcTemplate.update("DELETE FROM readerAndTheirTable WHERE bookId = ?", bookId);
    }
    public List<Integer> getAllBooksByReaderId(int readerId) {
        return jdbcTemplate.queryForList("SELECT bookid FROM readerAndTheirTable WHERE readerId = ?",
                Integer.class, readerId
                );
    }

    public int getReaderIdByBookId(int bookId) {
        return jdbcTemplate.queryForList("SELECT readerId FROM readerandtheirtable WHERE bookId = ?",
                Integer.class, bookId).stream().findFirst().orElse(-1);
    }


}
