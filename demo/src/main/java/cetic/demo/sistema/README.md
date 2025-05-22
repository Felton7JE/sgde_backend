# SGDE – Módulo Sistema

Este módulo faz parte do Sistema de Gestão de Equipamentos (SGDE) e é responsável pelo controle de equipamentos, manutenções, avarias, alocações, empréstimos e requisições.

---

## Visão Geral
O módulo **sistema** centraliza as operações de gestão de equipamentos, incluindo:
- Cadastro, consulta e atualização de equipamentos
- Registro e acompanhamento de manutenções
- Gerenciamento de avarias
- Controle de alocações e empréstimos
- Processamento de requisições

## Estrutura de Pastas
```
sistema/
├── controler/    # Controllers REST
├── dto/          # Data Transfer Objects
├── entidade/     # Entidades JPA
├── enums/        # Enumerações
├── repository/   # Repositórios Spring Data
├── services/     # Serviços de negócio
```

## Principais Endpoints
- `POST   /sistema/equipamento`         – Cadastrar equipamento
- `GET    /sistema/equipamento/listar`  – Listar equipamentos
- `PUT    /sistema/equipamento`         – Atualizar equipamento
- `POST   /sistema/manutencao`          – Registrar manutenção
- `PUT    /sistema/manutencao`          – Atualizar manutenção
- `POST   /sistema/manutencao/porEquipamento` – Listar manutenções por equipamento
- `POST   /sistema/avaria`              – Registrar avaria
- `PUT    /sistema/avaria`              – Atualizar avaria
- `POST   /sistema/alocacao`            – Alocar equipamento
- `POST   /sistema/emprestimo`          – Registrar empréstimo
- `POST   /sistema/requisicao`          – Nova requisição

## Exemplos de Requisições

### Criar uma manutenção
```http
POST /sistema/manutencao
Content-Type: application/json
{
  "numeroSerie": "12345",
  "tipoManutencao": "Preventiva",
  "descricaoManutencao": "Troca de filtro",
  "responsavel": "João Silva",
  "tempoInatividade": 2
}
```

### Concluir uma manutenção
```http
PUT /sistema/manutencao
Content-Type: application/json
{
  "numeroSerie": "12345",
  "status": "CONCLUIDO"
}
```

### Listar manutenções de um equipamento
```http
POST /sistema/manutencao/porEquipamento
Content-Type: application/json
{
  "numeroSerie": "12345"
}
```

## Requisitos
- Java 17+
- Spring Boot 3+
- Banco de dados relacional ( MySQL)

## Configuração e Execução
1. Configure o banco de dados em `src/main/resources/application.properties`.
2. Compile o projeto:
   ```shell
   ./mvnw clean install
   ```
3. Execute a aplicação:
   ```shell
   ./mvnw spring-boot:run
   ```
4. Utilize ferramentas como Postman ou Insomnia para consumir os endpoints REST.


## Suporte
Em caso de dúvidas, sugestões ou problemas, entre em contato comigo .
