package org.library.manager.util;

import org.library.manager.dao.ReaderDao;
import org.library.manager.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class ReaderValidator implements Validator {
    private ReaderDao readerDao;
    @Autowired
    public ReaderValidator(ReaderDao readerDao) {
        this.readerDao = readerDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Reader reader = (Reader) target;
        if(readerDao.getReader(reader.getPhoneNumber()) != null){
            errors.rejectValue("phoneNumber", "", "Phone number already exists");
        }
    }
}
