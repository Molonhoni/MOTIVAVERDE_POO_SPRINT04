# Motiva Verde — Sprint 4

API REST desenvolvida para a Sprint 4 do Challenge Motiva Verde, com o objetivo de migrar o sistema construído anteriormente com JDBC puro para uma arquitetura baseada em **Spring Boot, Spring Data JPA e REST**.

A aplicação reaproveita o banco Oracle e as tabelas criadas na Sprint 3, substituindo o acesso manual com `Connection`, `PreparedStatement` e `ResultSet` pelo uso de entidades JPA e interfaces `JpaRepository`.

---

## Integrantes

| Nome | RM |
|------|----|
| Arthur da Silva Alencar | 563684 |
| Felipe Paula Burba Molonhoni | 564395 |
| Lucas de Freitas Barbosa | 564685 |
| Pedro Del Neri Correia | 562168 |
| Vitor Limeira dos Santos | 565280 |


Turma: **2CCPX**

---

## Tecnologias utilizadas

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Bean Validation
- Maven
- Oracle Database
- Oracle JDBC `ojdbc17`
- H2 Database para testes
- JUnit 5
- MockMvc

---

## Arquitetura

O projeto segue uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Oracle
```

Estrutura principal:

```text
src/
├── main/
│   ├── java/
│   │   └── br/com/motivaverde/
│   │       ├── MotivaverdeApplication.java
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    ├── java/
    │   └── br/com/motivaverde/
    │       └── MotivaverdeApplicationTests.java
    │
    └── resources/
        └── application-test.properties
```

### Responsabilidade das camadas

**Model**

Contém as entidades JPA responsáveis pelo mapeamento das tabelas do banco Oracle.

**Repository**

Interfaces que estendem `JpaRepository`. O Spring Data gera automaticamente a implementação das operações de persistência.

**Service**

Contém as regras de negócio, validações e o motor de prioridade.

**Controller**

Expõe os endpoints HTTP da API REST.

**DTO**

Define os contratos de entrada e saída da API, evitando expor diretamente as entidades JPA nos principais recursos.

**Exception**

Centraliza as exceções e o tratamento global dos erros da API.

---

# Configuração do banco

A aplicação utiliza o banco Oracle da FIAP:

```text
oracle.fiap.com.br:1521:ORCL
```

As credenciais não são armazenadas no código-fonte.

O arquivo:

```text
src/main/resources/application.properties
```

utiliza variáveis de ambiente:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

Antes de executar a aplicação, configure:

### PowerShell

```powershell
$env:DB_USERNAME="SEU_USUARIO"
$env:DB_PASSWORD="SUA_SENHA"
```

Nenhuma senha real deve ser enviada ao GitHub.

---

# Sequences Oracle

Para atender à estratégia de geração de IDs utilizada pelo JPA, foram criadas as seguintes sequences:

```text
SEQ_EQUIPE_MANUTENCAO
SEQ_TRECHO_RODOVIA
SEQ_INTERVENCAO_OPERACIONAL
SEQ_RELATORIO_PRIORIDADE
```

As entidades utilizam:

```java
@GeneratedValue
@SequenceGenerator
```

para obtenção automática dos identificadores.

---

# Como executar

## 1. Verificar o Java

```bash
java -version
```

O projeto utiliza:

```text
Java 17
```

## 2. Compilar

Windows:

```powershell
./mvnw clean package -DskipTests
```

Resultado esperado:

```text
BUILD SUCCESS
```

## 3. Executar a API

```powershell
./mvnw spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

# Endpoints

## Equipes de manutenção

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| GET | `/api/equipes` | Lista todas as equipes | 200 |
| GET | `/api/equipes/{id}` | Busca uma equipe por ID | 200 / 404 |
| POST | `/api/equipes` | Cadastra uma equipe | 201 / 400 |
| PUT | `/api/equipes/{id}` | Atualiza uma equipe | 200 / 404 |
| DELETE | `/api/equipes/{id}` | Remove uma equipe | 204 / 404 |
| GET | `/api/equipes/especialidade?valor=...` | Busca por especialidade | 200 |

