package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.bot.bot.DefaultBotOptions;
import com.botmasterzzz.bot.generic.BotSession;
import com.botmasterzzz.bot.updatesreceivers.DefaultBotSession;
import com.botmasterzzz.telegram.config.Telegram;
import com.botmasterzzz.telegram.config.container.BotInstanceContainer;
import com.botmasterzzz.telegram.dao.ProjectDAO;
import com.botmasterzzz.telegram.dao.TelegramInstanceDAO;
import com.botmasterzzz.telegram.dao.UserDAO;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.botmasterzzz.telegram.dto.TelegramDTO;
import com.botmasterzzz.telegram.entity.TelegramInstanceEntity;
import com.botmasterzzz.telegram.entity.UserEntity;
import com.botmasterzzz.telegram.entity.UserProjectEntity;
import com.botmasterzzz.telegram.service.ProjectCommandService;
import com.botmasterzzz.telegram.service.TelegramInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TelegramInstanceServiceImpl implements TelegramInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramInstanceServiceImpl.class);

    private static BotInstanceContainer botInstanceContainer = BotInstanceContainer.getInstanse();

    @PostConstruct
    private void postConstruct() {
        List<TelegramInstanceEntity> telegramInstanceEntityList = telegramInstanceDAO.getFullTelegramInstanceList();
        for (TelegramInstanceEntity telegramInstanceEntity : telegramInstanceEntityList) {
            if (telegramInstanceEntity.isStatus()){
                BotSession botSession = new DefaultBotSession();
                String token = telegramInstanceEntity.getProject().getToken();
                String botName = telegramInstanceEntity.getProject().getName();
                botSession.setToken(token);
                DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
                List<ProjectCommandDTO> projectDTOList = projectCommandService.getProjectCommandGetList(telegramInstanceEntity.getUserEntity().getId(), telegramInstanceEntity.getProject().getId());
                Telegram telegramInstance = new Telegram(defaultBotOptions, projectDTOList, telegramInstanceEntity.getId());
                telegramInstance.setToken(token);
                telegramInstance.setUserName(botName);
                telegramInstance.setSession(botSession);
                telegramInstance.start();
                logger.info("Telegram bot after service restart has been started. {}", telegramInstanceEntity);
                botInstanceContainer.addTelegramBotInstance(telegramInstanceEntity.getId(), telegramInstance);
                logger.info("Telegram bot after service restart has been added. {}", telegramInstanceEntity);
            }

        }
    }

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TelegramInstanceDAO telegramInstanceDAO;

    @Autowired
    private ProjectCommandService projectCommandService;

    @Override
    public TelegramDTO start(TelegramDTO telegramDTO) {
        Long id;
        UserEntity userEntity = userDAO.loadUser(telegramDTO.getUserId());
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(telegramDTO.getProjectId(), telegramDTO.getUserId());
        TelegramInstanceEntity telegramInstanceEntity = telegramInstanceDAO.telegramInstanceGet(userEntity.getId(), userProjectEntity.getId());
        if (null == telegramInstanceEntity){
            telegramInstanceEntity = new TelegramInstanceEntity();
            telegramInstanceEntity.setName(userProjectEntity.getName());
            telegramInstanceEntity.setDescription(userProjectEntity.getDescription());
            telegramInstanceEntity.setNote(userEntity.getNote() + " : " + userProjectEntity.getNote());
            telegramInstanceEntity.setMessengerId(1);
            telegramInstanceEntity.setUserEntity(userEntity);
            telegramInstanceEntity.setProject(userProjectEntity);
            telegramInstanceEntity.setLastError("Ошибки отсутствуют");
            telegramInstanceEntity.setAudWhenCreate(new Timestamp(new Date().getTime()));
            id = telegramInstanceDAO.telegramInstanceAdd(telegramInstanceEntity);
        }
        id = telegramInstanceEntity.getId();
        telegramDTO.setId(id);
        boolean status = null != botInstanceContainer.getBotInstance(id) && botInstanceContainer.getBotInstance(id).botStatus();
        if (status){
            logger.warn("Telegram bot already has been started. {}", telegramInstanceEntity.toString());
            telegramDTO.setStatus(true);
            telegramDTO.setLastError("Экземпляр бота уже запущен");
            telegramDTO.setCreated(telegramInstanceEntity.getAudWhenCreate());
            telegramDTO.setUpdated(telegramInstanceEntity.getAudWhenUpdate());
            return telegramDTO;
        }
        BotSession botSession = new DefaultBotSession();
        String token = telegramInstanceEntity.getProject().getToken();
        String botName = telegramInstanceEntity.getProject().getName();
        botSession.setToken(token);
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        List<ProjectCommandDTO> projectDTOList = projectCommandService.getProjectCommandGetList(userEntity.getId(), userProjectEntity.getId());
        Telegram telegramInstance = new Telegram(defaultBotOptions, projectDTOList, id);
        telegramInstance.setToken(token);
        telegramInstance.setUserName(botName);
        telegramInstance.setSession(botSession);
        telegramInstance.start();
        logger.info("Telegram bot has been started. {}", telegramInstanceEntity);
        botInstanceContainer.addTelegramBotInstance(id, telegramInstance);
        logger.info("Telegram bot has been added. {}", telegramInstanceEntity);
        telegramInstanceEntity.setStatus(true);
        telegramDTO.setStatus(true);
        telegramInstanceEntity = telegramInstanceDAO.telegramInstanceUpdate(telegramInstanceEntity);
        telegramDTO.setLastError(telegramInstanceEntity.getLastError());
        telegramDTO.setCreated(telegramInstanceEntity.getAudWhenCreate());
        telegramDTO.setUpdated(telegramInstanceEntity.getAudWhenUpdate());
        return telegramDTO;
    }

    @Override
    public TelegramDTO stop(TelegramDTO telegramDTO) {
        Long id;
        UserEntity userEntity = userDAO.loadUser(telegramDTO.getUserId());
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(telegramDTO.getProjectId(), telegramDTO.getUserId());
        TelegramInstanceEntity telegramInstanceEntity = telegramInstanceDAO.telegramInstanceGet(userEntity.getId(), userProjectEntity.getId());
        if (null == telegramInstanceEntity){
            logger.warn("Telegram bot instance not found. {}", userProjectEntity.toString());
            telegramDTO.setStatus(false);
            return telegramDTO;
        }
        id = telegramInstanceEntity.getId();
        telegramDTO.setId(id);
        boolean status = null != botInstanceContainer.getBotInstance(id) && botInstanceContainer.getBotInstance(id).botStatus();
        if (!status){
            logger.warn("Telegram bot already has been stopped. {}", telegramInstanceEntity.toString());
            telegramDTO.setLastError("Бот не запущен. Для начала необходимо запустить его.");
            telegramDTO.setStatus(false);
            return telegramDTO;
        }
        if (!botInstanceContainer.isBotAdded(id)){
            logger.warn("Telegram bot not found in container. {}", telegramInstanceEntity.toString());
            telegramDTO.setStatus(false);
            telegramDTO.setLastError("Ошибка в контейнере бота. Бот отсутствует в контейнере. Необходимо переапустить бота.");
            telegramInstanceEntity.setLastError("Ошибка в контейнере бота. Бот отсутствует в контейнере. Необходимо переапустить бота.");
            telegramInstanceEntity = telegramInstanceDAO.telegramInstanceUpdate(telegramInstanceEntity);
            return telegramDTO;
        }
        Telegram telegramInstance = botInstanceContainer.getBotInstance(id);
        telegramInstance.stop();
        telegramDTO.setStatus(false);
        telegramInstanceEntity.setStatus(telegramInstance.botStatus());
        logger.info("Telegram bot has been stopped {}", telegramInstanceEntity.toString());
        telegramInstanceEntity = telegramInstanceDAO.telegramInstanceUpdate(telegramInstanceEntity);
        telegramDTO.setLastError(telegramInstanceEntity.getLastError());
        telegramDTO.setCreated(telegramInstanceEntity.getAudWhenCreate());
        telegramDTO.setUpdated(telegramInstanceEntity.getAudWhenUpdate());
        return telegramDTO;
    }

    @Override
    public TelegramDTO getStatus(TelegramDTO telegramDTO) {
        Long id;
        UserEntity userEntity = userDAO.loadUser(telegramDTO.getUserId());
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(telegramDTO.getProjectId(), telegramDTO.getUserId());
        TelegramInstanceEntity telegramInstanceEntity = telegramInstanceDAO.telegramInstanceGet(userEntity.getId(), userProjectEntity.getId());
        if (null != telegramInstanceEntity){
            id = telegramInstanceEntity.getId();
            telegramDTO.setId(id);
            telegramDTO.setLastError(telegramInstanceEntity.getLastError());
            telegramDTO.setCreated(telegramInstanceEntity.getAudWhenCreate());
            telegramDTO.setUpdated(telegramInstanceEntity.getAudWhenUpdate());
            boolean status = null != botInstanceContainer.getBotInstance(id) && botInstanceContainer.getBotInstance(id).botStatus();
            telegramDTO.setStatus(status);
            logger.info("Telegram bot status read {}", telegramInstanceEntity.toString());
        }else {
            telegramDTO.setStatus(false);
        }
        return telegramDTO;

    }

    @Override
    public List<TelegramDTO> getStartedTelegramInstancesList(Long userId) {
        List<TelegramDTO> telegramDTOList = new ArrayList<>();
        UserEntity userEntity = userDAO.loadUser(userId);
        List<TelegramInstanceEntity> telegramInstanceEntityList = telegramInstanceDAO.getTelegramInstanceList(userEntity.getId());
        for (TelegramInstanceEntity telegramInstanceEntity : telegramInstanceEntityList) {
            TelegramDTO telegramDTO = new TelegramDTO();
            telegramDTO.setId(telegramInstanceEntity.getId());
            telegramDTO.setLastError(telegramInstanceEntity.getLastError());
            boolean status = null != botInstanceContainer.getBotInstance(telegramInstanceEntity.getId()) && botInstanceContainer.getBotInstance(telegramInstanceEntity.getId()).botStatus();
            telegramDTO.setStatus(status);
            telegramDTO.setUserId(telegramInstanceEntity.getId());
            telegramDTO.setMessengerId(telegramInstanceEntity.getMessengerId());
            telegramDTO.setCreated(telegramInstanceEntity.getAudWhenCreate());
            telegramDTO.setProjectId(telegramInstanceEntity.getProject().getId());
            telegramDTO.setUpdated(telegramInstanceEntity.getAudWhenUpdate());
            telegramDTOList.add(telegramDTO);
        }
        return telegramDTOList;
    }
}
