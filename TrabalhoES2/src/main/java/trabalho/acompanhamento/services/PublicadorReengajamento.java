package trabalho.acompanhamento.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service 
public class PublicadorReengajamento {
    
    private final RabbitTemplate rabbitTemplate;

    public PublicadorReengajamento(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicar(String mensagem){
        rabbitTemplate.convertAndSend("solicitacoesReengajamento", mensagem);
    }
}
