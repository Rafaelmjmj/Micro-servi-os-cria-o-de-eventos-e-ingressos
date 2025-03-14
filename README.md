# MS Ticket Manager

## Descrição
O **MS Ticket Manager** é um microsserviço responsável pela gestão de tickets para eventos. Ele permite criar, buscar, atualizar e deletar tickets, armazenando os dados em um banco de dados **MongoDB**.

# MS Event Management

## Descrição
O **MS Event Management** é um microsserviço responsável pela gestão de eventos, como shows, teatros, concertos e exposições. Ele permite criar, buscar, atualizar e deletar eventos, além de armazenar dados em um banco de dados **MongoDB** e integrar-se à API ViaCEP para buscar informações de endereço a partir do CEP.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.3**
- **MongoDB**
- **Maven**
- **Lombok**
- **OpenFeign**
- **Swagger**

## Instalação e Configuração
### 1. Clonar o Repositório
```sh
git clone https://github.com/seu-usuario/ms-event-management.git
cd ms-event-management
```

### 2. Configurar o MongoDB
Certifique-se de que o MongoDB está rodando localmente ou defina as variáveis de ambiente no `application.yml`:
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://admin:admin@localhost:27017/db_event
```

### 3. Compilar e Executar o Projeto
```sh
mvn clean install
mvn spring-boot:run
```

## Endpoints da API
### Criar um Evento
```http
POST /events
```
#### Exemplo de JSON:
```json
{
  "name": "Show do Coldplay",
  "date": "2024-11-15",
  "time": "20:00",
  "cep": "87654321"
}
```

### Buscar um Evento por ID
```http
GET /events/{id}
```

### Listar Todos os Eventos
```http
GET /events
```

### Atualizar um Evento
```http
PUT /events/{id}
```

### Deletar um Evento
```http
DELETE /events/{id}
```

## Testes
Para executar os testes automatizados:
```sh
mvn test
```

## Implantando na AWS EC2
### Configuração da AWS
1. **Criar VPC (Virtual Private Cloud):**
   - Acesse o console AWS e vá até **VPC**.
   - Crie uma nova VPC com um bloco CIDR (ex: `10.0.0.0/16`).
   - Crie uma Subnet pública (ex: `10.0.1.0/24`).
   - Configure um Internet Gateway e associe-o à VPC.
   - Atualize a Tabela de Rotas da VPC para direcionar o tráfego externo para o Internet Gateway.

2. **Criar Security Group:**
   - Crie um Security Group permitindo:
     - Porta 22 (SSH) – Acesso ao seu IP.
     - Porta 8080 – Acesso público (ou restrito, conforme necessidade).
     - Porta 27017 – Se for necessário acessar o MongoDB remotamente.

3. **Criar Key Pair SSH:**
   - No console EC2, vá até **Key Pairs** e crie uma nova chave.
   - Faça o download do arquivo `.pem` gerado, pois ele será necessário para conexão via SSH.

4. **Criar Instância EC2:**
   - Escolha uma AMI, como **Amazon Linux 2023** ou **Ubuntu**.
   - Associe à VPC criada e à Subnet pública.
   - Anexe o Security Group configurado.
   - Escolha a Key Pair SSH criada.

### Deploy do MS Event Management
1. Gere o `.jar` do projeto:
```sh
mvn package
```
2. Transfira o arquivo para a EC2:
```sh
scp -i "sua-chave.pem" target/ms-event-management-0.0.1-SNAPSHOT.jar ec2-user@seu-ip:/home/ec2-user/
```
3. Conecte-se via SSH na instância EC2:
```sh
ssh -i "sua-chave.pem" ec2-user@seu-ip
```
4. Execute o serviço:
```sh
java -jar ms-event-management-0.0.1-SNAPSHOT.jar
```

# MS Ticket Manager

## Descrição
O **MS Ticket Manager** é um microsserviço responsável pela gestão de tickets para eventos. Ele permite criar, buscar, atualizar e deletar tickets, armazenando os dados em um banco de dados **MongoDB**.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.3**
- **MongoDB**
- **Maven**
- **Lombok**

## Instalação e Configuração
### 1. Clonar o Repositório
```sh
git clone https://github.com/seu-usuario/ms-ticket-manager.git
cd ms-ticket-manager
```

### 2. Configurar o MongoDB
Certifique-se de que o MongoDB está rodando localmente ou defina as variáveis de ambiente no `application.properties`:
```properties
spring.data.mongodb.uri=mongodb://admin:admin@localhost:27017/db_ticket
```

### 3. Compilar e Executar o Projeto
```sh
mvn clean install
mvn spring-boot:run
```

## Endpoints da API
### Criar um Ticket
```http
POST /tickets
```
#### Exemplo de JSON:
```json
{
  "eventName": "Tech Conference",
  "dateTime": "2024-10-05T14:00:00",
  "cep": "12345678"
}
```

### Buscar um Ticket por ID
```http
GET /tickets/{id}
```

### Buscar Tickets por CPF
```http
GET /tickets/cpf/{cpf}
```

### Cancelar um Ticket
```http
DELETE /tickets/{id}
```

## Testes
Para executar os testes automatizados:
```sh
mvn test
```

## Implantando na AWS EC2
1. Gere o `.jar` do projeto:
```sh
mvn package
```
2. Transfira o arquivo para a EC2:
```sh
scp -i "sua-chave.pem" target/ms-ticket-manager-0.0.1-SNAPSHOT.jar ec2-user@seu-ip:/home/ec2-user/
```
3. Conecte-se via SSH na instância EC2:
```sh
ssh -i "sua-chave.pem" ec2-user@seu-ip
```
4. Execute o serviço:
```sh
java -jar ms-ticket-manager-0.0.1-SNAPSHOT.jar
```

## Contribuição
Sinta-se à vontade para contribuir abrindo um pull request!

## Autor
[Rafael Machado](https://github.com/seu-usuario)

