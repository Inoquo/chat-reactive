package com.chatreactive.chatreactive.repository

import com.chatreactive.chatreactive.model.ChatMessage
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatMessageRepository : ReactiveMongoRepository<ChatMessage, String> {

}