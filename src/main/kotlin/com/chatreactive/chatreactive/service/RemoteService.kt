package com.chatreactive.chatreactive.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@Service
class RemoteService {

    fun simulateRemoteCall(): Mono<String> {
        return Mono.defer {
            if (Math.random() < 0.8) {
                // Simulamos una llamada exitosa con una demora aleatoria de 1 a 3 segundos.
                Mono.just("Respuesta exitosa del servicio remoto")
                    .delayElement(Duration.ofSeconds((1 + Math.random() * 2).toLong()))
            } else {
                // Simulamos un error en la llamada.
                Mono.error(RuntimeException("Error en la llamada al servicio remoto"))
            }
        }.subscribeOn(Schedulers.boundedElastic())
    }
}