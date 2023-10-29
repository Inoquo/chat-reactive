package com.chatreactive.chatreactive.config

import com.chatreactive.chatreactive.controller.ChatController
import com.chatreactive.chatreactive.model.ChatMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class ChatRouter(private val chatController: ChatController) {

    @Bean
    fun route() = router {
        ("/chat" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/messages") {
                ok().body(chatController.getMessages(), ChatMessage::class.java)
            }
            POST("/send") { it ->
                ok().body(
                    it.bodyToMono(ChatMessage::class.java).flatMap { chatController.sendMessage(it) },
                    Void::class.java
                )
            }
            GET("/call-remote") {
                ok().body(chatController.callRemoteService())
            }
        }
    }
}