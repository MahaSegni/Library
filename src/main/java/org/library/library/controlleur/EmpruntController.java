package org.library.library.controlleur;

import org.library.library.model.Emprunt;
import org.library.library.services.EmpruntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;

    @Autowired
    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    @GetMapping("/emprunt/{userId}")
    public List<Emprunt> getEmpruntsByUserId(@PathVariable Long userId) { // récuperer liste des emprunts selon identifiant de l'utilisateur
        return empruntService.getEmpruntsByUserId(userId);
    }

    @PostMapping("/emprunter/{userId}/{livreId}")
    public Emprunt emprunterLivre(@PathVariable Long userId, @PathVariable Long livreId) { // emprunter un livre
        return empruntService.emprunterLivre(userId, livreId);
    }
}
