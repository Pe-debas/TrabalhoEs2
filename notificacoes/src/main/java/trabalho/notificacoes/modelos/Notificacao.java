package trabalho.notificacoes.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificacoes")
public class Notificacao {

    @Id
    private String id;

    private long idCurso;
    private long idTarefa;
    private long idAluno;

    private String status;

    public Notificacao(long idCurso, long idTarefa, long idAluno) {
        this.idCurso = idCurso;
        this.idTarefa = idTarefa;
        this.idAluno = idAluno;

        this.id = idCurso + "-" + idTarefa + "-" + idAluno;
        this.status = "ENVIADO";
    }

    public Notificacao(){}

    public String getId(){
        return id; 
    }

}

