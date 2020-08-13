package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendDocument;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.send.SendVideo;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageReplyMarkup;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Message;
import com.botmasterzzz.bot.api.impl.objects.OutgoingMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.telegram.config.container.BotApiMethodContainer;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.controller.BotApiMethodController;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.service.TelegramBotStatisticService;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class KafkaTelegramConsumerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTelegramConsumerImpl.class);

    private static final BotApiMethodContainer container = BotApiMethodContainer.getInstanse();

    private final ObjectMapper objectMapper;
    private final Gson gson;
    private final TelegramBotStatisticService telegramBotStatisticService;

    @Autowired
    private TelegramMediaService telegramMediaService;


    private final KafkaTemplate<Long, OutgoingMessage> kafkaMessageTemplate;


    @Value(value = "${telegram.outgoing.messages.topic.name}")
    private String topicName;

    @Autowired
    public KafkaTelegramConsumerImpl(ObjectMapper objectMapper, Gson gson, TelegramBotStatisticService telegramBotStatisticService, KafkaTemplate<Long, OutgoingMessage> kafkaMessageTemplate) {
        this.objectMapper = objectMapper;
        this.gson = gson;
        this.telegramBotStatisticService = telegramBotStatisticService;
        this.kafkaMessageTemplate = kafkaMessageTemplate;
    }

    @KafkaListener(id = "telegram-instance-service", topics = {"telegram-income-messages"}, containerFactory = "singleFactory")
    public void consume(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Long key, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Update update) {
        LOGGER.info("{} => consumed {}", key, update.toString());
        UserContextHolder.setupContext(update);
        UserContextHolder.currentContext().setInstanceId(key);
        BotApiMethodController methodController = getHandle(update);
        dataWrite(key, update);
        List<PartialBotApiMethod> apiMethods = methodController.process(update);
        apiMethods.parallelStream().forEach(apiMethod -> dataSend(key, apiMethod));
    }

    @KafkaListener(id = "telegram-message-service", topics = {"telegram-callback-message"}, containerFactory = "singleMessageFactory")
    public void consumeMessage(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Message message) {
        LOGGER.info("{} => consumed {}", key, message.toString());
        String mediaFileId = message.getVideo().getFileId();
        telegramMediaService.telegramUserMediaUpdate(key, mediaFileId);
    }

    private void dataSend(Long key, PartialBotApiMethod apiMethod) {
        LOGGER.info("<= sending {} to topic {}", key, topicName);
        OutgoingMessage outgoingMessage = new OutgoingMessage();
        try {
            if (apiMethod instanceof SendDocument) {
                outgoingMessage.setData(objectMapper.writeValueAsBytes(apiMethod));
                outgoingMessage.setTypeMessage("SendDocument");
                kafkaMessageTemplate.send(topicName, key, outgoingMessage);
            } else if (apiMethod instanceof SendPhoto) {
                outgoingMessage.setData(objectMapper.writeValueAsBytes(apiMethod));
                outgoingMessage.setTypeMessage("SendPhoto");
                kafkaMessageTemplate.send(topicName, key, outgoingMessage);
            } else if (apiMethod instanceof SendVideo) {
                outgoingMessage.setData(objectMapper.writeValueAsBytes(apiMethod));
                outgoingMessage.setTypeMessage("SendVideo");
                kafkaMessageTemplate.send(topicName, key, outgoingMessage);
            } else if (apiMethod instanceof EditMessageText) {
                outgoingMessage.setData(objectMapper.writeValueAsBytes(apiMethod));
                outgoingMessage.setTypeMessage("EditMessageText");
                kafkaMessageTemplate.send(topicName, key, outgoingMessage);
            } else if (apiMethod instanceof EditMessageReplyMarkup) {
                outgoingMessage.setData(objectMapper.writeValueAsBytes(apiMethod));
                outgoingMessage.setTypeMessage("EditMessageReplyMarkup");
                kafkaMessageTemplate.send(topicName, key, outgoingMessage);
            } else {
                outgoingMessage.setData(objectMapper.writeValueAsBytes(apiMethod));
                outgoingMessage.setTypeMessage("SendMessage");
                kafkaMessageTemplate.send(topicName, key, outgoingMessage);
            }
        } catch (IOException exception) {
            LOGGER.error("Exception in send to kafka", exception);
        }

    }

    private BotApiMethodController getHandle(Update update) {
        CallBackData callBackData;
        BotApiMethodController controller;
        int instanceId = Math.toIntExact(UserContextHolder.currentContext().getInstanceId());
        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
        if (null != message && message.contains("/start") && message.split(" ").length > 1){
            Long referralId = Long.valueOf(message.split(" ")[1]);
            UserContextHolder.currentContext().setReferralId(referralId);
            message = message.split(" ")[0];
        }
        if (update.hasCallbackQuery()) {
            callBackData = gson.fromJson(update.getCallbackQuery().getData(), CallBackData.class);
            UserContextHolder.currentContext().setCallBackData(callBackData);
            message = callBackData.getPath();
        }
        boolean remain = UserContextHolder.currentContext().isRemain();
        boolean commentAwait = UserContextHolder.currentContext().isCommentRemain();
        switch (instanceId) {
            case 1:
                if (update.hasCallbackQuery()){
                    callBackData = gson.fromJson(update.getCallbackQuery().getData(), CallBackData.class);
                    UserContextHolder.currentContext().setCallBackData(callBackData);
                    message = callBackData.getPath();
                }
                remain = UserContextHolder.currentContext().isRemain();
                controller = !remain ? container.getControllerMap().get("getparts-" + message) : container.getControllerMap().get("getparts-SECRET-FIND") ;
                break;
            case 33:
                controller = container.getControllerMap().get("taxi-" + message);
                break;
            case 35:
                controller = container.getControllerMap().get("parkon-" + message);
                break;
            default:
                controller = !remain ? container.getControllerMap().get("tiktok-" + message) : container.getControllerMap().get("tiktok-media-upload");
                if (remain && null != message && message.equals("❌Отмена")) {
                    controller = container.getControllerMap().get("tiktok-" + message);
                } else if (remain && null != message && (message.contains("/watch?v=") || message.contains("https://youtu.be/"))) {
                    controller = container.getControllerMap().get("tiktok-media-upload-link");
                    break;
                } else if (remain && !(update.getMessage().hasPhoto() || update.getMessage().hasVideo() || update.getMessage().hasDocument())) {
                    controller = container.getControllerMap().get("tiktok-media-upload-error");
                } else if (commentAwait) {
                    controller = container.getControllerMap().get("tiktok-comment-upload");
                }
        }
        return null != controller ? controller : container.getControllerMap().get("command-unknown-error");
    }

    private void dataWrite(Long instanceId, Update update) {

        if (update.hasCallbackQuery()) {
            boolean userExists = telegramBotStatisticService.telegramUserExists(update.getCallbackQuery().getFrom().getId());
            if (!userExists) {
                LOGGER.info("User does not exists: {}", update.getCallbackQuery().getFrom().getId());
                telegramBotStatisticService.telegramUserAdd(update.getCallbackQuery().getFrom());
                LOGGER.info("User added: {}", update.getCallbackQuery().getFrom().getId());
            }
            LOGGER.info("User exists statistic add {}", update.getCallbackQuery().getFrom().getId());
            telegramBotStatisticService.telegramStatisticAdd(update.getCallbackQuery().getMessage(), instanceId, update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getData());
        } else {
            boolean userExists = telegramBotStatisticService.telegramUserExists(update.getMessage().getFrom().getId());
            if (!userExists) {
                Long referralId = UserContextHolder.currentContext().getReferralId();
                LOGGER.info("User does not exists: {} and referralId {}", update.getMessage().getFrom().getId(), referralId);
                telegramBotStatisticService.telegramUserAdd(update.getMessage().getFrom(), referralId);
                LOGGER.info("User added: {}", update.getMessage().getFrom().getId());
            }
            LOGGER.info("User exists statistic add: {}", update.getMessage().getFrom().getId());
            telegramBotStatisticService.telegramStatisticAdd(update.getMessage(), instanceId, update.getMessage().getFrom().getId());
        }
    }

}
