package com.botmasterzzz.telegram.config;

//import com.botmasterzzz.telegram.context.UserContextHolder;
//import com.botmasterzzz.telegram.util.Handle;
import com.botmasterzzz.bot.TelegramLongPollingBot;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.bot.DefaultBotOptions;
import com.botmasterzzz.bot.generic.BotSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class Telegram extends TelegramLongPollingBot {

    @Value("Botmasterzzz")
    private String userName;

    @Value("${token}")
    private String token;

//    @Autowired
//    private BotSession session;

//    @Autowired
//    private Handle handle;
//
//    @Autowired
//    private DefaultBotOptions options;

    private boolean registered;

    public Telegram(@Autowired DefaultBotOptions options) {
        super(options);
    }

    @Override
    public synchronized void onUpdateReceived(final Update update) {
//        UserContextHolder.setupContext(update);
//        handle.handleMessage(update);
    }

    @Override
    public String getBotUsername() {
        return this.userName;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

//    public void start() {
//        if (!session.isRunning() && registered) {
//            session.start();
//        } else if (!session.isRunning()) {
//            registerBot();
//            this.registered = Boolean.TRUE;
//        }
//    }
//
//    public void stop() {
//        if (session.isRunning()) {
//            session.stop();
//        }
//    }
//
//    public boolean botStatus() {
//        return session.isRunning();
//    }
//
//    private void registerBot() {
//        session.setCallback(this);
//        session.setOptions(options);
//        session.start();
//    }


}
