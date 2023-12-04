package org.library.library.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.library.library.model.Books;
import org.library.library.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    @Autowired
    private ObjectMapper objectMapper; // afin de traiter la sérialisation et la désérialisation d'objets

    @Test
    public void testAddBook() throws Exception {
        Books book = new Books();
        book.setTitre("Test Book");
        book.setAuteur("Test Author");
        book.setNbr(2);
        book.setIsDisponible(true);
        book.setDatePublication(new Date());

        when(booksService.addBook(book)).thenReturn(book); // si la méthode addBook est appelée avec cet objet book, elle devrait renvoyer objet de type book.

        mockMvc.perform(MockMvcRequestBuilders.post("/livres") // Envoyer une requête HTTP POST à un contrôleur
                        .contentType(MediaType.APPLICATION_JSON) //  en-tête HTTP utilisé
                        .content(objectMapper.writeValueAsString(book))) //body
                .andExpect(status().isOk());// Resultat
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Books book = new Books();
        book.setTitre("Test Book");
        book.setAuteur("Test Author");

        List<Books> booksList = Collections.singletonList(book); // une liste non modifiable contenant un seul élément pour garantir la prévisibilité du comportement simulé

        when(booksService.getAllBooks()).thenReturn(booksList); // si la méthode getAllBooks est appelée, elle devrait renvoyer une liste d'objet de type book.

        mockMvc.perform(MockMvcRequestBuilders.get("/livres")) // Envoyer une requête HTTP GET à un contrôleur
                .andExpect(status().isOk()); // Resultat
    }

    @Test
    public void testGetBookById() throws Exception {
        Books book = new Books();
        book.setId(1L);
        book.setTitre("Test Book");
        book.setAuteur("Test Author");

        when(booksService.getBookById(1L)).thenReturn(book);// si la méthode getBookById est appelée avec identifiant, elle devrait renvoyer un objet de type book.

        mockMvc.perform(MockMvcRequestBuilders.get("/livres/1")) // Envoyer une requête HTTP GET à un contrôleur
                .andExpect(status().isOk()); //Resultat
    }

    @Test
    public void testGetBooksByAuteurOrTitle() throws Exception {
        Books book = new Books();
        book.setTitre("Test Book");
        book.setAuteur("Test Author");

        List<Books> booksList = Collections.singletonList(book); // une liste non modifiable contenant un seul élément pour garantir la prévisibilité du comportement simulé

        when(booksService.getBooksByAuteurOrTitle("Test")).thenReturn(booksList); // si la méthode getBooksByAuteurOrTitle est appelée avec auteur ou titre, elle devrait renvoyer une liste d'objet de type book.


        mockMvc.perform(MockMvcRequestBuilders.get("/livres/search").param("search", "Test")) // Envoyer une requête HTTP GET à un contrôleur
                .andExpect(status().isOk());//Resultat
    }
}

