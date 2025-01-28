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
* [Como utilizar o projeto](#como-utilizar-o-projeto)
  * [Clonando o Repositório](#clonando-o-repositório)
  * [Subindo o ambiente com Docker](#subindo-o-ambiente-com-docker)
    * [Pré-requisitos](#pré-requisitos)
    * [Configurando o Docker](#configurando-o-docker)
    * [Subindo o Projeto](#subindo-o-projeto)
    * [Acessando a aplicação](#acessando-a-aplicação)
    * [Rodando o Backend Separadamente (opcional)](#rodando-o-backend-separadamente-opcional)
    * [Rodando o Frontend Separadamente (opcional)](#rodando-o-frontend-separadamente-opcional)
  * [Subindo o ambiente com npm e maven](#subindo-o-ambiente-com-npm-e-maven)
    * [Pré-requisitos](#pré-requisitos-1)
    * [Configurando o Ambiente](#configurando-o-ambiente)
    * [Subindo o Projeto Frontend](#subindo-o-projeto-frontend)
    * [Subindo o Projeto Backend](#subindo-o-projeto-backend)
    * [Acessando a aplicação](#acessando-a-aplicação-1)
* [Testando a Aplicação com JUnit](#testando-a-aplicação-com-junit)
* [Pontos de Melhoria do Projeto](#pontos-de-melhoria-do-projeto)

# Funcionalidades

- `Gerenciamento de pessoas`: Adição, edição, remoção e listagem de pessoas físicas.
- `Geração de arquivo CSV`: Geração de arquivo CSV contendo todos os dados das pessoas cadastradas.]

# Estrutura do Repositório

O repositório está dividido em duas pastas principais:

- `frontend`: Aplicação frontend construída com React.
- `backend`: Aplicação backend construída com Java Spring Boot.

# Como utilizar o projeto

## Clonando o Repositório

Clone o repositório para sua máquina local:

```bash
git clone https://github.com/sarahCamargo/person-register-project.git
cd person-register-project
```

## Subindo o ambiente com Docker

### Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)
  
Cada pasta possui seu próprio Dockerfile para criação de containers separados para frontend e backend.

- [Docker Hub](https://hub.docker.com/r/scdecamargo/person-register-project/tags)

### Configurando o Docker

Atualize as configurações no arquivo [docker-compose.yml](https://github.com/sarahCamargo/person-register-project/blob/main/docker-compose.yml) na raiz do projeto conforme necessário.

Configurações Postgres
- SPRING_DATASOURCE_URL=jdbc:postgresql://{usuario_postgres}:{porta}/persondb
- SPRING_DATASOURCE_USERNAME={usuario_postgres}
- SPRING_DATASOURCE_PASSWORD={senha}

Configurações RabbitMQ
- SPRING_RABBITMQ_HOST=rabbitmq
- SPRING_RABBITMQ_PORT=5672
- SPRING_RABBITMQ_USERNAME=guest
- SPRING_RABBITMQ_PASSWORD=guest

Configurações Diretório e Arquivo CSV
- CSV_OUTPUT_DIRECTORY={output_geracao_arquivo_csv}
- CSV_OUTPUT_FILENAME={nome_arquivo_csv}

### Subindo o Projeto
```bash
docker-compose up --build
```

### Acessando a Aplicação
- Frontend estará disponível em: http://localhost:80
- Backend estará disponível em: http://localhost:8080

### Rodando o Backend Separadamente (opcional)
```bash
docker run -d --name backend -p 8080:8080 scdecamargo/person-register-project/backend
```

### Rodando o Frontend Separadamente (opcional)
```bash
docker run -d --name backend -p 8080:8080 scdecamargo/person-register-project/frontend
```

## Subindo o ambiente com npm e maven

### Pré-requisitos

- [Node.js](https://nodejs.org/pt)
- [npm](https://www.npmjs.com/)
- [Java JDK 17](https://www.oracle.com/java/technologies/downloads/#java17?er=221886)
- [PostgreSQL](https://www.postgresql.org/)
- [Erlang](https://www.erlang.org/downloads)
- [RabbitMQ](https://www.rabbitmq.com/)

### Configurando o Ambiente

No arquivo [application.properties](https://github.com/sarahCamargo/person-register-project/blob/main/backend/src/main/resources/application.properties) configure as seguintes propriedades conforme necessário:

Configurações RabbitMQ
- spring.rabbitmq.host=localhost
- spring.rabbitmq.port=5672
- spring.rabbitmq.username=guest
- spring.rabbitmq.password=guest

Configurações Postgres
- spring.datasource.url=jdbc:postgresql://localhost:5432/persondb?createDatabaseIfNotExists=true
- spring.datasource.username=postgres
- spring.datasource.password=admin

Configurações Diretório e Arquivo CSV
- csv.output.directory=${CSV_OUTPUT_DIRECTORY:output}
- csv.output.filename=${CSV_OUTPUT_FILENAME:person-report.csv}

### Subindo o Projeto Frontend
```bash
cd frontend
npm install
npm start
```

### Subindo o Projeto Backend
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

### Acessando a Aplicação

- Frontend estará disponível em: http://localhost:3000
- Backend estará disponível em: http://localhost:8080

# Testando a Aplicação com JUnit
```bash
./mvnw test
```

# Pontos de Melhoria do Projeto

<p> A primeira versão do projeto conta com uma API completa de registros de pessoas (adição, edição, remoção e listagem). </p>
<p> Bem como possui uma interface gráfica para o usuário.</p>

<p> Porém, é possível adicionar incrementar o projeto com diversas melhorias futuras, afim de deixar a interface mais amigável ao usuário e documentação da API.</p>

- Para <b>documentação da API</b>, a proposta seria utilizar o [Swagger](https://swagger.io/) para tal funcionalidade, visando simplificar a utilização da API.
- Para a <b>interface gráfica do projeto</b>, seria interessante utilizar a API para aplicar filtros na tabela e paginação, pensando em um cenário com grande volume de dados, além da <b>ordenação</b> dos campos.
- Ainda para a interface gráfica, adicionar <b>carregamentos com spinners ou placeholders</b> enquanto o backend carrega a requisição, para melhor feeback visual.
- No modal de adição e edição, seria interessate para o usuário que alguns campos possuíssem <b>máscara para visualização</b>.
- Em um cenário no qual vários usuários acessam o sistema, o ideal seria utilizar a <b>autenticação dos usuários</b>, visando um ambiente mais seguros utilizando <b>Spring Security e JWT</b>.

<p> Essas e outras melhorias, podem ser incrementadas no projeto futuramente.</p>
<p> Além disso, sinta-se a vontade para enviar feedbacks positivos e negativos para a aplicação.</p>
