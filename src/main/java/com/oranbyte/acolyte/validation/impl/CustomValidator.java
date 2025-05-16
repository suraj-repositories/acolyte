package com.oranbyte.acolyte.validation.impl;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.acolyte.validation.ValidCustom;
import com.oranbyte.acolyte.dto.AbcDefDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<ValidCustom, AbcDefDto> {

    @Override
    public boolean isValid(AbcDefDto dto, ConstraintValidatorContext context) {
        if (dto == null)
            return true;

        context.disableDefaultConstraintViolation();

        return true;
    }
}