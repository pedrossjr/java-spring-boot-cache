## 📚 Aumentando a performance da aplicação com cache

### Descrição do projeto

API REST desenvolvida em Java com Spring Boot, que simula uma listagem de 
produtos. Na primeira consulta, os dados são carregados diretamente do 
banco de dados. A partir daí, as informações são armazenadas em cache e 
atualizadas automaticamente a cada 15 segundos. Durante esse intervalo, 
as requisições utilizam os dados em cache, otimizando o desempenho da 
aplicação.

### 🔧 Tecnologias utilizadas

- Java
- Spring Boot
- Spring Cache
- Redis
- Swagger
- Maven
- PostgreSQL
- Docker

### ⚙️ Ferramentas

- IntelliJ IDEA (Community Edition)
- PgAdmin4
- Postman
- Git
- GitHub
- Docker Desktop 

### 📚 Cache na teoria

**Definição de cache**

- Área de memória onde é mantida uma cópia temporária de dados armazenados em um meio de acesso mais lento, com o objetivo de acelerar a recuperação dos dados.

**Quando utilizar cache?**

- Em dias de grandes volumes de acessos a aplicação, por exemplo Black Friday.

**Políticas de Leitura**

Cache A-Side

1. Executa a leitura no cache.
2. Executa a leitura no SGBD.
3. Grava o resultado da leitura do SGBD no cache.

Read-Through

1. Executa a leitura no cache.
2. Executa a leitra no SGBD.

**Políticas de Escrita**

Write-Through

1. Executa a escrita no cache. (Síncrono)
2. Executa a escrita no SGBD. (Síncrono)

Write-Back (Write-Behind)

1. Executa a escrita no cache. (Síncrono)
2. Executa a escrita no SGBD. (Assíncrono)

Write-Around

1. Executa a escrita no SGBD
2. Executa a leitura no cache
3. Executa a leitura no SGBD (Se não existir no cache, em seguida persiste a escrita no cache)

**Tipos de Cache**

- Local - container ou servidor local.
- Distribuído - Redis, Memcached, ElastiCache, Hazelcast, outros...

### 🛠️ Configurando o projeto

#### 1. Pré-requisitos

- Java 17+
- IDE de sua preferência
- Maven instalado
- Postman para testes dos endpoints
- Docker Desktop para uso do PostgreSQL, PgAdmin4 e Redis

#### 2. Clonar o repositório

```
- git clone https://github.com/pedrossjr/spring-boot-cache.git
- cd cache-api
``` 

#### 3. Criação do container para uso do PostgreSQL e PgAdmin

```
- cd cache-api
- docker-compose up -d
``` 

#### 4. Acesse o pgAdmin

- Abra o navegador e acesse:

🔗 http://localhost:5050

- Faça login com as credenciais que definiu no docker-compose:
```
Email: admin@admin.com
Senha: admin
```

#### 5. Registrar um novo servidor

- Na janela de Dashboard, clique em "Add New Server".
- Aba "General"
- Name: Pode ser qualquer nome, ex: PostgreSQL Local
- Aba "Connection"
- Host name/address: postgres
- Port: 5432
- Maintenance database: postgres (ou db_cache, se já tiver sido criado)
- Username: postgres
- Password: postgres
- Marque a opção "Save Password"
- Clique em Save

#### 6. Criar um novo banco de dados

- Após registrar o servidor, expanda ele na lateral esquerda.
- Clique com o botão direito em Databases → Create > Database...
- Database name: ex: db_cache
- Owner: postgres (ou outro usuário, se quiser)
- Clique em Save

#### 7. Executando a aplicação:

- Execute o projeto com sua IDE de preferência através do botão __Run__ 
ou na raiz da aplicação, digite o comando abaixo:

```
./mvnw clean spring-boot:run
``` 

### 🛠️ Testando a alicação

#### 1. Aplicação estará disponível em:

🔗 http://localhost:8080

#### 2. Exemplo de Endpoint

#### 📘 Livros

- POST - produto/add - Cadastra um novo registro
- GET - produto/list - Lista todos os registros
- GET - produto/{id}/list - Consulta um registro por Id
- PUT - produto/{id}/update - Atualiza um registro existente
- GET - cache/clear - Limpa o cache

#### 3. PgAdmin estará disponível em

🔗 http://localhost:5050

#### 4. Documentação Swagger estará disponível em:

🔗 http://localhost:8080/cache/docs/api

#### 5. Acessando o Redis no container Docker

```
$> docker exec -it redis bash >>>>> Acessa o container docker
$> root@2b69ef6056b5:/data# redis-cli >>>>> Acessa a cli do Redis
$> 127.0.0.1:6379> KEYS * >>>>> Lista todas as chaves existentes
```

#### Se o container Redis tivesse uma senha, para acessá-lo seria necessário digita o comando abaixo. 

```
$> root@2b69ef6056b5:/data# redis-cli
$> 127.0.0.1:6379> AUTH password
```

#### Para deletar um cache dentro do redis, digite o comando abaixo:

```
$> root@2b69ef6056b5:/data# redis-cli
$> 127.0.0.1:6379> DEL "cache-name::SimpleKey[]"
```

### Annotations

- **@EnableCaching** - Ativa o suporte a cache no Spring Boot, na classe 
principal da aplicação (@SpringBootApplication) ou em uma classe 
de configuração.


- **@Cacheable** - Armazena o resultado de um método no cache. Se o método 
for chamado novamente com os mesmos parâmetros, o valor é retornado 
diretamente do cache, sem executar o método novamente.


- **@CacheEvict** - Remove entradas do cache. Usado quando o dado muda 
(inserção, atualização, remoção) e o cache precisa ser invalidado.

### 🙋‍♂️ Issues e sugestões

Fique à vontade para abrir issues e fazer sugestões ao projeto. Melhorias
sempre são bem-vindas e meu objetio é compartilhar conhecimento com a
linguagem Java!

