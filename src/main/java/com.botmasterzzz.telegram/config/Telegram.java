package com.botmasterzzz.telegram.config;

import com.botmasterzzz.bot.TelegramLongPollingBot;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.bot.DefaultBotOptions;
import com.botmasterzzz.bot.generic.BotSession;
import com.botmasterzzz.telegram.config.container.BotInstanceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Telegram extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);

    private String userName;
    private String token;
    private BotSession session;
    private DefaultBotOptions options;
    private boolean registered;

    public Telegram(DefaultBotOptions options) {
        super(options);
        this.options = options;
    }

    @Override
    public synchronized void onUpdateReceived(final Update update) {
        logger.info("Update received: " + update.toString());
//        UserContextHolder.setupContext(update);
//        handle.handleMessage(update);
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
