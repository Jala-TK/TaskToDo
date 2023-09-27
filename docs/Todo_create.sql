-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-09-27 02:03:55.519

-- tables
-- Table: tarefas
CREATE TABLE public.tarefas (
    id serial  NOT NULL,
    titulo text  NOT NULL,
    descricao text  NOT NULL,
    status text  NOT NULL,
    datainicio date  NOT NULL,
    datalimite date  NOT NULL,
    dataconclusao date  NULL,
    CONSTRAINT "public.tarefas_pk" PRIMARY KEY (id)
);

-- Table: tarefas_usuarios
CREATE TABLE tarefas_usuarios (
    idtarefas_usuarios serial  NOT NULL,
    idtarefas int  NOT NULL,
    username_usuario varchar(150)  NOT NULL,
    CONSTRAINT tarefas_usuarios_pk PRIMARY KEY (idtarefas_usuarios)
);

-- Table: usuarios
CREATE TABLE usuarios (
    username varchar(150)  NOT NULL,
    nome varchar(150)  NOT NULL,
    sobrenome varchar(150)  NOT NULL,
    email varchar(150)  NOT NULL,
    password varchar(150)  NOT NULL,
    CONSTRAINT usuarios_pk PRIMARY KEY (username)
);

-- foreign keys
-- Reference: tarefas_usuarios_tarefas (table: tarefas_usuarios)
ALTER TABLE tarefas_usuarios ADD CONSTRAINT tarefas_usuarios_tarefas
    FOREIGN KEY (idtarefas)
    REFERENCES public.tarefas (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: tarefas_usuarios_usuarios (table: tarefas_usuarios)
ALTER TABLE tarefas_usuarios ADD CONSTRAINT tarefas_usuarios_usuarios
    FOREIGN KEY (username_usuario)
    REFERENCES usuarios (username)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

