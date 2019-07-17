package com.botmasterzzz.telegram.controller.telegram.gkh.account;

import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.controller.telegram.gkh.GkhMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class GasGkhMessageController {

    private static final Logger logger = LoggerFactory.getLogger(GasGkhMessageController.class);

    @Autowired
    private GkhMessageService gkhMessageService;
}
