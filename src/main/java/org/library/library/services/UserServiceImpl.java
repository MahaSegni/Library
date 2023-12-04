package org.library.library.services;
import org.library.library.model.User;
import org.library.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        // Vérifier si l'email déjà existant
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already used");
        }

        // Hacher le mot de passe
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        // Mettre à jour dans la base
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email); // Recherche utilisateur par email
        // verifier le mot de passe
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return user;
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        // Verifier et récupere utilisateur sinon return exception
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Mettre à jour email et nom
        if (updatedUser.getNom() != null){
            existingUser.setNom(updatedUser.getNom());
        }
        if(updatedUser.getEmail()!= null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        // Enregistrer les modifications dans la base de données
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        // Supprimer l'utilisateur par identifiant
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")); // Chercher utilisateur par identifiant, s'il n'y a pas retourne exception
    }

    // Méthode pour hashé le mot de passe
    private static String hashPassword(String motDePasse) {
        try {
            // Créer une instance avec l'algorithme SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Calculer le hash du mot de passe
            byte[] hash = digest.digest(motDePasse.getBytes(StandardCharsets.UTF_8));

            // Convertir le tableau de bytes en une représentation hexadécimale
            StringBuilder chHexadecimale = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    chHexadecimale.append('0');
                }
                chHexadecimale.append(hex);
            }
            // Retourner le mot de passe hashé
            return chHexadecimale.toString();
        } catch (NoSuchAlgorithmException e) {
            // Exception si cmot de passe n'est pas haché
            throw new RuntimeException("Erreur lors du hashage du mot de passe");
        }
    }

}
