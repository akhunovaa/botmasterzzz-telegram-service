package com.botmasterzzz.telegram.controller;

import com.botmasterzzz.telegram.dto.TelegramDTO;
import com.botmasterzzz.telegram.dto.UserPrincipal;
import com.botmasterzzz.telegram.service.TelegramInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TelegramController {

    private static final Logger logger = LoggerFactory.getLogger(TelegramController.class);

    @Autowired
    private TelegramInstanceService telegramInstanceService;

    @RequestMapping(value = "/start",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("authenticated")
    public Map<String, Object> telegramInstanceStart(@RequestBody @NotNull TelegramDTO telegramDTO, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        Map<String, Object> result = new HashMap<>();
        result.put("page", httpRequest.getServletPath());
        if (telegramDTO.getProjectId() == 0){
            logger.error("Request to telegram instance start without project id from login {}", userPrincipal.getLogin());
            result.put("success", Boolean.FALSE);
            result.put("telegram", telegramDTO);
            result.put("message", "Project identifier cat not be empty. Please specify an identifier.");
            return result;
        }
        telegramDTO.setUserId(userPrincipal.getId());
        telegramDTO = telegramInstanceService.start(telegramDTO);
        logger.info("Request to telegram instance START {} for {}", telegramDTO.getId(), telegramDTO.getUserId());
        result.put("success", Boolean.TRUE);
        result.put("telegram", telegramDTO);
        return result;
    }

    @RequestMapping(value = "/stop",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("authenticated")
    public Map<String, Object> telegramInstanceStop(@RequestBody @NotNull TelegramDTO telegramDTO, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        Map<String, Object> result = new HashMap<>();
        result.put("page", httpRequest.getServletPath());
        if (telegramDTO.getProjectId() == 0){
            logger.error("Request to telegram instance stop without project id from login {}", userPrincipal.getLogin());
            result.put("success", Boolean.FALSE);
            result.put("telegram", telegramDTO);
            result.put("message", "Project identifier cat not be empty. Please specify an identifier.");
            return result;
        }
        telegramDTO.setUserId(userPrincipal.getId());
        telegramDTO = telegramInstanceService.stop(telegramDTO);
        logger.info("Request to telegram instance STOP {} for {}", telegramDTO.getId(), telegramDTO.getUserId());
        result.put("success", Boolean.TRUE);
        result.put("telegram", telegramDTO);
        return result;
    }

    @RequestMapping(value = "/restart",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("authenticated")
    public Map<String, Object> telegramInstanceRestart(@RequestBody @NotNull TelegramDTO telegramDTO, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        Map<String, Object> result = new HashMap<>();
        result.put("page", httpRequest.getServletPath());
        if (telegramDTO.getProjectId() == 0){
            logger.error("Request to telegram instance restart without project id from login {}", userPrincipal.getLogin());
            result.put("success", Boolean.FALSE);
            result.put("telegram", telegramDTO);
            result.put("message", "Project identifier cat not be empty. Please specify an identifier.");
            return result;
        }
        telegramDTO.setUserId(userPrincipal.getId());
        telegramDTO = telegramInstanceService.stop(telegramDTO);
        logger.info("Request to telegram instance STOP {} for {}", telegramDTO.getId(), telegramDTO.getUserId());
        telegramDTO = telegramInstanceService.start(telegramDTO);
        logger.info("Request to telegram instance START {} for {}", telegramDTO.getId(), telegramDTO.getUserId());
        result.put("success", Boolean.TRUE);
        result.put("telegram", telegramDTO);
        return result;
    }

    @RequestMapping(value = "/status",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("authenticated")
    public Map<String, Object> telegramInstanceStatus(@RequestBody @NotNull TelegramDTO telegramDTO, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        Map<String, Object> result = new HashMap<>();
        result.put("page", httpRequest.getServletPath());
        if (telegramDTO.getProjectId() == 0){
            logger.error("Request to telegram instance status without project id from login {}", userPrincipal.getLogin());
            result.put("success", Boolean.FALSE);
            result.put("telegram", telegramDTO);
            result.put("message", "Project identifier cat not be empty. Please specify an identifier.");
            return result;
        }
        telegramDTO.setUserId(userPrincipal.getId());
        telegramDTO = telegramInstanceService.getStatus(telegramDTO);
        logger.info("Request to telegram instance STATUS {} for {}", telegramDTO.getId(), telegramDTO.getUserId());
        result.put("success", Boolean.TRUE);
        result.put("telegram", telegramDTO);
        return result;
    }

    @RequestMapping(value = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("authenticated")
    public Map<String, Object> telegramInstanceList(HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
        Map<String, Object> result = new HashMap<>();
        result.put("page", httpRequest.getServletPath());
        List<TelegramDTO> telegramInstancesList = telegramInstanceService.getStartedTelegramInstancesList(userPrincipal.getId());
        logger.info("Request to telegram instance LIST for {}", userPrincipal.getId());
        result.put("success", Boolean.TRUE);
        result.put("bots", telegramInstancesList);
        return result;
    }
}
