package org.library.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
// on peut utiliser @Data aussi au lieu de @Getter @Setter ...
public class Books {


    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE) // afin de spécifier le champ datePublication [ type date ] soit etre mappé dans la base, il sera persisté en utilisant seulemennt la partie date
    private Date datePublication;

    private String titre;

    private String auteur;

    private int nbr;

    private Boolean isDisponible;


}
