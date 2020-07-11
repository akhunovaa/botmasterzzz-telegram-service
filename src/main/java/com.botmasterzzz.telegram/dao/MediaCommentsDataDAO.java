package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.MediaCommentsDataEntity;

import java.util.List;

public interface MediaCommentsDataDAO {

    void commentAdd(MediaCommentsDataEntity mediaCommentsDataEntity);

    List<MediaCommentsDataEntity> getCommentsForMedia(Long mediaFileId, int offset, int limit);
}
