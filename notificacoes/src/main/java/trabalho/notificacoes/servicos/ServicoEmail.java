package trabalho.notificacoes.servicos;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service 
public class ServicoEmail {
    
    private final JavaMailSender enviador;

    public ServicoEmail(JavaMailSender enviador){
        this.enviador = enviador;
    }

    public void enviarEmail(String email, String titulo, String descricao){
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(email);
        mensagem.setSubject(titulo);
        mensagem.setText(descricao);

        enviador.send(mensagem);
    }

}
