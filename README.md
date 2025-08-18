# VolunteerSmile - Backend

Backend do projeto VolunteerSmile, uma plataforma para conectar voluntários a pacientes em hospitais, promovendo acolhimento e bem-estar.

## Como Rodar o Projeto

Para rodar o backend localmente, siga os passos abaixo:

1.  **Pré-requisitos**:
    *   Java 21 ou superior
    *   Maven 3.x

3.  **Execute a aplicação**:
    Por enquanrto o projeto está configurado para usar o banco de dados em memória H2, então não é necessário configurar um banco externo para testes.

    *   No terminal, dentro da pasta `volunteersmile-backend/volunteersmile`, execute:
        ```sh
        ./mvnw spring-boot:run
        ```
    *   No Windows PowerShell:
        ```powershell
        .\mvnw.cmd spring-boot:run
        ```

4.  **Acesse os serviços**:
    *   **Swagger UI** (documentação da API): `http://localhost:8080/swagger-ui.html`

## Como Contribuir

### Nomenclatura de Branches

Crie suas branches a partir da `dev`, seguindo o padrão:

Formato: `<tipo>/<descrição>/<issue ID>`

*   **feature/**: Para novas funcionalidades.
    *   Exemplo: `feature/cadastro-de-voluntarios/86b64ef8m`
*   **fix/**: Para correções de bugs.
    *   Exemplo: `fix/erro-login-invalido/86b64ef8m`
*   **docs/**: Para melhorias na documentação.
    *   Exemplo: `docs/atualizar-readme/86b64ef8m`
*   **chore/**: Para tarefas de manutenção (atualização de dependências, etc.).
    *   Exemplo: `chore/atualizar-spring-boot/86b64ef8m`

### Padrão de Commits

Utilize o padrão de *Conventional Commits* para as mensagens de commit. Isso ajuda a gerar changelogs automaticamente e a manter o histórico limpo.

O formato é: `<tipo>(<escopo>): <descrição>`

*   **Tipos comuns**:
    *   `feature`: Uma nova funcionalidade.
    *   `fix`: Uma correção de bug.
    *   `docs`: Alterações na documentação.
    *   `style`: Alterações de formatação que não afetam o código.
    *   `refactor`: Refatoração de código sem alterar a funcionalidade.
    *   `test`: Adição ou correção de testes.
    *   `chore`: Tarefas de build, dependências, etc.

*   **Exemplos**:
    ```
    feature(auth): adiciona login com email e senha
    fix(api): corrige erro 500 ao listar voluntários
    docs(readme): atualiza instruções de como rodar o projeto
    ```

### Pull Requests (PRs)

1.  Crie sua branch a partir da `dev`.
2.  Faça seus commits seguindo o padrão.
3.  Abra um Pull Request para a `dev`.
4.  Descreva as alterações feitas no PR.
5.  Aguarde a revisão de pelo menos dois AGES III.
