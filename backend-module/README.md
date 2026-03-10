# API de Gerenciamento de Benefícios

API REST para gerenciamento de benefícios e transferência de saldo entre benefícios.

## Índice

- [API de Gerenciamento de Benefícios](#api-de-gerenciamento-de-benefícios)
  - [Índice](#índice)
  - [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Documentação da API](#documentação-da-api)
- [Endpoints](#endpoints)
  - [Benefícios](#benefícios)
    - [Listar benefícios](#listar-benefícios)
    - [Buscar benefício por ID](#buscar-benefício-por-id)
    - [Criar benefício](#criar-benefício)
    - [Atualizar benefício](#atualizar-benefício)
    - [Remover benefício](#remover-benefício)
    - [Transferência entre benefícios](#transferência-entre-benefícios)

## Tecnologias utilizadas

- Java
- Spring Boot
- JPA / Hibernate
- Maven
- Swagger (OpenAPI)

# Documentação da API

A documentação interativa da API pode ser acessada pelo Swagger:

`http://localhost:8080/swagger-ui/index.html`

# Endpoints

## Benefícios

### Listar benefícios

Retorna todos os benefícios cadastrados.

- `GET /api/v1/beneficios`

### Buscar benefício por ID

Retorna o benefício pelo id.

- `GET /api/v1/beneficios/{id}`

Exemplo:

- `GET /api/v1/beneficios/1`

### Criar benefício

- `POST /api/v1/beneficios`

Exemplo de body:

```json
{
  "nome": "Vale alimentação",
  "descricao": "Benefício alimentação",
  "valor": 500.0,
  "ativo": true
}
```

### Atualizar benefício

- `PUT /api/v1/beneficios/{id}`

Exemplo:

- `PUT /api/v1/beneficios/1`

Body:

```json
{
  "nome": "Vale alimentação",
  "descricao": "Benefício alimentação",
  "valor": 550.0,
  "ativo": true
}
```

### Remover benefício

- `DELETE /api/v1/beneficios/{id}`

Exemplo:

- `DELETE /api/v1/beneficios/1`

### Transferência entre benefícios

Permite transferir saldo entre dois benefícios.

- `POST /api/v1/beneficios/transfer`

Parâmetros:

- fromId: ID do benefício de origem
- toId: ID do benefício de destino
- amount: valor da transferência

Exemplo:

- `POST /api/v1/beneficios/transfer?fromId=1&toId=2&amount=100`
