package trabalho.acompanhamento.moodleClient;

import trabalho.acompanhamento.modelos.Tarefa;
import trabalho.acompanhamento.modelos.Entrega;
import trabalho.acompanhamento.modelos.Aluno;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MoodleClient {

    String url = "https://moodle.pucrs.br/webservice/rest/server.php";
    

    public String consultarMoodle(String urlConsulta){
        try{
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString()); 

            return resposta.body();
        }
        catch (IOException e){
            return "Erro ao consultar o Moodle";
        }
        catch (InterruptedException e){
            return "Erro ao consultar o Moodle";
        }
    }

    public List<Tarefa> buscarTarefas(long idCurso) {
        String funcao = "mod_assign_get_assignments";

        String urlConsulta = url
            + "?wsfunction=" + funcao
            + "&moodlewsrestformat=json"
            + "&courseids[0]=" + idCurso;

        String resposta = consultarMoodle(urlConsulta);

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode raiz = mapper.readTree(resposta);

            // Obtém os cursos retornados pelo Moodle
            JsonNode cursos = raiz.path("courses");

            for (JsonNode curso : cursos) {
                JsonNode assignments = curso.path("assignments");

            for (JsonNode item : assignments) {

                long prazo = item.path("duedate").asLong();

                Tarefa tarefa = new Tarefa(
                    item.path("id").asLong(),
                    curso.path("id").asLong(),
                    prazo == 0 ? null : Instant.ofEpochSecond(prazo),
                    item.path("name").asText(),
                    item.path("intro").asText()
                );
 
                tarefas.add(tarefa);
            }
        }

        return tarefas;

        } catch (IOException e) {
            throw new RuntimeException(
                "Erro ao interpretar as tarefas do Moodle", e
            );
        }
    }

    public List<Aluno> buscarAlunos(long idCurso) {
        String funcao = "core_enrol_get_enrolled_users";

        String urlConsulta = url
            + "?wsfunction=" + funcao
            + "&moodlewsrestformat=json"
            + "&courseid=" + idCurso;

        String resposta = consultarMoodle(urlConsulta);

        List<Aluno> alunos = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode raiz = mapper.readTree(resposta);

            for (JsonNode item : raiz) {
                Aluno aluno = new Aluno(
                    item.path("id").asLong(),
                    item.path("fullname").asText(),
                    item.path("email").asText()
                );

                alunos.add(aluno);
            }

            return alunos;

        } catch (IOException e) {
            throw new RuntimeException(
                "Erro ao acessar os alunos do Moodle", e
            );
        }
    }

    public List<Entrega> buscarSubmissoes(long idTarefa) {
        String funcao = "?wsfunction=mod_assign_get_submissions";

        String urlConsulta = url
            + "?wsfunction=" + funcao
            + "&moodlewsrestformat=json"
            + "&assignmentids[0]=" + idTarefa;    

        String resposta = consultarMoodle(urlConsulta);

        List<Entrega> entregas = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode raiz = mapper.readTree(resposta);

            JsonNode assignments = raiz.path("assignments");

            for (JsonNode assignment : assignments) {
                long id = assignment.path("assignmentid").asLong();

                JsonNode submissions = assignment.path("submissions");

                for (JsonNode item : submissions) {
                    long idAluno = item.path("userid").asLong();

                    String statusMoodle = item.path("status").asText();

                    Entrega.Status status;

                    switch (statusMoodle) {
                        case "submitted":
                            status = Entrega.Status.ENTREGUE;
                            break;

                        case "new":
                            status = Entrega.Status.NAO_ENTREGUE;
                            break;
                            
                        default:
                            throw new IllegalArgumentException(
                            "Status desconhecido do Moodle: " + statusMoodle
                                );    
                        }

                    Entrega entrega = new Entrega(
                        id,
                        idAluno,
                        status
                    );

                    entregas.add(entrega);
                }
            }

            return entregas;

        } catch (IOException e) {
            throw new RuntimeException(
                "Erro ao interpretar as submissões do Moodle", e
            );
        }
    }

}
