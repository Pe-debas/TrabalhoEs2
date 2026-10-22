package trabalho.acompanhamento.consumirFilaRabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import trabalho.acompanhamento.configuracao.RabbitMQConfig;

public class ConsumidorDaFila {

    @RabbitListener(queues = RabbitMQConfig.solicitacoesReengajamento)
    public void receberMensagem(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
    }
}
