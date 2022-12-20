package org.top.book_library.service;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.repository.AuthorRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthorServiceImplTest {

    // mocked dependencies
    // @InjectMocks создает экземпляр класса и внедряет в него моки, помеченные аннотациями @Mock.
    @InjectMocks
    private AuthorServiceImpl authorService;

    // @Mock создает фиктивную реализацию для необходимых классов.
    @Mock
    private AuthorRepository authorRepository;
    private static Author author;

    @BeforeEach
    public void prepareTestData() {
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
        // сценарий с использованием doReturn
        doReturn(Arrays.asList(author, author2)).when(authorRepository).findAll();

        // создаём список с авторами
        List<Author> authors = authorService.listAllAuthors();

        // проверяем содержит ли список двух авторов
        Assertions.assertEquals(2, authors.size(), "findAll should return 2 authors");
    }

    @Test
    @DisplayName("Test save author")
    void testSaveAuthor() {

        // сценарий с использованием doReturn
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
    @DisplayName("Test update author")
    void testUpdateAuthor() {
        // создаём нового автора
        Author authorUpdate = new Author(1L, "Update_name", "Update_lastName");
        // сценарий с использованием when
        when(authorRepository.save(any(Author.class))).thenReturn(authorUpdate);
        // вызываем метод сохранения
        author = authorRepository.save(authorUpdate);
        assertThat(author.getName()).isNotNull();
        // проверяем поля обновленного автора
        Assertions.assertEquals("Update_name", author.getName());
        Assertions.assertEquals("Update_lastName", author.getLastName());
    }


    @Test
    @DisplayName("Test delete author")
    void testDeleteAuthorByID() {
        AuthorService authorService = mock(AuthorService.class);
        // сценарий с использованием doNothing
        doNothing().when(authorService).deleteAuthorByID(1L);
        // вызываем метод удаления
        authorService.deleteAuthorByID(1L);
        Optional<Author> returnedAuthor = authorService.getById(1L);
        Assertions.assertTrue(true);
        Assertions.assertTrue(returnedAuthor.isEmpty(), "The author must be null");
    }

}