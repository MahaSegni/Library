package org.library.library.services;

import org.library.library.model.User;

public interface UserService {
    User registerUser(User user); // ajouter utilisateur
    User loginUser(String email, String password); // se connecter
    User updateUser(Long userId, User updatedUser); // mettre a jour utilisateur
    void deleteUser(Long userId); // supprimer un utilisateur
    User getUserById(Long userId); // récuperer utilisateur par id
}
