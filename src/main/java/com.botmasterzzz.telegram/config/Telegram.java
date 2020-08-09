package com.botmasterzzz.telegram.config;

import com.botmasterzzz.bot.TelegramLongPollingBot;
import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendDocument;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.send.SendVideo;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.bot.DefaultBotOptions;
import com.botmasterzzz.bot.exceptions.TelegramApiException;
import com.botmasterzzz.bot.generic.BotSession;
import com.botmasterzzz.telegram.config.container.BotInstanceContainer;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.controller.Handle;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.botmasterzzz.telegram.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class Telegram extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);

    private String userName;
    private String token;
    private BotSession session;
    private DefaultBotOptions options;
    private boolean registered;
    private Long instanceId;
    private List<ProjectCommandDTO> projectCommandDTOList;

    public Telegram(DefaultBotOptions options, List<ProjectCommandDTO> projectCommandDTOList, Long instanceId) {
        super(options);
        this.options = options;
        this.projectCommandDTOList = projectCommandDTOList;
        this.instanceId = instanceId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized void onUpdateReceived(final Update update) {
        logger.info("Update received: " + update.toString());
        UserContextHolder.setupContext(update);
        UserContextHolder.currentContext().setProjectCommandDTOList(this.projectCommandDTOList);
        UserContextHolder.currentContext().setInstanceId(this.instanceId);
        Handle handle = BeanUtil.getBean(Handle.class);
        List<PartialBotApiMethod> partialBotApiMethod = handle.handleMessage(update);
        Class genericClass = null;
        Iterator it = partialBotApiMethod.iterator();
        if (it.hasNext()) {
            genericClass = it.next().getClass();
        }

        try {
            if (genericClass == SendPhoto.class) {
                for (PartialBotApiMethod botApiMethod : partialBotApiMethod) {
                    executePhoto((SendPhoto) botApiMethod);
                }
            } else if (genericClass == SendVideo.class) {
                for (PartialBotApiMethod botApiMethod : partialBotApiMethod) {
                    executeVideo((SendVideo) botApiMethod);
                }
            } else if (genericClass == SendDocument.class) {
                for (PartialBotApiMethod botApiMethod : partialBotApiMethod) {
                    executeDocument((SendDocument) botApiMethod);
                }
            } else {
                for (PartialBotApiMethod botApiMethod : partialBotApiMethod) {
                    execute(botApiMethod);
                }
            }
        } catch (TelegramApiException e) {
            logger.error("ERROR TelegramApiException", e);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BotSession getSession() {
        return session;
    }

    public void setSession(BotSession session) {
        this.session = session;
    }

    public void setOptions(DefaultBotOptions options) {
        this.options = options;
    }

    @Override
    public String getBotUsername() {
        return this.userName;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    public void start() {
        if (!session.isRunning() && registered) {
            session.start();
        } else if (!session.isRunning()) {
            registerBot();
            this.registered = Boolean.TRUE;
        }
    }

    public void stop() {
        if (session.isRunning()) {
            session.stop();
        }
    }

    public boolean botStatus() {
        return session.isRunning();
    }

    private void registerBot() {
        session.setCallback(this);
        session.setOptions(options);
        session.start();
    }


}
