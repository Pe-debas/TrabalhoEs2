package trabalho.acompanhamento.modelos;

import java.time.Instant;

public class Tarefa{

    //Tarefa: o que preciso saber dela? (identificador, curso, nome, prazo);

    private long id;
    private long idCurso;
    private Instant prazo;
    private String nome;
    private String descricao;

    public Tarefa(long id, long idCurso, Instant prazo, String nome, String descricao){
        this.id = id;
        this.idCurso = idCurso;
        this.prazo = prazo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public long getIdCurso() {
        return idCurso;
    }

    public Instant getPrazo() {
        return prazo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao(){
        return descricao;
    }
    
}
