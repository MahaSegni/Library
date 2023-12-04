package org.library.library.services;

import org.library.library.model.Books;
import org.library.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl implements BooksService {

    private final BookRepository bookRepository;

    @Autowired
    public BooksServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Books addBook(Books book) {
        try {
            bookRepository.save(book); // ajouter un livre dans la base
            return book;
        } catch (Exception e) {
            throw new RuntimeException("Failed to add book"); // s'il y a une exception ce message sera afficher
        }
    }

    @Override
    public List<Books> getAllBooks() {
        try {
            return bookRepository.findAll(); // recuperer la liste complete des livres
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all books");// s'il y a une exception ce message sera afficher
        }
    }

    @Override
    public Books getBookById(Long id) {
        try {
            return bookRepository.getById(id); // recuperer libre selon id
        } catch (Exception e) {
            throw new RuntimeException("Book not found"); // s'il y a une exception ce message sera afficher
        }
    }

    @Override
    public List<Books> getBooksByAuteurOrTitle(String search) {
        try {
            return bookRepository.findBooksByAuteurOrTitre(search); // recuperer la liste des livres selon le nom d'auteur ou le titre
        } catch (Exception e) {
            throw new RuntimeException("Books not found"); // s'il y a une exception ce message sera afficher
        }
    }
}
