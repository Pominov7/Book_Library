package org.top.book_library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Link;

import java.util.Objects;

// Валидатор для ссылки на скачивание
@Component
public class LinkValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book link = (Book) target;
        if (!Objects.equals(link.getLink().getLinkDownload(), "https://...")){
            errors.rejectValue("linkDownload"," the link must begin with the prefix https://");
        }
    }
}
