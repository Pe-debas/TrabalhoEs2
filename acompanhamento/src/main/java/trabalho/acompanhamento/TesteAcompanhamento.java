package trabalho.acompanhamento;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import trabalho.acompanhamento.modelos.Aluno;
import trabalho.acompanhamento.modelos.Entrega;
import trabalho.acompanhamento.modelos.Tarefa;
import trabalho.acompanhamento.services.AcompanhamentoExecutor;

@Configuration
public class TesteAcompanhamento {

    @Bean
    CommandLineRunner testarAcompanhamento(
            AcompanhamentoExecutor executor) {

        return args -> {
            System.out.println(">>> TESTE DE ACOMPANHAMENTO INICIADO <<<");
            // Aluno fictício para o teste.
            Aluno aluno = new Aluno(
                    1001L, "Aluno Teste", "aluno.teste@example.com");

            // Tarefa fictícia com prazo daqui a 12 horas.
            Tarefa tarefa = new Tarefa(
                    2001L,
                    3001L,
                    Instant.now().plus(12, ChronoUnit.HOURS),
                    "Trabalho de teste", "descricao de teste"
            );

            // Nenhuma entrega: a tarefa deve ser considerada pendente.
            List<Entrega> entregas = List.of();

            // Executa a verificação e tenta publicar a solicitação.
            executor.executar(
                    List.of(aluno),
                    List.of(tarefa),
                    entregas
            );
        };
    }
}
