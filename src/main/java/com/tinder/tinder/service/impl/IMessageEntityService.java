package com.tinder.tinder.service.impl;

import com.tinder.tinder.dto.request.ChatMessage;

import java.util.List;

public interface IMessageEntityService {
    ChatMessage sendMessage(Long matchId, Long senderId, String content);
    List<ChatMessage> getMessages(Long matchId);
}
