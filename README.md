# url-shortener
Um projeto simples para encurtar urls feito com Angular + Spring.

- IDEs utilizadas: Visual Studio Code e Spring Tool Suit 4
- Tecnologia de back-end: Java e Spring Boot com Maven
- Tecnologia de front-end: Angular
- SGBD utilizado: PostgreSQL

**Front-end:** Pra rodar o front, é necessário entrar na pasta url-shortener-frontend e fazer `npm i`, após a instalação, `npm start`.

**Back-end:** Não é preciso de script para rodar o projeto, o flyway está configurado no projeto e já vai rodar os
arquivos SQL direto no banco. (src/main/resources/db/migration).

Pra rodar o backend, só é necessário abrir o projeto no Eclipse ou STS que o Maven vai baixar as dependências,
depois basta rodar o método main do projeto que ele já possui o Tomcat embutido.

**Banco de dados:** 
Configurações:

- username: postgres
- password: postgres
- nome do banco: url_shortener
- porta: 5432

A tabela user vai ter dois usuários: maria e joao, ambos possuem a senha 123456.
