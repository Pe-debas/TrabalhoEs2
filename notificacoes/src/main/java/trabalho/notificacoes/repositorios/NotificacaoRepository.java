
package trabalho.notificacoes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalho.notificacoes.modelos.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, String> {
}
