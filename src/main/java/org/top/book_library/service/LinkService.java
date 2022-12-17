package org.top.book_library.service;

import org.springframework.data.domain.Page;
import org.top.book_library.db.entity.Link;

import java.util.List;
import java.util.Optional;

public interface LinkService {

    // получить ссылку по id
    Optional<Link> getById(Long id);

    // получить список всех ссылок
    List<Link> listAllLinks();

    // сохранить ссылку
    Link saveLink(Link link);

    // редактировать поля ссылки
    void updateLink(Link link);

    // удалить ссылку по id
    void deleteLinkByID(Long id);

    // нумерация страниц(пагинация)
    Page<Link> findPaginated(int pageNo, int size);
}
