package com.chatreactive.chatreactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatReactiveApplication

fun main(args: Array<String>) {
    runApplication<ChatReactiveApplication>(*args)
}
