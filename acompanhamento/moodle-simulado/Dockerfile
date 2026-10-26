FROM python:3.12-slim

WORKDIR /app

COPY servidor.py dados.json ./

EXPOSE 8000

CMD ["python", "-u", "servidor.py"]
