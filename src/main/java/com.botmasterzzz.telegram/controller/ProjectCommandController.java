package com.botmasterzzz.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProjectCommandController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectCommandController.class);

    @RequestMapping(value = "/test",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> test(HttpServletRequest httpRequest) {
        logger.info("TEST controller from telegram service");
        Map<String, Object> result = new HashMap<>();
        result.put("success", Boolean.TRUE);
        result.put("page", httpRequest.getServletPath());
        return result;
    }
}
