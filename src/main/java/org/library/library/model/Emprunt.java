package org.library.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Emprunt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Emprunt {

    @Id // pour scécifier sue c'est une clé primaire  de l'entité
    @GeneratedValue// pour que la valeur sera générée par la base lors d'un insert
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books books;

    @ManyToOne
    @JoinColumn(name = "user_id" )
    private User user;

    @Temporal(TemporalType.DATE) // afin de spécifier le champ emprunt [ type date ] soit etre mappé dans la base, il sera persisté en utilisant seulemennt la partie date
    private Date dateEmprunt;

    @Temporal(TemporalType.DATE) // afin de spécifier le champ emprunt [ type date ] soit etre mappé dans la base, il sera persisté en utilisant seulemennt la partie date
    private Date dateRetourPrevue;
}