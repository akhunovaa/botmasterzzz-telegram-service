package com.botmasterzzz.telegram.config;

import com.botmasterzzz.bot.TelegramLongPollingBot;
import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.bot.bot.DefaultBotOptions;
import com.botmasterzzz.bot.exceptions.TelegramApiException;
import com.botmasterzzz.bot.generic.BotSession;
import com.botmasterzzz.telegram.config.container.BotInstanceContainer;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.botmasterzzz.telegram.dto.ProjectCommandTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Telegram extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);

    private String userName;
    private String token;
    private BotSession session;
    private DefaultBotOptions options;
    private boolean registered;
    private List<ProjectCommandDTO> projectCommandDTOList;

    public Telegram(DefaultBotOptions options, List<ProjectCommandDTO> projectCommandDTOList) {
        super(options);
        this.options = options;
        this.projectCommandDTOList = projectCommandDTOList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized void onUpdateReceived(final Update update) {
        logger.info("Update received: " + update.toString());
        String message = update.getMessage().getText();
        String answer = "Команда неизвестна. Попробуйте заново.";
        BotApiMethod method = new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(answer);
        for (ProjectCommandDTO projectCommandDTO : projectCommandDTOList) {
            if (message.equals(projectCommandDTO.getCommand())) {
                ProjectCommandTypeDTO projectCommandTypeDTO = projectCommandDTO.getCommandType();
                long typeId = projectCommandTypeDTO.getId();
                switch ((int) typeId) {
                    case 1:
                        answer = projectCommandDTO.getAnswer();
                        method = new SendMessage()
                                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                                .setText(answer);
                        break;
                    case 2:
                        answer = projectCommandDTO.getAnswer();
                        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
                        keyboard.setOneTimeKeyboard(false);
                        List<KeyboardRow> keyboardRows = new ArrayList<>();
                        KeyboardRow keyboardRowLineOne = new KeyboardRow();
                        keyboardRowLineOne.add(answer);
                        keyboardRows.add(keyboardRowLineOne);
                        keyboard.setKeyboard(keyboardRows);
                        method = new SendMessage()
                                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                                .setText(answer)
                                .setReplyMarkup(keyboard);
                        break;
                    default:
                        answer = projectCommandDTO.getAnswer();
                        method = new SendMessage()
                                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                                .setText(answer);
                }
            }
        }


        try {
            logger.info("Send message : " + answer);
            execute(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
