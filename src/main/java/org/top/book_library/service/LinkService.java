package org.top.book_library.service;

import org.top.book_library.db.entity.Link;

import java.util.List;
import java.util.Optional;

public interface LinkService {

    // Найти ссылку по названию
    Link findByName(String linkName);

    // Получение ссылки по id
     Optional<Link> getById(Long id);

    // получить список всех ссылок
    List<Link> listAllLinks();

    // сохранить ссылку
    Link saveLink(Link link);

    // удалить ссылку по id
    void deleteLinkByID(Long id);


}
