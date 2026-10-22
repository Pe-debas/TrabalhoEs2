package trabalho.acompanhamento.configuracao;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String solicitacoesReengajamento =
            "solicitacoes-reengajamento";

    @Bean
    public Queue filaReengajamento() {
        return new Queue(solicitacoesReengajamento, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}