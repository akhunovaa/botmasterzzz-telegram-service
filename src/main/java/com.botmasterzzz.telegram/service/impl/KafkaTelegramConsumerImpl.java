package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendDocument;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.send.SendVideo;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageReplyMarkup;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.OutgoingMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.telegram.config.container.BotApiMethodContainer;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.controller.BotApiMethodController;
import com.botmasterzzz.telegram.controller.Handle;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.service.TelegramBotStatisticService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@Service
public class KafkaTelegramConsumerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTelegramConsumerImpl.class);

    private static BotApiMethodContainer container = BotApiMethodContainer.getInstanse();

    private final ObjectMapper objectMapper;
    private final Handle handle;
    private final Gson gson;
    private final TelegramBotStatisticService telegramBotStatisticService;

    private final KafkaTemplate<Long, OutgoingMessage> kafkaMessageTemplate;


    @Value(value = "${telegram.outgoing.messages.topic.name}")
    private String topicName;

    @Autowired
    public KafkaTelegramConsumerImpl(ObjectMapper objectMapper, Handle handle, Gson gson, TelegramBotStatisticService telegramBotStatisticService, KafkaTemplate<Long, OutgoingMessage> kafkaMessageTemplate) {
        this.objectMapper = objectMapper;
        this.handle = handle;
        this.gson = gson;
        this.telegramBotStatisticService = telegramBotStatisticService;
        this.kafkaMessageTemplate = kafkaMessageTemplate;
    }

    @KafkaListener(id = "telegram-instance-service", topics = {"telegram-income-messages"}, containerFactory = "singleFactory")
    public void consume(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Long key, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Update update) {
        LOGGER.info("{} => consumed {}", key, update.toString());
        dataWrite(key, update);
        UserContextHolder.setupContext(update);
        UserContextHolder.currentContext().setInstanceId(key);
        BotApiMethodController methodController = getHandle(update);
        PartialBotApiMethod apiMethod = methodController.process(update);
        dataSend(key, apiMethod);
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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private BotApiMethodController getHandle(Update update) {
        CallBackData callBackData;
        BotApiMethodController controller;
        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
        if (update.hasCallbackQuery()) {
            callBackData = gson.fromJson(update.getCallbackQuery().getData(), CallBackData.class);
            UserContextHolder.currentContext().setCallBackData(callBackData);
            message = callBackData.getPath();
        }
        boolean remain = UserContextHolder.currentContext().isRemain();
        controller = !remain ? container.getControllerMap().get("tiktok-" + message) : container.getControllerMap().get("tiktok-media-upload");
        if (remain && null != message && message.equals("❌Отмена")) {
            controller = container.getControllerMap().get("tiktok-" + message);
        } else if (remain && !(update.getMessage().hasPhoto() || update.getMessage().hasVideo() || update.getMessage().hasDocument())) {
            controller = container.getControllerMap().get("tiktok-media-upload-error");
        }
        return controller;
    }

    private void dataWrite(Long instanceId, Update update) {
        if (update.hasCallbackQuery()) {
            boolean userExists = telegramBotStatisticService.telegramUserExists(update.getCallbackQuery().getFrom().getId());
            if (!userExists) {
                LOGGER.debug("User does not exists: {}", update.getCallbackQuery().getFrom().getId());
                telegramBotStatisticService.telegramUserAdd(update.getCallbackQuery().getFrom());
                LOGGER.debug("User added: {}", update.getCallbackQuery().getFrom().getId());
            }
            LOGGER.debug("User exists statistic add {}", update.getCallbackQuery().getFrom().getId());
            telegramBotStatisticService.telegramStatisticAdd(update.getCallbackQuery().getMessage(), instanceId, update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getData());
        } else {

            boolean userExists = telegramBotStatisticService.telegramUserExists(update.getMessage().getFrom().getId());
            if (!userExists) {
                LOGGER.debug("User does not exists: {}", update.getMessage().getFrom().getId());
                telegramBotStatisticService.telegramUserAdd(update.getMessage().getFrom());
                LOGGER.debug("User added: {}", update.getMessage().getFrom().getId());
            }
            LOGGER.debug("User exists statistic add: {}", update.getMessage().getFrom().getId());
            telegramBotStatisticService.telegramStatisticAdd(update.getMessage(), instanceId, update.getMessage().getFrom().getId());
        }
    }

}