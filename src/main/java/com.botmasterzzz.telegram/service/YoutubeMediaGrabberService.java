package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.dto.ReceivedMediaFile;

public interface YoutubeMediaGrabberService {

    ReceivedMediaFile downloadVideo(String videoId);

}
