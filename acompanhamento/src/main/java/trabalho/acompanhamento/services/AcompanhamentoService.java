package trabalho.acompanhamento.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import trabalho.acompanhamento.modelos.Aluno;
import trabalho.acompanhamento.modelos.Entrega;
import trabalho.acompanhamento.modelos.Solicitacao;
import trabalho.acompanhamento.modelos.Tarefa;

@Service 
public class AcompanhamentoService {

    private final PublicadorReengajamento publicador;

    public AcompanhamentoService(PublicadorReengajamento publicador) {
        this.publicador = publicador;
    }

    public List<Solicitacao> verificarTarefas(
            List<Aluno> alunos,
            List<Tarefa> tarefas,
            List<Entrega> entregas) {

        List<Solicitacao> solicitacoes = new ArrayList<>();

        Instant agora = Instant.now();
        Instant limite = agora.plus(24, ChronoUnit.HOURS);

        for (Tarefa tarefa : tarefas) {
            Instant prazo = tarefa.getPrazo();

            if (prazo != null
                    && prazo.isAfter(agora)
                    && prazo.isBefore(limite)) {

                for (Aluno aluno : alunos) {

                    boolean tarefaEntregue = false;

                    for (Entrega entrega : entregas) {

                        if (entrega.getIdTarefa() == tarefa.getId()
                                && entrega.getIdAluno() == aluno.getId()) {

                            if (entrega.getStatus() == Entrega.Status.ENTREGUE) {
                                tarefaEntregue = true;
                            }
                        }
                    }

                    if (!tarefaEntregue) {
                        Solicitacao solicitacao = new Solicitacao(
                                tarefa.getIdCurso(),
                                tarefa.getId(),
                                aluno.getId(),
                                tarefa.getNome(),
                                tarefa.getDescricao(),
                                aluno.getNome(),
                                aluno.getEmail(),
                                tarefa.getPrazo());

                        solicitacoes.add(solicitacao);
                    }
                }
            }
        }

        return solicitacoes;
    }

     public void publicarSolicitacoes(List<Solicitacao> solicitacoes) {

        for (Solicitacao solicitacao : solicitacoes) {
            publicador.publicar(solicitacao);
        }
    }
}