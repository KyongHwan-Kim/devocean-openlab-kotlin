package com.openlab.openapi_platform.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, Any>) {

    fun sendMessage(topic: String, message: KafkaMessage) {
        kafkaTemplate.send(topic, message.key, message.value)
    }
}