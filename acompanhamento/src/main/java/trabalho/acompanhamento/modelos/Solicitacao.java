package trabalho.acompanhamento.modelos;

import java.time.Instant;

public class Solicitacao {

    
    long idCurso;
    long idTarefa;
    long idAluno;

    String nomeTarefa;
    String descricaoTarefa;
    String nomeAluno;
    String emailAluno;

    Instant prazo;

    public Solicitacao(long idCurso, long idTarefa, long idAluno, String nomeTarefa, String descricaoTarefa, String nomeAluno, String emailAluno, Instant prazo) {
        this.idCurso = idCurso;
        this.idTarefa = idTarefa;
        this.idAluno = idAluno;
        this.nomeTarefa = nomeTarefa;
        this.descricaoTarefa = descricaoTarefa;
        this.nomeAluno = nomeAluno;
        this.emailAluno = emailAluno;
        this.prazo = prazo;
    }

    public long getIdCurso() {
        return idCurso;
    }

    public long getIdTarefa() {
        return idTarefa;
    }

    public long getIdAluno() {
        return idAluno;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public String getDescricaoTarefa(){
        return descricaoTarefa;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public Instant getPrazo() {
        return prazo;
    }

    @Override
    public String toString() {
        return "Solicitacao [idCurso=" + idCurso + ", idTarefa=" + idTarefa + ", idAluno=" + idAluno + ", nomeTarefa="
                + nomeTarefa + ", nomeAluno=" + nomeAluno + ", emailAluno=" + emailAluno + ", prazo=" + prazo + "]";
    }

    

}