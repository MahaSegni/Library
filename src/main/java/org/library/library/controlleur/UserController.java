package org.library.library.controlleur;

import org.library.library.model.User;
import org.library.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
// chaque fonction fait appele a service
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) { // ajouter utilisateur
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestParam String email, @RequestParam String password) { // se connecter
        return userService.loginUser(email, password);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) { // mettre à jour un utilisateur
        return userService.updateUser(userId, updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) { // supprimer un utilisateur
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) { // récuperer utilisateur par identifiant
        return userService.getUserById(userId);
    }
}

