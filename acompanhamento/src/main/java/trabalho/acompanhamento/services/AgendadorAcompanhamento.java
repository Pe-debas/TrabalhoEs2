package main.java.trabalho.acompanhamento.services;

import org.springframework.stereotype.Component;

import org.springframework.scheduling.annotation.Scheduled;

@Component
public class AgendadorAcompanhamento {

    @Scheduled()
    public void executar(){
        System.out.println("Agendador do Acompanhamento executou!");
    }
}