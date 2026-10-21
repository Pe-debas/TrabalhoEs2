package trabalho.acompanhamento.configuracao;

import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;


@Configuration
public class RabbitMQConfig {
    
    @Bean 
    public Queue filaReengajamento(){

        Queue solicitacoesReengajamento = new Queue("solicitacoes-reengajamento", true);


        return solicitacoesReengajamento;
    }
}