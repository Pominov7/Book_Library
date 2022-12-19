package org.top.book_library.service;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.repository.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorServiceImplTest {

    // mocked dependencies
    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    private static Author author;

    @BeforeAll
    public static void prepareTestData() {
        author = new Author(1L, "Author_Name_Test", "Author_LastName_Test");
    }

    // проверка на существование автора с определенным ID
    @Test
    @DisplayName("Test findById Success")
    void testGetById() {

        //  определение поведения с использованием doReturn
        doReturn(Optional.of(author)).when(authorRepository).findById(1L);

        // Вызываем сервис
        Optional<Author> returnedAuthor = authorService.getById(1L);

        // Получаем ответ
        Assertions.assertTrue(returnedAuthor.isPresent(), "Author was not found");
        Assertions.assertSame(returnedAuthor.get(), author, "The author returned was not the same as the mock");

    }

    // обратная проверка, когда автора не существует
    @Test
    @DisplayName("Test findById Not Found")
    void testGetByIdNotFound() {
        //  определение поведения с использованием doReturn
        doReturn(Optional.empty()).when(authorRepository).findById(1L); //сравниваем пустого автора с ID=1

        // Вызываем сервис
        Optional<Author> returnedAuthor = authorService.getById(1L);

        // Проверяем что автор не равен null
        Assertions.assertFalse(returnedAuthor.isPresent(), "Author should not be found");
    }

    @Test
    @DisplayName("Test findAll")
    void testListAllAuthors() {

        // создаём еще одного автора
        Author author2 = new Author(2L, "Author_Name2", "Author_LastName_2");
        // определение поведения с использованием doReturn
        doReturn(Arrays.asList(author, author2)).when(authorRepository).findAll();

        // создаём список с авторами
        List<Author> authors = authorService.listAllAuthors();

        // проверяем содержит ли список двух авторов
        Assertions.assertEquals(2, authors.size(), "findAll should return 2 authors");
    }

    @Test
    @DisplayName("Test save author")
    void testSaveAuthor() {
        //  Устанавливаем mock authorRepository
        doReturn(author).when(authorRepository).save(any());

        // вызываем метод сохранения
        Author returnedAuthor = authorService.saveAuthor(author);

        // проверяем что автор не равен null
        Assertions.assertNotNull(returnedAuthor, "The saved author should not be null");
        // проверяем что имя сохраненного автора "Author_Name_New"
        Assertions.assertEquals("Author_Name_Test", returnedAuthor.getName(),
                "The name author should be Author_Name_Test");
    }

    @Test
    void updateAuthor() {

        Author authorUpdate = new Author(1L, "Name_Update", "LastName_Update");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(authorUpdate);
        authorService.updateAuthor(authorUpdate);
        Assertions.assertEquals("Name_Update", author.getName());
        Assertions.assertEquals("LastName_Update", author.getLastName());
    }

    @Test
    @DisplayName("Test delete author")
    void deleteAuthorByID() {
        Author author2 = new Author(2L, "Author_Name2", "Author_LastName_2");
        // определение поведения с использованием doReturn
        doReturn(Arrays.asList(author, author2)).when(authorRepository).findAll();
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).delete(author);
        // создаём список с авторами
        List<Author> authors = authorService.listAllAuthors();

        authorService.deleteAuthorByID(1L);
        // Вызываем сервис
        Assertions.assertEquals(1, authors.size(), "findAll should return 1 author");

    }

    @Test
    void findByContainsNameAuthor() {
    }

}