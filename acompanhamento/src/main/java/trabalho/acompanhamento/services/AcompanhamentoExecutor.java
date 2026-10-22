
package trabalho.acompanhamento.services;

import java.util.List;

import org.springframework.stereotype.Component;

import trabalho.acompanhamento.modelos.Aluno;
import trabalho.acompanhamento.modelos.Entrega;
import trabalho.acompanhamento.modelos.Solicitacao;
import trabalho.acompanhamento.modelos.Tarefa;

@Component
public class AcompanhamentoExecutor {

    private final AcompanhamentoService acompanhamentoService;

    public AcompanhamentoExecutor(AcompanhamentoService acompanhamentoService) {
        this.acompanhamentoService = acompanhamentoService;
    }

    public void executar(
            List<Aluno> alunos,
            List<Tarefa> tarefas,
            List<Entrega> entregas) {

        List<Solicitacao> solicitacoes =acompanhamentoService.verificarTarefas(alunos, tarefas, entregas);

        acompanhamentoService.publicarSolicitacoes(solicitacoes);

        System.out.println("Solicitações publicadas: " + solicitacoes.size());
    }
}
