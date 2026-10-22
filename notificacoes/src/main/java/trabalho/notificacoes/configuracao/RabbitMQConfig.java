package trabalho.notificacoes.configuracao;

import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;


@Configuration
@EnableRabbit 
public class RabbitMQConfig {
    
    public static final String solicitacoesReengajamento = "solicitacoes-reengajamento";

    @Bean 
    public Queue filaReengajamento(){

        Queue solicitacoesReengajamento = new Queue("solicitacoes-reengajamento", true);


        return solicitacoesReengajamento;
    }
}
