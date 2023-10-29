package com.chatreactive.chatreactive.controller

import com.chatreactive.chatreactive.exception.ChatException
import com.chatreactive.chatreactive.model.ChatMessage
import com.chatreactive.chatreactive.repository.ChatMessageRepository
import com.chatreactive.chatreactive.service.RemoteService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/chat")
class ChatController {


    @Autowired
    private lateinit var chatMessageRepository: ChatMessageRepository

    @Autowired
    private lateinit var remoteService: RemoteService

    @GetMapping("/messages")
    fun getMessages(): Flux<ChatMessage> {
        return chatMessageRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))

//        return chatMessageRepository.findByOrderByTimestampDesc()
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: ChatMessage): Mono<ChatMessage> {
        return chatMessageRepository.save(message)
            .onErrorResume {
                // Aquí puedes manejar la excepción, registrarla, y retornar un mensaje de error adecuado.
                Mono.error(ChatException("No se pudo guardar el mensaje."))
            }
    }

    @CircuitBreaker(name = "remoteService", fallbackMethod = "fallbackRemoteCall")
    @GetMapping("/call-remote")
    fun callRemoteService(): Mono<String> {
        return remoteService.simulateRemoteCall()
    }


    fun fallbackRemoteCall(throwable: Throwable): Mono<String> {
        return Mono.just("Respuesta de fallback debido a un error en la llamada al servicio remoto: ${throwable.message}")
    }

    /*private val chatMessages = mutableListOf<ChatMessage>()

    @GetMapping("/messages")
    fun getMessages(): Flux<ChatMessage> {
        return Flux.fromIterable(chatMessages)
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: ChatMessage): Mono<Void> {
        chatMessages.add(message)
        return Mono.empty()
    }*/


}