package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.entity.Link;
import org.top.book_library.db.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    // Найти автора по имени
    @Override
    public Author findByName(String authorName) {
        return authorRepository.findByName(authorName).orElse(null);
    }

    // Получение автора по id
    @Override
    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    // получить список всех авторов
    @Override
    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    // сохранить автора
    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // изменить поля автора
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

    // Найти автора по введенной строке
    @Override
    public List<Author> findByContainsNameAuthor(String match) {
        if (match == null || match.equals(""))
            return authorRepository.findAll();
        return authorRepository.findAll()
                .stream()
                .filter(s -> s.getName().contains(match) || s.getLastName().contains(match))
                .toList();
    }

}

