# Baozi Store API

API REST para gerenciamento de clientes, produtos e pedidos de uma loja.

## Stack
- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- H2 Database (em memoria)
- Maven Wrapper

## Pre-requisitos
- Java 17 ou superior
- Git

## Como executar
```bash
# na raiz do projeto
./mvnw clean spring-boot:run
```

A aplicacao sobe em: `http://localhost:8080`

## Build do projeto
```bash
./mvnw clean package
```

Jar gerado em `target/api-0.0.1-SNAPSHOT.jar`.

## Banco de dados (H2)
Configurado como banco em memoria:
- URL JDBC: `jdbc:h2:mem:db`
- Usuario: `sa`
- Senha: (vazia)
- Console H2: `http://localhost:8080/h2-console`

## Endpoints
### Clientes (`/clientes`)
- `GET /clientes` - lista todos
- `GET /clientes/{id}` - busca por id
- `POST /clientes` - cadastra cliente
- `PUT /clientes/{id}` - atualiza cliente
- `DELETE /clientes/{id}` - remove cliente

### Produtos (`/produtos`)
- `GET /produtos` - lista todos
- `GET /produtos/{id}` - busca por id
- `POST /produtos` - cadastra produto
- `PUT /produtos/{id}` - atualiza produto
- `DELETE /produtos/{id}` - remove produto

### Pedidos (`/pedidos`)
- `GET /pedidos` - lista todos
- `GET /pedidos/{id}` - busca por id
- `POST /pedidos` - cria pedido (usa DTO)
- `PUT /pedidos/{id}` - atualiza pedido
- `DELETE /pedidos/{id}` - remove pedido

## Exemplo rapido: criar pedido
Antes, cadastre um cliente e um produto para obter os IDs.

Requisicao:
```http
POST /pedidos
Content-Type: application/json

{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 2
}
```

Resposta esperada (exemplo):
```json
{
  "idPedido": 1,
  "quantidade": 10,
  "nomeCliente": "duZAO",
  "nomeProduto": "Pãozinho Chinês"
}
```

## Estrutura do projeto
- `src/main/java/com/baozistore/api/controller` - endpoints REST
- `src/main/java/com/baozistore/api/model` - entidades JPA
- `src/main/java/com/baozistore/api/repository` - repositorios
- `src/main/java/com/baozistore/api/dto` - objetos de entrada/saida para pedido
- `src/main/resources/application.properties` - configuracoes da aplicacao
