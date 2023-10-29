package com.chatreactive.chatreactive.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class ChatMessage(@Id val id: String? = null, val username: String, val message: String, val timestamp: Instant = Instant.now())

