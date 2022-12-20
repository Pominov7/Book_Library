package org.top.book_library.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.top.book_library.db.entity.*;
import org.top.book_library.db.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BookServiceImplTest {

    // mocked dependencies
    // @InjectMocks создает экземпляр класса и внедряет в него моки, помеченные аннотациями @Mock.
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    private static Book book;
    private static Author author;
    private static Genre genre;
    private static Link link;
    private static Cover cover;
//    private static Comment comment;

    // вызывается для каждого экземпляра теста
    @BeforeEach
    public void prepareTestData() {
        author = new Author(1L, "Test_Name", "Test_lastName");
        genre = new Genre(1L, "Test_genre");
        link = new Link(1L, "Test_link", "https://");
        cover = new Cover(1L, "Test_cover", "/img/...");
        book = new Book(1L, "Test_Book", "Description", author, genre, link, cover);
    }

    // проверка на существование книги с определенным ID
    @Test
    @DisplayName("Test GetById Success")
    void testGetById() {
        //  определение поведения с использованием doReturn
        doReturn(Optional.of(book)).when(bookRepository).findById(1L);

        // Вызываем сервис
        Optional<Book> returnedBook = bookService.getById(1L);

        // Получаем ответ
        Assertions.assertTrue(returnedBook.isPresent(), "Book was not found");
        Assertions.assertSame(returnedBook.get(), book, "The book returned was not the same as the mock");
        Assertions.assertEquals(book.getTitle(), returnedBook.get().getTitle());

    }

    // обратная проверка, когда автора не существует

    @Test
    @DisplayName("Test GetById Not Found")
    void testGetByIdNotFound() {
        //  определение поведения с использованием doReturn
        doReturn(Optional.empty()).when(bookRepository).findById(1L); //сравниваем пустую книгу с ID=1

        // Вызываем сервис
        Optional<Book> returnedBook = bookService.getById(1L);

        // Проверяем что книга == null
        Assertions.assertFalse(returnedBook.isPresent(), "Book should not be found");
    }


    // проверка на получение списка книг
    @Test
    @DisplayName("Test findAll")
    void testListAllBooks() {
        // создаём все субсущности, которые входят в сущность book
        Author author_2 = new Author(1L, "Test_Name_2", "Test_lastName_2");
        Genre genre_2 = new Genre(1L, "Test_genre_2");
        Link link_2 = new Link(1L, "Test_link_2", "https://_2");
        Cover cover_2 = new Cover(1L, "Test_cover_2", "/img/..._2");
        // создаём еще одну книгу
        Book book_2 = new Book(1L, "Test_Book_2", "Description_2", author_2, genre_2, link_2, cover_2);

        // сценарий с использованием doReturn
        doReturn(Arrays.asList(book, book_2)).when(bookRepository).findAll();

        // создаём список с авторами
        List<Book> books = bookService.listAllBooks();

        // проверяем содержит ли список двух авторов
        Assertions.assertEquals(2, books.size(), "findAll should return 2 authors");
    }

    // проверка на сохранение и обновление книги
    @Test
    @DisplayName("Test save or update book")
    void testSaveOrUpdateBook() {
        // создаём новую книгу
        Book authorUpdate = new Book(1L, "Update_Book", "Update_Description", author, genre, link, cover);
        // сценарий с использованием when
        when(bookRepository.save(any(Book.class))).thenReturn(authorUpdate);
        // вызываем метод сохранения
        book = bookRepository.save(authorUpdate);
        assertThat(book.getTitle()).isNotNull();
        // проверяем поля обновленной книги
        Assertions.assertEquals(authorUpdate.getTitle(), book.getTitle());
    }


    // проверка на удаление книги
    @Test
    @DisplayName("Test delete book")
    void deleteBookByID() {
        BookService authorService = mock(BookService.class);
        // сценарий с использованием doNothing
        doNothing().when(authorService).deleteBookByID(1L);
        // вызываем метод удаления
        authorService.deleteBookByID(1L);
        Optional<Book> returnedBook = bookService.getById(1L);
        Assertions.assertTrue(true);
        Assertions.assertTrue(returnedBook.isEmpty(), "The book must be null");

    }

}