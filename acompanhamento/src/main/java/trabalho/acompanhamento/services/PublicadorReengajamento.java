package trabalho.acompanhamento.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import trabalho.acompanhamento.configuracao.RabbitMQConfig;
import trabalho.acompanhamento.modelos.Solicitacao;

@Service 
public class PublicadorReengajamento {
    
    private final RabbitTemplate rabbitTemplate;
    private Solicitacao solicitacao;

    public PublicadorReengajamento(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicar(Solicitacao solicitacao){
        rabbitTemplate.convertAndSend(RabbitMQConfig.solicitacoesReengajamento, solicitacao);
    }
}
