package com.triprint.backend.core.valid;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.List;

public class FileSizeValidator implements ConstraintValidator<ValidFileSize, List<MultipartFile>> {

    private int maxSize;
        private static int KILO_BYTE = 1000;

    @Override
    public void initialize(ValidFileSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize() * KILO_BYTE;
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext context) {
        for(MultipartFile file: multipartFiles){
            if (!isValidFileSize(file)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidFileSize(MultipartFile file){
        try {
            int contentBytes = file.getBytes().length;
            return contentBytes < this.maxSize;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
