package org.library.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue// pour que la valeur sera générée par la base lors d'un insert
    private Long id;

    private String nom;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user" )
    @JsonIgnore // pour ne pas afficher les détails de entité emprunt lors de l'ajout d'un User
    private List<Emprunt> empruntList;

    private int limiteEmprunts;

    private Long dureeMaxEmprunt;


}