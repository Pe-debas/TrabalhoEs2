package trabalho.acompanhamento.services;

import org.springframework.stereotype.Component;

import trabalho.acompanhamento.modelos.Aluno;
import trabalho.acompanhamento.modelos.Entrega;
import trabalho.acompanhamento.modelos.Solicitacao;
import trabalho.acompanhamento.modelos.Tarefa;
import trabalho.acompanhamento.moodleClient.MoodleClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

@Component
public class AgendadorAcompanhamento {

    private final MoodleClient moodleClient;
    private final AcompanhamentoService acompanhamentoService;
    
    public AgendadorAcompanhamento(MoodleClient moodleClient, AcompanhamentoService acompanhamentoService) {
        this.moodleClient = moodleClient;
        this.acompanhamentoService = acompanhamentoService;
    }

    @Scheduled(fixedDelayString = "${acompanhamento.intervalo-ms:3600000}")
    public void executar() {
        System.out.println("Iniciando consulta ao Moodle...");

        try {
            // Por enquanto, vamos consultar o curso 101 do simulador.
            long idCurso = 101;

            List<Tarefa> tarefas = moodleClient.buscarTarefas(idCurso);
            List<Aluno> alunos = moodleClient.buscarAlunos(idCurso);

            List<Entrega> entregas = new ArrayList<>();

            for (Tarefa tarefa : tarefas) {
                entregas.addAll(
                    moodleClient.buscarSubmissoes(tarefa.getId())
                );
            }

            System.out.println("Tarefas encontradas: " + tarefas.size());
            System.out.println("Alunos encontrados: " + alunos.size());
            System.out.println("Entregas encontradas: " + entregas.size());

            List<Solicitacao> solicitacoes =
                acompanhamentoService.verificarTarefas(
                    alunos, tarefas, entregas
                );

            System.out.println(
                "Solicitações identificadas: " + solicitacoes.size()
            );

                acompanhamentoService.publicarSolicitacoes(solicitacoes);

                System.out.println("Consulta finalizada.");

            } catch (Exception e) {
                System.err.println("Erro durante a consulta ao Moodle:");
                e.printStackTrace();
            }   
        }
}