package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Cover;
import org.top.book_library.db.repository.CoverRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoverServiceImpl implements CoverService {

    @Autowired
    private CoverRepository coverRepository;

    // получить обложку по id
    @Override
    public Optional<Cover> getById(Long id) {
        return coverRepository.findById(id);
    }

    // получить список всех обложек
    @Override
    public List<Cover> listAllCovers() {
        return coverRepository.findAll().stream()
                .sorted(Comparator.comparing(Cover::getNameCover))
                .collect(Collectors.toList());
    }

    // сохранить обложку
    @Override
    public Cover saveCover(Cover cover) {
        return coverRepository.save(cover);
    }

    // редактировать поля обложки
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

    // удалить обложку по id
    @Override
    public void deleteCoverByID(Long id) {
        Optional<Cover> result = coverRepository.findById(id);
        result.ifPresent(coverRepository::delete);
    }
}
