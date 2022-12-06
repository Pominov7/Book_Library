package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Cover;
import org.top.book_library.db.repository.CoverRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CoverServiceImpl implements CoverService {

    @Autowired
    private CoverRepository coverRepository;

    // Найти обложку по названию
    @Override
    public Cover findByName(String coverName) {
        return coverRepository.findByNameCover(coverName).orElse(null);
    }

    // Получение обложки по id
    @Override
    public Optional<Cover> getById(Long id) {
        return coverRepository.findById(id);
    }

    // получить список всех обложек
    @Override
    public List<Cover> listAllCovers() {
        return coverRepository.findAll();
    }

    // сохранить обложку
    @Override
    public Cover saveCover(Cover cover) {
        return coverRepository.save(cover);
    }

    // изменить поля обложки
    @Override
    public void updateCover(Cover cover) {
        Optional<Cover> optionalCover = getById(cover.getId());
        if (optionalCover.isPresent()) {
            Cover editedCover = optionalCover.get();
            if (!editedCover.equals(cover)) {
                editedCover.setNameCover(cover.getNameCover());
                editedCover.setUrlCover(cover.getUrlCover());
                coverRepository.save(editedCover);
            }
        }
    }

    // удалить ссылку по id
    @Override
    public void deleteCoverByID(Long id) {
        Optional<Cover> result = coverRepository.findById(id);
        result.ifPresent(coverRepository::delete);
    }
}
