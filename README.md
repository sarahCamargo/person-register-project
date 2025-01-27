<h1 align="center"> Registro de Pessoas </h1>

<p align="center">
  <img src="https://img.shields.io/badge/npm-10.8.2-blue" alt="npm version">
  <img src="https://img.shields.io/badge/node-20.18.0-pink" alt="node version">
  <img src="https://img.shields.io/badge/java-17-blue" alt="java version">
  <img src="https://img.shields.io/badge/JUnit-5.8.1-pink" alt="Junit version">
</p>

Aplicação CRUD para gerenciamento de registros de pessoas, bem como geração dos dados em arquivo CSV.

# Índice 

* [Funcionalidades](#funcionalidades)
* [Estrutura do Repositório](#estrutura-do-repositório)
* [Como utilizar o projeto com Docker](#como-utilizar-o-projeto-com-docker)
  * [Clonando o Repositório](#clonando-o-repositório)
  * [Acessando a aplicação](#acessando-a-aplicação)
  * [Rodando o Backend Separadamente (opcional)](#rodando-o-backend-separadamente-opcional)
  * [Rodando o Frontend Separadamente (opcional)](#rodando-o-frontend-separadamente-opcional)

# Funcionalidades

- `Gerenciamento de pessoas`: Adição, edição, remoção e listagem de pessoas físicas.
- `Geração de arquivo CSV`: Geração de arquivo CSV contendo todos os dados das pessoas cadastradas.]

# Estrutura do Repositório

O repositório está dividido em duas pastas principais:

- `frontend`: Aplicação frontend construída com React.
- `backend`: Aplicação backend construída com Java Spring Boot.

# Como utilizar o projeto com Docker

## Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)
  
Cada pasta possui seu próprio Dockerfile para criação de containers separados para frontend e backend.

## Clonando o Repositório

Clone o repositório para sua máquina local:

```bash
git clone https://github.com/sarahCamargo/person-register-project.git
cd person-register-project
```

## Configurando o Docker

Atualize as configurações no arquivo docker-compose.yml na raiz do projeto conforme necessário.

- SPRING_DATASOURCE_URL=jdbc:postgresql://{usuario_postgres}:{porta}/persondb
- SPRING_DATASOURCE_USERNAME={usuario_postgres}
- SPRING_DATASOURCE_PASSWORD={senha}
- SPRING_RABBITMQ_HOST=rabbitmq
- SPRING_RABBITMQ_PORT=5672
- SPRING_RABBITMQ_USERNAME=guest
- SPRING_RABBITMQ_PASSWORD=guest
- CSV_OUTPUT_DIRECTORY={output_geracao_arquivo_csv}
- CSV_OUTPUT_FILENAME={nome_arquivo_csv}

## Subindo o Projeto
```bash
docker-compose up --build
```

## Acessando a Aplicação
- Frontend estará disponível em: http://localhost:80
- Backend estará disponível em: http://localhost:8080

## Rodando o Backend Separadamente (opcional)
```bash
docker run -d --name backend -p 8080:8080 scdecamargo/person-register-project/backend
```

## Rodando o Frontend Separadamente (opcional)
```bash
docker run -d --name backend -p 8080:8080 scdecamargo/person-register-project/frontend
```
