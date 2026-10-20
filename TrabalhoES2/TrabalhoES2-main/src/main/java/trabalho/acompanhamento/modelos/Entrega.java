package trabalho.acompanhamento.modelos;

public class Entrega {
    
    //Entrega: (qual tarefa, qual aluno, status);

    public enum Status{
        NAO_ENTREGUE,
        ENTREGUE;
    }

    long idTarefa;
    long idAluno;
    Status status;
    

    public Entrega(long idTarefa, long idAluno, Status status){
        this.idTarefa = idTarefa;
        this.idAluno = idAluno;
        this.status = status;
    }


    public long getIdTarefa() {
        return idTarefa;
    }


    public long getIdAluno() {
        return idAluno;
    }


    public Status getStatus() {
        return status;
    }

    

}
