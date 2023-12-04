package org.library.library.controlleur;

import org.library.library.model.Books;
import org.library.library.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livres")
public class BooksController {

    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping
    public Books addBook(@RequestBody Books book) {
        try {
            return booksService.addBook(book); // ajouter un livre
        } catch (Exception e) {
            throw new RuntimeException("Failed to add book");
        }
    }

    @GetMapping
    public List<Books> getAllBooks() {

            return booksService.getAllBooks(); // récuprer liste des livres

    }

    @GetMapping("/{id}")
    public Books getBookById(@PathVariable Long id) {

            return booksService.getBookById(id); // récuperer livre selon identifiant

    }

    @GetMapping("/search")
    public List<Books> getBooksByAuteurOrTitle(@RequestParam String search) {

            return booksService.getBooksByAuteurOrTitle(search); // récupérer listes des livres selon auteur ou titre

    }
}
