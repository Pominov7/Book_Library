package org.top.book_library.service;

import org.top.book_library.db.entity.Cover;

import java.util.List;
import java.util.Optional;

public interface CoverService {

    // получить обложку по id
    Optional<Cover> getById(Long id);

    // получить список всех обложек
    List<Cover> listAllCovers();

    // сохранить обложку
    Cover saveCover(Cover cover);

    // редактировать поля обложки
    void updateCover(Cover cover);

    // удалить обложку по id
    void deleteCoverByID(Long id);

}
