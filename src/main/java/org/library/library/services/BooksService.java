package org.library.library.services;

import org.library.library.model.Books;

import java.awt.print.Book;
import java.util.List;

public interface BooksService {
    Books addBook(Books book); // Ajout d'un nouveau livre
    List<Books> getAllBooks(); // récuperer tous les livres

    Books getBookById(Long id); // récuperer livre selon identifiant

    List<Books> getBooksByAuteurOrTitle(String auteur); // récuperer liste des livres selon Auteur ou Titre du livre



}
