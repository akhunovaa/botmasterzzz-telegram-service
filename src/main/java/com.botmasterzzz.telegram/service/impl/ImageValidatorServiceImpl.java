package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dto.ValidationError;
import com.botmasterzzz.telegram.service.ImageValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageValidatorServiceImpl implements ImageValidatorService {

    public static final String PNG_MIME_TYPE = "image/png";
    public static final String JPEG_MIME_TYPE = "image/jpeg";
    public static final long TEN_MB_IN_BYTES = 5242880;

    public void validate(MultipartFile target, ValidationError validationError) {
        MultipartFile file = target;

        if(file.isEmpty()){
            validationError.setErrorReason("Загружаемый файл не должен быть пустым");
        }

        else if(file.getSize() > TEN_MB_IN_BYTES){
            validationError.setErrorReason("Загружаемый размер файла первышает допустимые нормы");
        }

        else if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) && !JPEG_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
            validationError.setErrorReason("Загружаемый файл не соответствует допустимым нормам");
        }
    }
}
