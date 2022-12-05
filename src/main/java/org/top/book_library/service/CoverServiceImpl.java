package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    // удалить ссылку по id
    @Override
    public void deleteCoverByID(Long id) {
        Optional<Cover> result = coverRepository.findById(id);
        result.ifPresent(coverRepository::delete);
    }
}