---

## Trechos rodoviários

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| GET | `/api/trechos` | Lista todos os trechos | 200 |
| GET | `/api/trechos/{id}` | Busca trecho por ID | 200 / 404 |
| POST | `/api/trechos` | Cadastra um trecho | 201 / 400 / 404 |
| PUT | `/api/trechos/{id}` | Atualiza um trecho | 200 / 404 |
| DELETE | `/api/trechos/{id}` | Remove um trecho | 204 / 404 |
| GET | `/api/trechos/altura-minima?valor=25` | Filtra por altura mínima da vegetação | 200 |
| GET | `/api/trechos/tipo?valor=MONITORADO` | Filtra por tipo | 200 |

---

## Intervenções operacionais

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| GET | `/api/intervencoes` | Lista todas as intervenções | 200 |
| GET | `/api/intervencoes/{id}` | Busca intervenção por ID | 200 / 404 |
| POST | `/api/intervencoes` | Registra uma intervenção | 201 / 400 / 404 |
| PUT | `/api/intervencoes/{id}` | Atualiza uma intervenção | 200 / 404 |
| DELETE | `/api/intervencoes/{id}` | Remove uma intervenção | 204 / 404 |
| GET | `/api/intervencoes/tipo?valor=ROCADA_MECANIZADA` | Filtra pelo tipo | 200 |
| GET | `/api/intervencoes/periodo?inicio=2026-09-01&fim=2026-09-30` | Consulta por período | 200 |

---

## Relatórios de prioridade

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| POST | `/api/relatorios` | Gera e persiste um novo relatório | 201 |
| GET | `/api/relatorios` | Lista o histórico de relatórios | 200 |
| GET | `/api/relatorios/periodo?inicio=2026-09-01&fim=2026-09-30` | Consulta relatórios por período | 200 / 400 |

---

# Exemplos cURL

## Listar equipes

```bash
curl -X GET http://localhost:8080/api/equipes
```

---

## Criar equipe

```bash
curl -X POST http://localhost:8080/api/equipes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Equipe Norte",
    "especialidade": "Rocada Mecanizada"
  }'
```

---

## Buscar equipe

```bash
curl -X GET http://localhost:8080/api/equipes/13
```

---

## Atualizar equipe

```bash
curl -X PUT http://localhost:8080/api/equipes/13 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Equipe Norte Atualizada",
    "especialidade": "Rocada Mecanizada"
  }'
```

---

## Excluir equipe

```bash
curl -X DELETE http://localhost:8080/api/equipes/13
```

---

## Criar trecho

O `equipeId` precisa corresponder a uma equipe existente.

```bash
curl -X POST http://localhost:8080/api/trechos \
  -H "Content-Type: application/json" \
  -d '{
    "quilometro": 72.5,
    "alturaVegetacao": 28.0,
    "condicaoCrescimento": "ALTO_CRESCIMENTO",
    "equipeId": 13,
    "tipoTrecho": "MONITORADO",
    "codigoSensor": "SENSOR-SPRINT4-001"
  }'
```

Exemplo de resposta:

```json
{
  "id": 1000,
  "quilometro": 72.5,
  "alturaVegetacao": 28.0,
  "condicaoCrescimento": "ALTO_CRESCIMENTO",
  "equipeId": 13,
  "equipeNome": "Equipe Norte",
  "tipoTrecho": "MONITORADO",
  "codigoSensor": "SENSOR-SPRINT4-001"
}
```

---

## Buscar trechos com vegetação a partir de 25 cm

```bash
curl -X GET "http://localhost:8080/api/trechos/altura-minima?valor=25"
```

Este endpoint utiliza uma derived query do Spring Data:

```java
findByAlturaVegetacaoGreaterThanEqual(Double minimo)
```

---

## Criar intervenção

```bash
curl -X POST http://localhost:8080/api/intervencoes \
  -H "Content-Type: application/json" \
  -d '{
    "trechoId": 1000,
    "tipoIntervencao": "ROCADA_MECANIZADA",
    "dataExecucao": "2026-09-28",
    "alturaAntes": 31.0,
    "alturaDepois": 10.0
  }'
```

