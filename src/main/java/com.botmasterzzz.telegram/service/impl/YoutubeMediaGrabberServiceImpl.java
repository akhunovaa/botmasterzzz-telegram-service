package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dto.ReceivedMediaFile;
import com.botmasterzzz.telegram.service.YoutubeMediaGrabberService;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.github.kiulian.downloader.model.formats.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class YoutubeMediaGrabberServiceImpl implements YoutubeMediaGrabberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YoutubeMediaGrabberServiceImpl.class);

    private final YoutubeDownloader youtubeDownloader;

    @Value("${video.file.upload.path}")
    private String path;

    public YoutubeMediaGrabberServiceImpl(YoutubeDownloader youtubeDownloader) {
        this.youtubeDownloader = youtubeDownloader;
    }

    @Override
    public ReceivedMediaFile downloadVideo(String videoId) {
        ReceivedMediaFile receivedMediaFile = new ReceivedMediaFile();
        YoutubeVideo video;
        try {
            video = youtubeDownloader.getVideo(videoId);
            List<AudioVideoFormat> videoWithAudioFormats = video.videoWithAudioFormats();

            File outputDir = new File(path);
            int last = videoWithAudioFormats.size() - 1;
            Format format = videoWithAudioFormats.get(last);
            receivedMediaFile.setTitle(video.details().title());
            receivedMediaFile.setDescription(video.details().description());
            receivedMediaFile.setFile(video.download(format, outputDir, videoId, true));
            return receivedMediaFile;
        } catch (YoutubeException | IOException exception) {
            LOGGER.error("Youtube media getter error occurs", exception);
        }
        return null;
    }

}

