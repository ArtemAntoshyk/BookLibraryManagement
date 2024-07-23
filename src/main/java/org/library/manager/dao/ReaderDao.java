package org.library.manager.dao;

import org.library.manager.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReaderDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //ADD
    //addNewReader
    public void addNewReader(Reader reader) {
        jdbcTemplate.update("INSERT INTO reader(fullname, phonenumber, email, address) VALUES (?,?,?,?)",
                reader.getFullName(), reader.getPhoneNumber(), reader.getEmail(), reader.getAddress());
    }

    //EDIT
    //editReaderData
    public void editReaderData(int id, Reader updatedReader) {
        jdbcTemplate.update("UPDATE reader SET fullname=?, phonenumber=?, email=?, address=? WHERE id=?",
                updatedReader.getFullName(),
                updatedReader.getPhoneNumber(),
                updatedReader.getEmail(),
                updatedReader.getAddress(),
                id);
    }

    //REMOVE
    //removeReader
    public void deleteReader(int id) {
        jdbcTemplate.update("DELETE FROM reader WHERE id=?", id);
    }

    //GET
    //getAllReader
    public List<Reader> getAllReaders() {
        return jdbcTemplate.query("SELECT * FROM reader", new BeanPropertyRowMapper<>(Reader.class));
    }

    //getReaderByName
    public Reader getReader(String phoneNumber) {
//        return jdbcTemplate.queryForObject("SELECT * FROM reader WHERE id=?", new BeanPropertyRowMapper<>(Reader.class), id);
        return jdbcTemplate.query("SELECT * FROM reader WHERE phonenumber=?",
                        new Object[]{phoneNumber},
                        new BeanPropertyRowMapper<>(Reader.class))
                .stream().findAny().orElse(null);
    }

    //getReaderById
    public Reader getReader(int id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM reader WHERE id=?", new BeanPropertyRowMapper<>(Reader.class), id);
        return jdbcTemplate.query("SELECT * FROM reader WHERE id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Reader.class))
                .stream().findAny().orElse(null);
    }
}
