package trabalho.notificacoes.consumidor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import trabalho.notificacoes.configuracao.RabbitMQConfig;

@Component
public class ConsumidorNotificacoes {
    
    @RabbitListener(queues = RabbitMQConfig.solicitacoesReengajamento)
    public void receberMensagem(String mensagem) {
        System.out.println("=== NOTIFICAÇÕES RECEBEU UMA MENSAGEM ===");
        System.out.println(mensagem);
    }
}
