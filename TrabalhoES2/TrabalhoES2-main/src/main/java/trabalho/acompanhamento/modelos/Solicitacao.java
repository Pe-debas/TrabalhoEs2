package trabalho.acompanhamento.modelos;

import java.time.Instant;

public class Solicitacao {

    
    long idCurso;
    long idTarefa;
    long idAluno;

    String nomeTarefa;
    String nomeAluno;
    String emailAluno;

    Instant prazo;

    public Solicitacao(long idCurso, long idTarefa, long idAluno, String nomeTarefa, String nomeAluno, String emailAluno, Instant prazo) {
        this.idCurso = idCurso;
        this.idTarefa = idTarefa;
        this.idAluno = idAluno;
        this.nomeTarefa = nomeTarefa;
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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public Instant getPrazo() {
        return prazo;
    }

    

}