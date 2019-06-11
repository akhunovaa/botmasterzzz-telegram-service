package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.dto.ValidationError;
import org.springframework.web.multipart.MultipartFile;

public interface ImageValidatorService {

    void validate(MultipartFile file, ValidationError validationError);

}
