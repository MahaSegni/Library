package org.library.library.repositories;

import org.library.library.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    List<Books> findAll();
    @Query("SELECT b FROM Books b WHERE LOWER(b.auteur) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(b.titre) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Books> findBooksByAuteurOrTitre(String search); // requete sql pour recherche selon auteur ou titre

    @Query("SELECT b FROM Books b WHERE b.id = :id ")
    Books getById(Long id); // recuperer book by identifiant
}