Exemplo de resposta:

```json
{
  "id": 1000,
  "trechoId": 1000,
  "quilometroTrecho": 72.5,
  "tipoIntervencao": "ROCADA_MECANIZADA",
  "dataExecucao": "2026-09-28",
  "alturaAntes": 31.0,
  "alturaDepois": 10.0
}
```

---

## Consultar intervenções por período

```bash
curl -X GET "http://localhost:8080/api/intervencoes/periodo?inicio=2026-09-01&fim=2026-09-30"
```

---

## Gerar relatório de prioridade

```bash
curl -X POST http://localhost:8080/api/relatorios
```

O endpoint consulta todos os trechos atuais, aplica o motor de prioridade e persiste o resultado no histórico.

---

## Consultar histórico

```bash
curl -X GET http://localhost:8080/api/relatorios
```

---

## Consultar relatórios por período

```bash
curl -X GET "http://localhost:8080/api/relatorios/periodo?inicio=2026-09-01&fim=2026-09-30"
```

---

# Motor de prioridade

A classificação utilizada no projeto foi preservada das Sprints anteriores:

| Altura da vegetação | Prioridade |
|---|---|
| Até 15 cm | BAIXA |
| Acima de 15 cm e abaixo de 21 cm | MODERADA |
| De 21 cm até abaixo de 30 cm | ALTA |
| A partir de 30 cm | URGENTE |

A regra está implementada em:

```text
MotorPrioridadeService
```

A lógica de negócio não fica no Controller.

---

# Derived Queries

O projeto demonstra o recurso de derived queries do Spring Data.

Exemplos:

```java
List<TrechoRodovia> findByAlturaVegetacaoGreaterThanEqual(Double minimo);
```

```java
List<TrechoRodovia> findByTipoTrecho(TipoTrecho tipoTrecho);
```

```java
List<IntervencaoOperacional> findByTipoIntervencao(
        TipoIntervencao tipoIntervencao
);
```

```java
List<IntervencaoOperacional> findByDataExecucaoBetween(
        LocalDate inicio,
        LocalDate fim
);
```

```java
List<RelatorioPrioridade> findByDataGeracaoBetween(
        LocalDateTime inicio,
        LocalDateTime fim
);
```

O SQL dessas operações não é escrito manualmente pelo projeto. Ele é gerado pelo Spring Data JPA a partir dos nomes dos métodos.

---

# Validações

A API utiliza Bean Validation com anotações como:

```text
@NotNull
@NotBlank
@PositiveOrZero
@Size
@Valid
```

Também existem regras de negócio implementadas na camada `Service`.

Exemplo:

```text
alturaDepois <= alturaAntes
```

Uma intervenção em que a altura após a execução seja maior que a altura anterior é rejeitada com:

```text
HTTP 400 Bad Request
```

---

# Tratamento global de erros

A aplicação possui:

```text
@RestControllerAdvice
```

para centralizar o tratamento de exceções.

São tratados, entre outros casos:

```text
400 Bad Request
404 Not Found
erros de Bean Validation
JSON inválido
violações das regras de negócio
```

Exemplo de estrutura de erro:

```json
{
  "timestamp": "2026-09-28T13:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "A altura após a intervenção não pode ser maior que a altura anterior",
  "path": "/api/intervencoes",
  "campos": null
}
```

---

# Testes automatizados

Os testes utilizam:

```text
@SpringBootTest
MockMvc
JUnit 5
H2
```

O banco H2 é utilizado somente durante os testes, impedindo que os testes automatizados alterem os dados do Oracle.

Executar:

```bash
./mvnw test
```

Resultado obtido:

```text
Tests run: 5
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

Os testes verificam:

```text
Inicialização do contexto Spring
GET retornando HTTP 200
POST retornando HTTP 201
Bean Validation retornando HTTP 400
Recurso inexistente retornando HTTP 404
```

---

# Migração JDBC para JPA

Na Sprint 3, a persistência era feita manualmente com JDBC:

```text
Connection
PreparedStatement
ResultSet
SQL escrito manualmente
DAOs
```

Na Sprint 4, essa responsabilidade foi substituída por:

```text
@Entity
JpaRepository
Spring Data JPA
Hibernate
```

Exemplo conceitual:

### Sprint 3

```java
PreparedStatement stmt =
        connection.prepareStatement(
                "INSERT INTO ..."
        );

