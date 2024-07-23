package org.library.manager.controllers;

import jakarta.validation.Valid;
import org.library.manager.dao.BookDao;
import org.library.manager.dao.BorrowedDao;
import org.library.manager.dao.ReaderDao;
import org.library.manager.entity.Book;
import org.library.manager.entity.Reader;
import org.library.manager.util.ReaderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/readers")
public class ReaderController {
    private BookDao bookDao;
    private ReaderDao readerDao;
    private ReaderValidator readerValidator;
    private BorrowedDao borrowedDao;

    @Autowired
    public ReaderController(ReaderDao readerDao, ReaderValidator readerValidator, BookDao bookDao, BorrowedDao borrowedDao) {
        this.readerDao = readerDao;
        this.readerValidator = readerValidator;
        this.bookDao = bookDao;
        this.borrowedDao = borrowedDao;
    }

    @GetMapping()
    private String showReaders(Model model) {
        model.addAttribute("readers", readerDao.getAllReaders());
        return "readers/readers";
    }

    @GetMapping("/addReader")
    private String showAddReader(Model model) {
        model.addAttribute("reader", new Reader());
        return "readers/addReader";
    }

    @PostMapping("/addReader")
    private String addReader(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult) {
        readerValidator.validate(reader, bindingResult);
        if (bindingResult.hasErrors()) {
            return "readers/addReader";
        }
        readerDao.addNewReader(reader);
        return "redirect:/readers";
    }

    @GetMapping("reader/{id}")
    private String showReader(@PathVariable("id") int id, Model model) {


        List<Book> borrowedBooks = new ArrayList<>();
        borrowedDao.getAllBooksByReaderId(id).stream().forEach(bookId ->
            borrowedBooks.add(bookDao.getBook(bookId))
        );
        model.addAttribute("reader", readerDao.getReader(id));
        model.addAttribute("borrowedBooks", borrowedBooks);
        return "readers/readerPage";
    }

    @GetMapping("edit/{id}")
    private String showEditReader(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerDao.getReader(id));
        return "readers/editReader";
    }

    @PatchMapping("edit/{id}")
    private String editReader(@PathVariable("id") int id, @ModelAttribute Reader reader) {
        readerDao.editReaderData(id, reader);
        return "redirect:/readers";
    }

    @DeleteMapping("delete/{id}")
    private String deleteReader(@PathVariable("id") int id) {
        readerDao.deleteReader(id);
        return "redirect:/readers";
    }

}
