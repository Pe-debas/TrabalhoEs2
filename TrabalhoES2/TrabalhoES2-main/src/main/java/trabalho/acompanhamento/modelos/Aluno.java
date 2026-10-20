package trabalho.acompanhamento.modelos;

public class Aluno {
    
    //Aluno matriculado: (identificador, nome, email);

    long id;
    String nome;
    String email;

    public Aluno(long id, String nome, String email){
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }


    
}
