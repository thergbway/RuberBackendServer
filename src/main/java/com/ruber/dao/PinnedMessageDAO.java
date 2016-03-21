package com.ruber.dao;

import com.ruber.dao.entity.PinnedMessage;

public interface PinnedMessageDAO extends GenericDAO<PinnedMessage> {
    void deleteById(Integer pinnedMessageId);
}
