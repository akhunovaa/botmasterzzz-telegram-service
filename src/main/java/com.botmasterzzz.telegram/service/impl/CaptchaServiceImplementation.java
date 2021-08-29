package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dto.GoogleResponse;
import com.botmasterzzz.telegram.exception.InvalidReCaptchaException;
import com.botmasterzzz.telegram.service.CaptchaService;
import com.botmasterzzz.telegram.service.ReCaptchaAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.regex.Pattern;

@Service
public class CaptchaServiceImplementation implements CaptchaService {

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    @Value("${google.recaptcha.key.site}")
    private String secretSiteKey;

    @Value("${google.recaptcha.key.secret}")
    private String secretKeySecret;

    @Autowired
    private RestOperations restTemplate;

    @Autowired
    private ReCaptchaAttemptService reCaptchaAttemptService;


    @Override
    public void processResponse(String response, String clientIp) {
        if(!responseSanityCheck(response)) {
            throw new InvalidReCaptchaException("Response contains invalid characters");
        }

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                secretKeySecret, response, clientIp));

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if(!googleResponse.isSuccess()) {
            if(googleResponse.hasClientError()) {
                reCaptchaAttemptService.reCaptchaFailed(clientIp);
            }
            throw new InvalidReCaptchaException("reCaptcha was not successfully validated");
        }
        reCaptchaAttemptService.reCaptchaSucceeded(clientIp);
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }
}
