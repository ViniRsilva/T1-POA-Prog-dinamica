# VolunteerSmile API

## 🚀 Começando

Siga estas instruções para configurar e rodar o projeto no seu ambiente de desenvolvimento local.

### 1. Pré-requisitos

Antes de começar, garanta que tem os seguintes softwares instalados:

- **Git:** Para clonar o repositório.
- **JDK 21 (ou superior):** O Java Development Kit para rodar a aplicação Spring.
- **Docker Desktop:** Para rodar o banco de dados PostgreSQL. Certifique-se de que está em execução.
- **Maven:** Para gerir as dependências e o build do projeto.
- **IDE de sua preferência:** IntelliJ IDEA ou VS Code com o "Extension Pack for Java".

### 2. Clonar o Repositório

Abra um terminal e clone o projeto para a sua máquina:

```bash
git clone https://tools.ages.pucrs.br/volunteer-smile/volunteersmile-backend.git
cd volunteersmile-backend
```

## 📜 Padrões de Desenvolvimento

Para manter a organização e a clareza do nosso repositório, seguimos os seguintes padrões para a criação de branches e a escrita de mensagens de commit.

### Padrão de Branches

Toda nova branch deve seguir o formato abaixo, utilizando o ID da task (do Jira, Trello, etc.) como referência.

**Formato:** `tipo/<id_da_task>-breve-descricao`

**Tipos de Branch:**

- **feat:** Para o desenvolvimento de novas funcionalidades (`features`).
- **fix:** Para a correção de bugs.
- **chore:** Para tarefas de manutenção que não alteram o código de produção (ex: atualizar dependências, configurar CI/CD).
- **docs:** Para alterações na documentação.
- **refactor:** Para refatorações de código que não alteram o comportamento externo.

**Exemplo:**

```bash
git checkout -b feat/86b6cx1xp-create-postgre-db
```

### Padrão de Commits

Utilizamos o padrão **Conventional Commits** para as nossas mensagens de commit. Isso nos ajuda a ter um histórico claro e a automatizar a geração de changelogs no futuro.

**Formato:** `tipo: descrição curta em letra minúscula`

**Tipos de Commit:**

- `feat`: Adiciona uma nova funcionalidade.
- `fix`: Corrige um bug.
- `chore`: Atualizações de tarefas de build, pacotes, etc.
- `docs`: Alterações na documentação.
- `style`: Alterações de formatação, espaços em branco, etc. (sem impacto no código).
- `refactor`: Refatoração de código.
- `test`: Adição ou modificação de testes.
- `ci`: Mudanças nos arquivos de CI/CD.

**Exemplo:**

```bash
git commit -m "feat: add user login endpoint"
```

### 3. A Configurar o Ambiente Local

O projeto utiliza dois arquivos locais para armazenar credenciais e configurações sensíveis. Estes arquivos não são versionados no Git, por isso precisa de os criar manualmente.

**A. Crie o arquivo `.env`**

Este arquivo configura o contentor do banco de dados.

1.  Na raiz do projeto (`volunteersmile-backend`), crie um arquivo chamado `.env`.
2.  Adicione o seguinte conteúdo a ele:
    ```
    # Credenciais para o contentor do PostgreSQL
    POSTGRES_USER=postgres
    POSTGRES_PASSWORD=postgres
    POSTGRES_DB=volunteersmile_db
    ```

**B. Crie o arquivo `application-local.properties`**

Este arquivo configura a sua aplicação Spring para se conectar ao banco de dados.

1.  Navegue até à pasta `src/main/resources`.
2.  Crie um novo arquivo chamado `application-local.properties`.
3.  Adicione o seguinte conteúdo, garantindo que as credenciais sejam **idênticas** às do arquivo `.env`:
    `properties
    # Credenciais para o Banco de Dados a rodar no Docker
    spring.datasource.url=jdbc:postgresql://localhost:5432/volunteersmile_db
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    spring.sql.init.mode=never
    spring.jpa.defer-datasource-initialization=false
    `

### 4. A Subir o Banco de Dados

Com o Docker Desktop em execução, abra um terminal na raiz do projeto e execute o comando:

```bash
docker-compose up -d
```

Este comando irá descarregar a imagem do PostgreSQL (apenas na primeira vez) e iniciar o contentor do banco de dados em segundo plano. Para verificar se o contentor está a rodar, use docker-compose ps.

### 5. A Construir e Rodar a Aplicação

Na raiz do projeto, execute o seguinte comando Maven para construir e iniciar a aplicação:

```bash
mvn spring-boot:run
```

# URL Para UI SWAGGER: http://localhost:8080/swagger-ui/index.html
