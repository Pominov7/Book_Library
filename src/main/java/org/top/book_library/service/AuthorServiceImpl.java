package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.repository.AuthorRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    // Получить автора по id
    @Override
    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    // получить список всех авторов
    @Override
    public List<Author> listAllAuthors() {
        return authorRepository.findAll().stream()
                .sorted(Comparator.comparing(Author::getLastName)) // сортировка по умолчанию
                .collect(Collectors.toList());
    }

    // сохранить автора
    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // редактировать поля автора
    @Override
    public void updateAuthor(Author author) {
        Optional<Author> optionalAuthor = getById(author.getId());
        if (optionalAuthor.isPresent()) {
            Author editedAuthor = optionalAuthor.get();
            if (!editedAuthor.equals(author)) {
                editedAuthor.setName(author.getName());
                editedAuthor.setLastName(author.getLastName());
                authorRepository.save(editedAuthor);
            }
        }
    }

    // удалить автора по id
    @Override
    public void deleteAuthorByID(Long id) {
        Optional<Author> result = authorRepository.findById(id);
        result.ifPresent(authorRepository::delete);
    }

    // найти автора по содержимому строки
    @Override
    public List<Author> findByContainsNameAuthor(String match) {
        if (match == null || match.equals(""))
            return authorRepository.findAll();
        return authorRepository.findAll()
                .stream()
                .filter(s -> s.getName().toLowerCase().contains(match.toLowerCase()) ||
                        s.getLastName().toLowerCase().contains(match.toLowerCase()))          //игнорируем регистр
                .toList();
    }


    // нумерация страниц(пагинация)
    @Override
    public Page<Author> findPaginated(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo-1,size);
        return authorRepository.findAll(pageable);
    }

}

