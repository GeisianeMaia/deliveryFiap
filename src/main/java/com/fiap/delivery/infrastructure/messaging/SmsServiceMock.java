package com.fiap.delivery.infrastructure.messaging;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class SmsServiceMock {

    public String sendSms(String phoneNumber, String message) {
        System.out.println("SIMULADO: Enviando SMS para o número: " + phoneNumber);
        System.out.println("Mensagem: " + message);
        return "SMS Simulado Enviado";
    }
}
