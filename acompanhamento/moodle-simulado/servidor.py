
import json
import os
import time
from datetime import datetime, timezone
from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse, parse_qs

HOST = "0.0.0.0"
PORT = 8000

DIRETORIO = os.path.dirname(os.path.abspath(__file__))
ARQUIVO_DADOS = os.path.join(DIRETORIO, "dados.json")


def carregar_dados():
    with open(ARQUIVO_DADOS, "r", encoding="utf-8") as arquivo:
        return json.load(arquivo)


class MoodleSimuladoHandler(BaseHTTPRequestHandler):

    def responder_json(self, dados):
        corpo = json.dumps(dados).encode("utf-8")
        self.send_response(200)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(corpo)))
        self.end_headers()
        self.wfile.write(corpo)

    def do_GET(self):
        url = urlparse(self.path)
        parametros = parse_qs(url.query)

        if url.path == "/":
            return self.responder_json({
                "mensagem": "Moodle simulado funcionando",
                "servicos": [
                    "mod_assign_get_assignments",
                    "core_enrol_get_enrolled_users",
                    "mod_assign_get_submissions"
                ]
            })

        funcao = parametros.get("wsfunction", [""])[0]
        dados = carregar_dados()

        if funcao == "mod_assign_get_assignments":
            cursos = []

            for curso in dados["courses"]:
                tarefas = []

                for tarefa in dados["assignments"]:
                    if tarefa["course"] != curso["id"]:
                        continue

                    prazo = int(
                        time.time()
                        + tarefa["hours_until_due"] * 3600
                    )

                    tarefas.append({
                        "id": tarefa["id"],
                        "name": tarefa["name"],
                        "intro": tarefa["intro"],
                        "duedate": prazo
                    })

                cursos.append({
                    "id": curso["id"],
                    "fullname": curso["fullname"],
                    "assignments": tarefas
                })

            return self.responder_json({"courses": cursos})

        if funcao == "core_enrol_get_enrolled_users":
            curso_id = int(parametros.get("courseid", ["0"])[0])
            cursos_validos = [
                curso for curso in dados["courses"]
                if curso["id"] == curso_id
            ]

            if not cursos_validos:
                return self.responder_json([])

            return self.responder_json(dados["students"])

        if funcao == "mod_assign_get_submissions":
            ids = parametros.get("assignmentids[0]", ["0"])
            tarefa_id = int(ids[0])

            entregas = [
                {
                    "userid": entrega["student"],
                    "status": entrega["status"]
                }
                for entrega in dados["submissions"]
                if entrega["assignment"] == tarefa_id
            ]

            return self.responder_json({
                "assignments": [
                    {
                        "assignmentid": tarefa_id,
                        "submissions": entregas
                    }
                ]
            })

        return self.responder_json({
            "exception": "invalidparameter",
            "message": "Função não reconhecida: " + funcao
        })

    def log_message(self, formato, *args):
        print("[Moodle simulado] " + (formato % args))


if __name__ == "__main__":
    servidor = HTTPServer((HOST, PORT), MoodleSimuladoHandler)
    print(f"Moodle simulado disponível na porta {PORT}")
    servidor.serve_forever()
