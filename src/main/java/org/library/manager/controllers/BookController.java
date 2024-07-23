package org.library.manager.controllers;

import jakarta.validation.Valid;
import org.library.manager.dao.BookDao;
import org.library.manager.dao.BorrowedDao;
import org.library.manager.dao.ReaderDao;
import org.library.manager.entity.Book;


import org.library.manager.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDao bookDao;
    private ReaderDao readerDao;
    private BorrowedDao borrowedDao;

    @Autowired
    public BookController(BookDao bookDao, ReaderDao readerDao, BorrowedDao borrowedDao) {
        this.bookDao = bookDao;
        this.readerDao = readerDao;
        this.borrowedDao = borrowedDao;
    }

    @GetMapping()
    private String showBooks(Model model) {
        model.addAttribute("books", bookDao.getAllBooks());
        return "books/books";
    }

    @GetMapping("/addBook")
    private String showAddBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/addBook";
    }

    @PostMapping("/addBook")
    private String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "books/addBook";
        }
        bookDao.addNewBook(book);
        return "redirect:/books";
    }

    @GetMapping("book/{id}")
    private String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("readers", readerDao.getAllReaders());
        model.addAttribute("book", bookDao.getBook(id));
        Reader reader = readerDao.getReader(borrowedDao.getReaderIdByBookId(id));
        model.addAttribute("readerWhichBorrow", reader);
        return "books/bookPage";
    }

    @PatchMapping("returnBook/{id}")
    private String returnBook(@PathVariable("id") int id){
        borrowedDao.returnBook(id);
        return "redirect:/books/book/" + id;
    }
    @PatchMapping("/borrowBook/{id}")
    public String borrow(@PathVariable("id") int id, @RequestParam("reader") int readerId) {
        System.out.println(readerId);
        borrowedDao.borrowBook(readerId, id);
        return "redirect:/books/book/" + id;
    }

    @GetMapping("edit/{id}")
    private String showEditBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getBook(id));
        return "books/editBook";
    }

    @PatchMapping("edit/{id}")
    private String editBook(@PathVariable("id") int id, @ModelAttribute Book book) {
        bookDao.editBookData(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("delete/{id}")
    private String deleteBook(@PathVariable("id") int id) {
        bookDao.removeBook(id);
        return "redirect:/books";
    }
}