stmt.executeUpdate();
```

### Sprint 4

```java
repository.save(entidade);
```

Não existem `Connection`, `PreparedStatement` ou `ResultSet` na implementação da Sprint 4.

---

# Perguntas de reflexão

## 1. Por que o Repository é uma interface e não uma classe? Quem escreve a implementação e quando?

O Repository é uma interface porque utilizamos o Spring Data JPA. Nós declaramos apenas o contrato de acesso aos dados, estendendo interfaces como `JpaRepository`.

A implementação concreta não precisa ser escrita manualmente pela equipe. O próprio Spring cria essa implementação em tempo de execução durante a inicialização da aplicação.

Por isso conseguimos utilizar métodos como:

```java
save()
findAll()
findById()
deleteById()
```

sem criar manualmente uma classe DAO para implementá-los.

---

## 2. O pattern DAO da Sprint 3 morreu na migração ou apenas mudou de forma?

O conceito não morreu. Ele mudou de forma.

Na Sprint 3, criamos manualmente classes DAO responsáveis por conversar com o banco usando JDBC, conexão, SQL e `PreparedStatement`.

Na Sprint 4, essa responsabilidade continua existindo, mas é abstraída pelo Spring Data JPA através dos Repositories.

Assim, o Repository exerce o mesmo papel arquitetural de acesso aos dados, porém grande parte da implementação é gerada automaticamente pelo framework.

---

## 3. Por que a validação de `nivelVegetacao >= 0` deve ficar no Service e não no Controller?

O Controller deve ser responsável principalmente pelo protocolo HTTP: receber requisições, interpretar parâmetros e devolver respostas.

A regra que determina se um valor é válido para o domínio pertence à lógica de negócio.

Ao manter essa regra no Service, ela pode ser reutilizada independentemente de onde a chamada venha, além de manter uma separação clara entre a camada HTTP e as regras da aplicação.

O projeto também utiliza Bean Validation nos DTOs para validar o contrato de entrada, enquanto as regras de negócio permanecem na camada Service.

---

## 4. No JDBC puro escrevíamos SQL. Onde está o SQL do `findByTipo()`? Quem o gerou?

O SQL não está escrito diretamente no código do projeto.

Métodos como:

```java
findByTipoTrecho(...)
```

ou:

```java
findByTipoIntervencao(...)
```

seguem a convenção de nomes do Spring Data.

O framework interpreta o nome do método, analisa a entidade e seus atributos e gera automaticamente a consulta necessária através do JPA/Hibernate.

Assim, a aplicação consegue realizar a consulta sem SQL escrito manualmente.

---

# Boas práticas implementadas

O projeto utiliza:

```text
Arquitetura em camadas
Spring Data JPA
DTOs de entrada e saída
Bean Validation
Tratamento global de erros
Derived Queries
Variáveis de ambiente para credenciais
Testes automatizados com MockMvc
Banco H2 isolado para testes
Commits incrementais
.gitignore
```

---

# Segurança

Credenciais do Oracle não são versionadas.

O projeto utiliza:

```text
${DB_USERNAME}
${DB_PASSWORD}
```

As credenciais devem ser fornecidas localmente através de variáveis de ambiente.

Arquivos de build como:

```text
target/
```

também não são enviados ao repositório.

---

# Status da Sprint

Funcionalidades implementadas:

```text
CRUD REST de Equipes
CRUD REST de Trechos
CRUD REST de Intervenções
Motor de prioridade
Geração e persistência de relatórios
Histórico de relatórios
Consulta de relatórios por período
Derived Queries
Bean Validation
DTOs
Tratamento global de erros
Testes automatizados
Persistência Oracle com Spring Data JPA
```

Projeto desenvolvido para o Challenge Motiva Verde — FIAP.