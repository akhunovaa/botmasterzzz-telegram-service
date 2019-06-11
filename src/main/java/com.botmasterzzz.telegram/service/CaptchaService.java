package com.botmasterzzz.telegram.service;

public interface CaptchaService {

    void processResponse(String response, String clientIp);

}
