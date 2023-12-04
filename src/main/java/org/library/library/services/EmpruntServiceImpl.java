package org.library.library.services;

import org.library.library.model.Books;
import org.library.library.model.Emprunt;
import org.library.library.model.User;
import org.library.library.repositories.BookRepository;
import org.library.library.repositories.EmpruntRepository;
import org.library.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmpruntServiceImpl implements EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public EmpruntServiceImpl(EmpruntRepository empruntRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.empruntRepository = empruntRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Emprunt> getEmpruntsByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId); // recuperer utilisateur selon identifiant
        if (optionalUser.isPresent()) { // si la liste n'est pas vide
            User user = optionalUser.get(); // recuperer l'utilisateur
            return empruntRepository.findByUser(user); // recuperer list emprunt selon utilisateur
        } else {
            throw new RuntimeException("User not found"); // s'il y a une exception ce message sera afficher
        }
    }

    @Override
    public Emprunt emprunterLivre(Long userId, Long bookId) {
        // Vérifier si l'utilisateur existe
        Optional<User> optionalUser = userRepository.findById(userId); // récuperer utilisateur selon identifiant
        if (!optionalUser.isPresent()) { // si la liste est vide cette exception sera afficher avec le message
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        // Vérifier si le livre existe
        Optional<Books> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            throw new IllegalArgumentException("Livre non trouvé avec l'ID : " + bookId);
        }

        User user = optionalUser.get();
        Books book = optionalBook.get();

        // Vérifier si la durée maximale d'emprunt est définie pour l'utilisateur null
        if (user.getDureeMaxEmprunt() == null) {
            throw new IllegalStateException("La durée maximale d'emprunt n'est pas définie pour l'utilisateur avec l'ID : " + userId);
        }

        // Vérifier si le livre n'est pas disponible  afficher l'exception
        if (!book.getIsDisponible()) {
            throw new IllegalStateException("Le livre avec l'ID : " + bookId + " n'est pas disponible pour l'emprunt.");
        }

        // Créer un nouvel emprunt
        Emprunt emprunt = new Emprunt();
        emprunt.setUser(user);
        emprunt.setBooks(book);
        emprunt.setDateEmprunt(new Date()); // Date d'emprunt actuelle

        // Calculer la date de retour prévue en utilisant la durée maximale d'emprunt de l'utilisateur
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, user.getDureeMaxEmprunt().intValue());
        emprunt.setDateRetourPrevue(calendar.getTime());

        // Mettre à jour le nombre de livres disponibles
        int nbrDisponible = book.getNbr();
        if (nbrDisponible > 0) {
            book.setNbr(nbrDisponible - 1);
            if (book.getNbr() == 0) {// Verifier si le nombre est 0 la disponibilité change en false
                book.setIsDisponible(false);
            }
        } else {
            throw new IllegalStateException("Le livre avec l'ID : " + bookId + " n'est pas disponible pour l'emprunt.");
        }
        user.getEmpruntList().add(emprunt); // ajouter emprunt a utilisateur
        // Enregistrer l'emprunt et mettre à jour le livre dans la base de données
        empruntRepository.save(emprunt);
        userRepository.save(user);
        bookRepository.save(book);
        return emprunt;
    }

}
