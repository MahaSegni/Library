package org.library.library.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.library.library.model.User;
import org.library.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest { // class de test pour utilisateur

    @Autowired
    private MockMvc mockMvc; // class de test des controlleurs

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User(); // initialiser un utilisateur
        user.setNom("test");
        user.setEmail("test@gmail.com");
        user.setPassword("string");
        user.setDureeMaxEmprunt(7L);
        user.setLimiteEmprunts(2);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register") // Envoyer une requête HTTP POST à un contrôleur
                        .contentType(MediaType.APPLICATION_JSON) //  en-tête HTTP utilisé
                        .content(objectMapper.writeValueAsString(user))) // body
                .andExpect(status().isOk()); // resultat
    }

    @Test
    public void testLoginUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")// Envoyer une requête HTTP POST à un contrôleur
                        .param("email", "test@gmail.com") // paramètres de requête
                        .param("password", "password")) // paramètres de requête
                .andExpect(status().isOk()); // resultat
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setNom("UpdatedName");

        mockMvc.perform(MockMvcRequestBuilders.put("/user/1") // Envoyer une requête HTTP PUT à un contrôleur
                        .contentType(MediaType.APPLICATION_JSON) //  en-tête HTTP utilisé
                        .content(objectMapper.writeValueAsString(updatedUser)))// body
                .andExpect(status().isOk()); // resultat
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))// Envoyer une requête HTTP DELETE à un contrôleur
                .andExpect(status().isOk()); //Resultat
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))// Envoyer une requête HTTP GET à un contrôleur
                .andExpect(status().isOk()); // Resultat
    }
}
