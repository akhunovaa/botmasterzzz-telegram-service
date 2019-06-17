package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.config.container.BotInstanceContainer;
import com.botmasterzzz.telegram.service.ResourceStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class ResourceStreamServiceImpl implements ResourceStreamService {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);
    private static final String STATIC_IMAGGE_URL = "https://ih0.redbubble.net/image.430996243.1168/tapestry,1000x-pad,750x1000,f8f8f8.jpg";

    @Override
    public InputStream getImageFromUrl(String url) {
        InputStream in = null;
        try {
            in = new FileInputStream(url);
        } catch (FileNotFoundException e) {
            try {
                in = new FileInputStream(STATIC_IMAGGE_URL);
            } catch (FileNotFoundException e1) {
                logger.error("Image InputStream error", e);
            }
        }
        return in;
    }
}
