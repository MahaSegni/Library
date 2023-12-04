package org.library.library.services;

import org.library.library.model.Emprunt;

import java.util.List;

public interface EmpruntService {
    List<Emprunt> getEmpruntsByUserId(Long userId); // recuperer liste des emprunts selon identifiant de l'utilisateur
    Emprunt emprunterLivre(Long userId, Long livreId); // emprunter un livre
}
