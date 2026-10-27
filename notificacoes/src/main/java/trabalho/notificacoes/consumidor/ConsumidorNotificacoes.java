package trabalho.notificacoes.consumidor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import trabalho.notificacoes.configuracao.RabbitMQConfig;
import trabalho.notificacoes.modelos.Notificacao;
import trabalho.notificacoes.repositorios.NotificacaoRepository;
import trabalho.notificacoes.servicos.ServicoEmail;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import tools.jackson.core.JacksonException;

@Component
public class ConsumidorNotificacoes {

    private final ObjectMapper objectMapper;
    private final ServicoEmail servicoEmail;
    private final NotificacaoRepository notificacaoRepository;

    public ConsumidorNotificacoes(ObjectMapper objectMapper, ServicoEmail servicoEmail, NotificacaoRepository notificacaoRepository) {
        this.objectMapper = objectMapper;
        this.servicoEmail = servicoEmail;
        this.notificacaoRepository = notificacaoRepository;
    }
    
    @RabbitListener(queues = RabbitMQConfig.solicitacoesReengajamento)
    public void receberMensagem(String mensagem) {

        System.out.println("=== NOTIFICAÇÕES RECEBEU UMA MENSAGEM ===");
        System.out.println(mensagem);

        try{
            JsonNode dados = objectMapper.readTree(mensagem);

            String nomeAluno = dados.get("nomeAluno").asString();
            String emailAluno = dados.get("emailAluno").asString();
            String nomeTarefa = dados.get("nomeTarefa").asString();
            String descricaoTarefa = dados.get("descricaoTarefa").asString();
            String prazo = dados.get("prazo").asString();

            long idCurso = dados.get("idCurso").asLong();
            long idTarefa = dados.get("idTarefa").asLong();
            long idAluno = dados.get("idAluno").asLong();

            String id = idCurso + "-" + idTarefa + "-" + idAluno;

            if (notificacaoRepository.existsById(id)) {
                System.out.println("Notificação já registrada. E-mail não será enviado novamente.");
                return;
            }

            String corpoEmail = "Olá, " + nomeAluno + "!\n\n"
                + "Você tem uma tarefa próxima do prazo.\n\n"
                + "Tarefa: " + nomeTarefa + "\n"
                + "Descrição: " + descricaoTarefa + "\n"
                + "Prazo: " + prazo + "\n\n"
                + "Lembre-se de entregar a atividade antes do vencimento.\n\n"
                + "Bons estudos!";

            servicoEmail.enviarEmail(emailAluno, "Lembrete: tarefa próxima do prazo", corpoEmail);

            Notificacao notificacao = new Notificacao(idCurso, idTarefa, idAluno);
            notificacaoRepository.save(notificacao);

        } catch (JacksonException e) {
            System.err.println("Falha ao interpretar o JSON: " + e.getMessage());
            throw new IllegalStateException("Não foi possível processar a mensagem recebida", e);
        }
    }
}
