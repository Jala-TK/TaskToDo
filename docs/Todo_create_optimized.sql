-- Criação das sequências
CREATE SEQUENCE tarefas_id_seq;
CREATE SEQUENCE tarefas_usuarios_idtarefas_usuarios_seq;

-- Tabela "tarefas"
CREATE TABLE tarefas
(
    id            INT4 DEFAULT NEXTVAL('tarefas_id_seq'::regclass) NOT NULL
        CONSTRAINT tarefas_pk
            PRIMARY KEY,
    titulo        TEXT NOT NULL,
    descricao     TEXT NOT NULL,
    status        TEXT NOT NULL,
    datainicio    DATE NOT NULL,
    datalimite    DATE NOT NULL,
    dataconclusao DATE
);

-- Tabela "usuarios"
CREATE TABLE usuarios
(
    username  VARCHAR(150) NOT NULL
        CONSTRAINT usuarios_pk
            PRIMARY KEY,
    nome      VARCHAR(150) NOT NULL,
    sobrenome VARCHAR(150) NOT NULL,
    email     VARCHAR(150) NOT NULL,
    password  VARCHAR(150) NOT NULL
);

-- Tabela "tarefas_usuarios"
CREATE TABLE tarefas_usuarios
(
    idtarefas_usuarios INT4 DEFAULT NEXTVAL('tarefas_usuarios_idtarefas_usuarios_seq'::regclass) NOT NULL
        CONSTRAINT tarefas_usuarios_pk
            PRIMARY KEY,
    idtarefas          INT4 NOT NULL
        CONSTRAINT tarefas_usuarios_tarefas
            REFERENCES tarefas,
    username_usuario   VARCHAR(150) NOT NULL
        CONSTRAINT tarefas_usuarios_usuarios
            REFERENCES usuarios
);

-- Índices
CREATE INDEX idx_tarefas_usuarios_idtarefas
    ON tarefas_usuarios (idtarefas);
CREATE INDEX idx_tarefas_usuarios_username
    ON tarefas_usuarios (username_usuario);
CREATE INDEX idx_usuarios_email
    ON usuarios (email);

-- Materialized View "usuario_login"
CREATE MATERIALIZED VIEW usuario_login AS
SELECT usuarios.username,
       usuarios.password
FROM usuarios;

-- Índice para a Materialized View
CREATE INDEX idx_usuario_login_username
    ON usuario_login (username);

-- Índices de chaves primárias únicas
CREATE UNIQUE INDEX tarefas_pk
    ON tarefas (id);
CREATE UNIQUE INDEX usuarios_pk
    ON usuarios (username);
