# Aplicativo Catalogo

## Configuracao do PostgreSQL
Antes de rodar o projeto, verifique se o PostgreSQL esta instalado e iniciado.

No arquivo `src/main/resources/application.properties`, ajuste estas linhas conforme o banco da sua maquina:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=123456
spring.datasource.driver-class-name=org.postgresql.Driver
```

### O que alterar
- `localhost`: mantenha assim se o PostgreSQL estiver em sua maquina.
- `5432`: porta padrao do PostgreSQL.
- `postgres`: nome do banco. Troque se voce criou outro banco.
- `spring.datasource.username`: usuario do PostgreSQL.
- `spring.datasource.password`: senha do usuario.

### Exemplo usando um banco chamado `catalogo`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/catalogo
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
```

Depois disso, rode a aplicacao. O Spring cria/atualiza as tabelas automaticamente por causa desta configuracao:

```properties
spring.jpa.hibernate.ddl-auto=update
```

## Usuarios criados automaticamente ao iniciar
- `userAdmin`: `admin/admin123`
