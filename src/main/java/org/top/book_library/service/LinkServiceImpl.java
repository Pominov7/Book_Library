package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.entity.Cover;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.entity.Link;
import org.top.book_library.db.repository.LinkRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkRepository linkRepository;

    // Найти ссылку по названию
    @Override
    public Link findByName(String linkName) {
        return linkRepository.findByNameLink(linkName).orElse(null);
    }

    // Получение ссылки по id
    @Override
    public Optional<Link> getById(Long id) {
        return linkRepository.findById(id);
    }

    // получить список всех ссылок
    @Override
    public List<Link> listAllLinks() {
        return linkRepository.findAll();
    }

    // сохранить ссылку
    @Override
    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }

    // изменить поля ссылки
    @Override
    public void updateLink(Link link) {
        Optional<Link> optionalLink = getById(link.getId());
        if (optionalLink.isPresent()) {
            Link editedLink = optionalLink.get();
            if (!editedLink.equals(link)) {
                editedLink.setNameLink(link.getNameLink());
                editedLink.setLinkDownload(link.getLinkDownload());
                linkRepository.save(editedLink);
            }
        }
    }

    // удалить ссылку по id
    @Override
    public void deleteLinkByID(Long id) {
        Optional<Link> result = linkRepository.findById(id);
        result.ifPresent(linkRepository::delete);
    }
}
